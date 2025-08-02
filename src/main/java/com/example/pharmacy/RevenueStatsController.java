package com.example.pharmacy;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class RevenueStatsController implements Initializable {
    
    @FXML private ComboBox<String> period_selector;
    @FXML private DatePicker start_date_picker;
    @FXML private DatePicker end_date_picker;
    @FXML private RadioButton line_chart_radio;
    @FXML private RadioButton bar_chart_radio;
    @FXML private ToggleGroup chart_type_group;
    
    @FXML private Label total_revenue_label;
    @FXML private Label average_revenue_label;
    @FXML private Label best_period_label;
    @FXML private Label growth_rate_label;
    
    @FXML private LineChart<String, Number> line_chart;
    @FXML private BarChart<String, Number> bar_chart;
    @FXML private CategoryAxis line_x_axis;
    @FXML private NumberAxis line_y_axis;
    @FXML private CategoryAxis bar_x_axis;
    @FXML private NumberAxis bar_y_axis;
    
    @FXML private TableView<RevenueAnalyticsData> revenue_table;
    @FXML private TableColumn<RevenueAnalyticsData, String> col_period;
    @FXML private TableColumn<RevenueAnalyticsData, String> col_revenue;
    @FXML private TableColumn<RevenueAnalyticsData, Integer> col_transactions;
    @FXML private TableColumn<RevenueAnalyticsData, String> col_avg_transaction;
    @FXML private TableColumn<RevenueAnalyticsData, String> col_growth;
    @FXML private TableColumn<RevenueAnalyticsData, String> col_percentage;
    
    @FXML private Button export_button;
    @FXML private Button refresh_button;
    @FXML private Button close_button;

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private ObservableList<RevenueAnalyticsData> analyticsList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize period selector
        period_selector.getItems().addAll("Daily", "Weekly", "Monthly", "Yearly", "Custom Range");
        period_selector.setValue("Monthly");
        
        // Initialize date pickers
        end_date_picker.setValue(LocalDate.now());
        start_date_picker.setValue(LocalDate.now().minusMonths(1));
        
        // Initially disable date pickers
        start_date_picker.setDisable(true);
        end_date_picker.setDisable(true);
        
        // Setup chart axes
        line_x_axis.setLabel("Period");
        line_y_axis.setLabel("Revenue (VND)");
        bar_x_axis.setLabel("Period");
        bar_y_axis.setLabel("Revenue (VND)");
        
        // Setup table columns
        col_period.setCellValueFactory(new PropertyValueFactory<>("period"));
        col_revenue.setCellValueFactory(new PropertyValueFactory<>("formattedRevenue"));
        col_transactions.setCellValueFactory(new PropertyValueFactory<>("transactions"));
        col_avg_transaction.setCellValueFactory(new PropertyValueFactory<>("formattedAvgTransaction"));
        col_growth.setCellValueFactory(new PropertyValueFactory<>("formattedGrowth"));
        col_percentage.setCellValueFactory(new PropertyValueFactory<>("formattedPercentage"));
        
        // Add listener for period selector
        period_selector.setOnAction(e -> {
            String selected = period_selector.getValue();
            if ("Custom Range".equals(selected)) {
                start_date_picker.setDisable(false);
                end_date_picker.setDisable(false);
            } else {
                start_date_picker.setDisable(true);
                end_date_picker.setDisable(true);
            }
            updateAnalytics();
        });
        
        // Load initial data
        updateAnalytics();
    }
    
    @FXML
    private void toggleChartType() {
        if (line_chart_radio.isSelected()) {
            line_chart.setVisible(true);
            bar_chart.setVisible(false);
        } else {
            line_chart.setVisible(false);
            bar_chart.setVisible(true);
        }
        updateChartData();
    }
    
    @FXML
    private void updateAnalytics() {
        String selectedPeriod = period_selector.getValue();
        if (selectedPeriod == null) return;
        
        analyticsList.clear();
        
        switch (selectedPeriod) {
            case "Daily":
                loadDailyAnalytics();
                break;
            case "Weekly":
                loadWeeklyAnalytics();
                break;
            case "Monthly":
                loadMonthlyAnalytics();
                break;
            case "Yearly":
                loadYearlyAnalytics();
                break;
            case "Custom Range":
                loadCustomRangeAnalytics();
                break;
        }
        
        revenue_table.setItems(analyticsList);
        calculateSummaryStats();
        updateChartData();
    }
    
    private void updateChartData() {
        // Clear existing data
        line_chart.getData().clear();
        bar_chart.getData().clear();
        
        if (analyticsList.isEmpty()) return;
        
        // Create data series
        XYChart.Series<String, Number> revenueSeriesLine = new XYChart.Series<>();
        revenueSeriesLine.setName("Revenue");
        
        XYChart.Series<String, Number> revenueSeriesBar = new XYChart.Series<>();
        revenueSeriesBar.setName("Revenue");
        
        // Add data points
        for (RevenueAnalyticsData data : analyticsList) {
            revenueSeriesLine.getData().add(new XYChart.Data<>(data.getPeriod(), data.getRevenue()));
            revenueSeriesBar.getData().add(new XYChart.Data<>(data.getPeriod(), data.getRevenue()));
        }
        
        // Add series to charts
        line_chart.getData().add(revenueSeriesLine);
        bar_chart.getData().add(revenueSeriesBar);
        
        // Style the charts
        line_chart.setCreateSymbols(true);
        line_chart.setLegendVisible(false);
        bar_chart.setLegendVisible(false);
    }
    
    private void loadCustomRangeAnalytics() {
        if (start_date_picker.getValue() == null || end_date_picker.getValue() == null) {
            return;
        }
        
        LocalDate startDate = start_date_picker.getValue();
        LocalDate endDate = end_date_picker.getValue();
        
        if (startDate.isAfter(endDate)) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Invalid Date Range");
            alert.setHeaderText(null);
            alert.setContentText("Start date must be before end date.");
            alert.showAndWait();
            return;
        }
        
        String sql = "SELECT DATE(createdDate) as period, " +
                    "SUM(total) as revenue, " +
                    "COUNT(*) as transactions " +
                    "FROM history " +
                    "WHERE DATE(createdDate) BETWEEN ? AND ? " +
                    "GROUP BY DATE(createdDate) " +
                    "ORDER BY period ASC";
        
        loadAnalyticsDataWithDateRange(sql, startDate, endDate);
    }
    
    private void loadAnalyticsDataWithDateRange(String sql, LocalDate startDate, LocalDate endDate) {
        connect = database.connectDb();
        
        try {
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, startDate.toString());
            prepare.setString(2, endDate.toString());
            result = prepare.executeQuery();
            
            int totalRevenue = 0;
            RevenueAnalyticsData previousData = null;
            
            while (result.next()) {
                String period = result.getString("period");
                int revenue = result.getInt("revenue");
                int transactions = result.getInt("transactions");
                
                totalRevenue += revenue;
                
                // Calculate growth rate
                double growthRate = 0;
                if (previousData != null) {
                    growthRate = ((double)(revenue - previousData.getRevenue()) / previousData.getRevenue()) * 100;
                }
                
                RevenueAnalyticsData data = new RevenueAnalyticsData(
                    period, revenue, transactions, growthRate, 0.0 // percentage will be calculated later
                );
                
                analyticsList.add(data);
                previousData = data;
            }
            
            // Calculate percentages
            for (RevenueAnalyticsData data : analyticsList) {
                double percentage = totalRevenue > 0 ? ((double)data.getRevenue() / totalRevenue) * 100 : 0;
                data.setPercentage(percentage);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void loadMonthlyAnalytics() {
        String sql = "SELECT DATE_FORMAT(createdDate, '%Y-%m') as period, " +
                    "SUM(total) as revenue, " +
                    "COUNT(*) as transactions " +
                    "FROM history " +
                    "WHERE createdDate >= DATE_SUB(CURDATE(), INTERVAL 12 MONTH) " +
                    "GROUP BY YEAR(createdDate), MONTH(createdDate) " +
                    "ORDER BY period ASC";
        
        loadAnalyticsData(sql);
    }
    
    private void loadWeeklyAnalytics() {
        String sql = "SELECT CONCAT(YEAR(createdDate), '-W', LPAD(WEEK(createdDate, 1), 2, '0')) as period, " +
                    "SUM(total) as revenue, " +
                    "COUNT(*) as transactions " +
                    "FROM history " +
                    "WHERE createdDate >= DATE_SUB(CURDATE(), INTERVAL 12 WEEK) " +
                    "GROUP BY YEAR(createdDate), WEEK(createdDate, 1) " +
                    "ORDER BY YEAR(createdDate), WEEK(createdDate, 1) ASC";
        
        loadAnalyticsData(sql);
    }
    
    private void loadDailyAnalytics() {
        String sql = "SELECT DATE(createdDate) as period, " +
                    "SUM(total) as revenue, " +
                    "COUNT(*) as transactions " +
                    "FROM history " +
                    "WHERE createdDate >= DATE_SUB(CURDATE(), INTERVAL 30 DAY) " +
                    "GROUP BY DATE(createdDate) " +
                    "ORDER BY period ASC";
        
        loadAnalyticsData(sql);
    }
    
    private void loadYearlyAnalytics() {
        String sql = "SELECT YEAR(createdDate) as period, " +
                    "SUM(total) as revenue, " +
                    "COUNT(*) as transactions " +
                    "FROM history " +
                    "GROUP BY YEAR(createdDate) " +
                    "ORDER BY period ASC";
        
        loadAnalyticsData(sql);
    }
    
    private void loadAnalyticsData(String sql) {
        connect = database.connectDb();
        
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            
            int totalRevenue = 0;
            RevenueAnalyticsData previousData = null;
            
            while (result.next()) {
                String period = result.getString("period");
                int revenue = result.getInt("revenue");
                int transactions = result.getInt("transactions");
                
                totalRevenue += revenue;
                
                // Calculate growth rate
                double growthRate = 0;
                if (previousData != null) {
                    growthRate = ((double)(revenue - previousData.getRevenue()) / previousData.getRevenue()) * 100;
                }
                
                RevenueAnalyticsData data = new RevenueAnalyticsData(
                    period, revenue, transactions, growthRate, 0.0 // percentage will be calculated later
                );
                
                analyticsList.add(data);
                previousData = data;
            }
            
            // Calculate percentages
            for (RevenueAnalyticsData data : analyticsList) {
                double percentage = totalRevenue > 0 ? ((double)data.getRevenue() / totalRevenue) * 100 : 0;
                data.setPercentage(percentage);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void calculateSummaryStats() {
        if (analyticsList.isEmpty()) return;
        
        int totalRevenue = analyticsList.stream().mapToInt(RevenueAnalyticsData::getRevenue).sum();
        double averageRevenue = (double)totalRevenue / analyticsList.size();
        
        RevenueAnalyticsData bestPeriod = analyticsList.stream()
                .max((a, b) -> Integer.compare(a.getRevenue(), b.getRevenue()))
                .orElse(null);
        
        // Calculate overall growth rate
        double growthRate = 0;
        if (analyticsList.size() > 1) {
            int firstPeriodRevenue = analyticsList.get(0).getRevenue();
            int lastPeriodRevenue = analyticsList.get(analyticsList.size() - 1).getRevenue();
            if (firstPeriodRevenue > 0) {
                growthRate = ((double)(lastPeriodRevenue - firstPeriodRevenue) / firstPeriodRevenue) * 100;
            }
        }
        
        DecimalFormat df = new DecimalFormat("#,###");
        DecimalFormat percentFormat = new DecimalFormat("#.##");
        
        total_revenue_label.setText(df.format(totalRevenue) + " VND");
        average_revenue_label.setText(df.format((int)averageRevenue) + " VND");
        best_period_label.setText(bestPeriod != null ? bestPeriod.getPeriod() : "N/A");
        growth_rate_label.setText(percentFormat.format(growthRate) + "%");
    }
    
    @FXML
    private void exportData() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Export Revenue Data");
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("CSV Files", "*.csv")
        );
        
        Stage stage = (Stage) export_button.getScene().getWindow();
        File file = fileChooser.showSaveDialog(stage);
        
        if (file != null) {
            try (FileWriter writer = new FileWriter(file)) {
                // Write header
                writer.append("Period,Revenue,Transactions,Avg Transaction,Growth %,% of Total\n");
                
                // Write data
                for (RevenueAnalyticsData data : analyticsList) {
                    writer.append(data.getPeriod()).append(",");
                    writer.append(String.valueOf(data.getRevenue())).append(",");
                    writer.append(String.valueOf(data.getTransactions())).append(",");
                    writer.append(String.valueOf(data.getAvgTransaction())).append(",");
                    writer.append(String.format("%.2f", data.getGrowthRate())).append(",");
                    writer.append(String.format("%.2f", data.getPercentage())).append("\n");
                }
                
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Export Successful");
                alert.setHeaderText(null);
                alert.setContentText("Data exported successfully to: " + file.getAbsolutePath());
                alert.showAndWait();
                
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Export Error");
                alert.setHeaderText(null);
                alert.setContentText("Failed to export data: " + e.getMessage());
                alert.showAndWait();
            }
        }
    }
    
    @FXML
    private void closeWindow() {
        Stage stage = (Stage) close_button.getScene().getWindow();
        stage.close();
    }
}
