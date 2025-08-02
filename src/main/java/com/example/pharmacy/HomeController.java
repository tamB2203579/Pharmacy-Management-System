package com.example.pharmacy;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Modality;
import javafx.util.Callback;

import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.sql.*;
import java.util.Date;

public class HomeController implements Initializable {
    @FXML
    private AnchorPane main_form;

    @FXML
    private AnchorPane home_form;

    @FXML
    private AnchorPane dashboard_form;

    @FXML
    private AreaChart<String, Number> dashboard_chart;

    @FXML
    private Label dashboard_customer;

    @FXML
    private Label dashboard_income;

    @FXML
    private Label dashboard_today_sales;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private ComboBox<String> revenueFilterComboBox;

    @FXML
    private AnchorPane addMedicines_form;

    @FXML
    private TextField addMedicines_medicineID;

    @FXML
    private TextField addMedicines_productName;

    @FXML
    private ComboBox<?> addMedicines_category;

    @FXML
    private  TextField addMedicines_quantity;

    @FXML
    private TextField addMedicines_price;

    @FXML
    private ComboBox<?> addMedicines_status;

    @FXML
    private TextField addMedicines_search;

    @FXML
    private ComboBox<String> filterCategory_comboBox;

    @FXML
    private ComboBox<String> filterStatus_comboBox;

    @FXML
    private Button clearFilter_btn;

    @FXML
    private TableView<medicineData> addMedicines_tableView;

    @FXML
    private TableColumn<medicineData, String> addMedicines_col_medicineID;

    @FXML
    private TableColumn<medicineData, String> addMedicines_col_productName;

    @FXML
    private TableColumn<medicineData, String> addMedicines_col_category;

    @FXML
    private TableColumn<medicineData, String> addMedicines_col_quantity;

    @FXML
    private TableColumn<medicineData, String> addMedicines_col_price;

    @FXML
    private TableColumn<medicineData, String> addMedicines_col_status;

    @FXML
    private AnchorPane customer_form;

    @FXML
    private TextField customerFullName;

    @FXML
    private TextField customerPhoneNumber;

    @FXML
    private TextField customerTotal;

    @FXML
    private TextField customerPoints;

    @FXML
    private TextField customer_search;

    @FXML
    private TableView<customerData> customer_tableView;

    @FXML
    private TableColumn<customerData, String> customer_col_id;

    @FXML
    private TableColumn<customerData, String> customer_col_fullName;

    @FXML
    private TableColumn<customerData, String> customer_col_phoneNumber;

    @FXML
    private TableColumn<customerData, String> customer_col_points;

    @FXML
    private TableColumn<customerData, String> customer_col_date;

    @FXML
    private AnchorPane history_form;

    @FXML
    private TextField history_search;

    @FXML
    private TableView<historyData> history_tableView;

    @FXML
    private TableColumn<historyData, String> history_col_id;

    @FXML
    private TableColumn<historyData, String> history_col_cusName;

    @FXML
    private TableColumn<historyData, String> history_col_staffName;

    @FXML
    private TableColumn<historyData, String> history_col_total;

    @FXML
    private TableColumn<historyData, String> history_col_date;

    @FXML
    private TableColumn<historyData, Void> history_col_action;

    @FXML
    private AnchorPane purchase_form;

    @FXML
    private TextField staffName;

    @FXML
    private ComboBox<?> purchase_medID;

    @FXML
    private ComboBox<?> purchase_productName;

    @FXML
    private ComboBox<?> purchase_category;

    @FXML
    private TextField purchase_quantity;

    @FXML
    private Label purchase_items;

    @FXML
    private CheckBox checkDiscount;

    @FXML
    private Label purchase_discount;

    @FXML
    private Label purchase_total;

    @FXML
    private TableView<purchaseData> purchase_tableView;

    @FXML
    private TableColumn<purchaseData, String> purchase_col_category;

    @FXML
    private TableColumn<purchaseData, String> purchase_col_price;

    @FXML
    private TableColumn<purchaseData, String> purchase_col_productName;

    @FXML
    private TableColumn<purchaseData, String> purchase_col_quantity;

    @FXML
    private TextField customer_search1;

    @FXML
    private TableView<customerData> customer_tableView1;

    @FXML
    private TableColumn<customerData, String> customer_col_id1;

    @FXML
    private TableColumn<customerData, String> customer_col_fullName1;

    @FXML
    private TableColumn<customerData, String> customer_col_phoneNumber1;

    @FXML
    private TableColumn<customerData, String> customer_col_points1;

    @FXML
    private TableColumn<customerData, String> customer_col_date1;

    @FXML
    private Label username;

    @FXML
    private Button dashboard_btn;

    @FXML
    private Button medicines_btn;

    @FXML
    private Button customer_btn;

    @FXML
    private Button purchase_btn;

    @FXML
    private Button add_invoice_btn;

    @FXML
    private Button logout;

    @FXML
    private Button addMedicines_updateBtn;

    @FXML
    private Button addMedicines_deleteBtn;

    @FXML
    private Button customer_updateBtn;

    @FXML
    private Button customer_deleteBtn;

    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;

    private double x = 0;
    private double y = 0;
    
    // State management for purchase process
    private boolean purchaseInProgress = false;
    
    // Validation methods
    private boolean isValidPositiveInteger(String value) {
        try {
            int num = Integer.parseInt(value);
            return num >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    private boolean isValidPositiveDouble(String value) {
        try {
            double num = Double.parseDouble(value);
            return num >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    private boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber != null && phoneNumber.replaceAll("\\s", "").length() == 10 && phoneNumber.matches("\\d+");
    }
    
    // Enable/disable buttons based on selection
    private void updateButtonStates() {
        boolean medicineSelected = addMedicines_tableView.getSelectionModel().getSelectedItem() != null;
        boolean customerSelected = customer_tableView.getSelectionModel().getSelectedItem() != null;
        
        if (addMedicines_updateBtn != null) addMedicines_updateBtn.setDisable(!medicineSelected);
        if (addMedicines_deleteBtn != null) addMedicines_deleteBtn.setDisable(!medicineSelected);
        if (customer_updateBtn != null) customer_updateBtn.setDisable(!customerSelected);
        if (customer_deleteBtn != null) customer_deleteBtn.setDisable(!customerSelected);
    }

    public void homeChart(){
        // Use default monthly view
        updateRevenueChart("Monthly", null, null);
    }

    public void homeTC(){
        String sql = "SELECT COUNT(id) FROM Customer";

        connect = database.connectDb();

        int countTC = 0;

        try{
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while(result.next()){
                countTC = result.getInt("COUNT(id)");
            }

            dashboard_customer.setText(String.valueOf(countTC));

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void homeTI(){
        String sql = "SELECT SUM(total) FROM history";

        connect = database.connectDb();
        int totalDisplay = 0;
        try{
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while(result.next()){
                totalDisplay = result.getInt("SUM(total)");
            }

            dashboard_income.setText(String.valueOf(totalDisplay) + " VND");

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    // Initialize revenue filter ComboBox
    public void initializeRevenueFilter(){
        ObservableList<String> filterOptions = FXCollections.observableArrayList("Weekly", "Monthly", "Yearly");
        revenueFilterComboBox.setItems(filterOptions);
        revenueFilterComboBox.setValue("Monthly"); // Default selection
    }

    // Initialize medicine filter ComboBoxes
    public void initializeMedicineFilters(){
        // Skip initialization if components are not ready
        if (filterCategory_comboBox == null || filterStatus_comboBox == null) {
            System.err.println("Filter ComboBoxes not initialized yet");
            return;
        }
        
        // Initialize Category filter
        ObservableList<String> categoryList = FXCollections.observableArrayList();
        categoryList.add("All Categories"); // Add "All" option
        
        String sql = "SELECT DISTINCT category FROM medicine ORDER BY category";
        
        Connection localConnect = null;
        PreparedStatement localPrepare = null;
        ResultSet localResult = null;
        
        try {
            localConnect = database.connectDb();
            if (localConnect != null) {
                localPrepare = localConnect.prepareStatement(sql);
                localResult = localPrepare.executeQuery();
                
                while (localResult.next()) {
                    String category = localResult.getString("category");
                    if (category != null && !category.trim().isEmpty()) {
                        categoryList.add(category);
                    }
                }
            } else {
                System.err.println("Database connection failed - using default categories");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error loading categories: " + e.getMessage());
        } finally {
            // Clean up resources
            try {
                if (localResult != null) localResult.close();
                if (localPrepare != null) localPrepare.close();
                if (localConnect != null) localConnect.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        filterCategory_comboBox.setItems(categoryList);
        filterCategory_comboBox.setValue("All Categories"); // Default selection
        
        // Initialize Status filter
        ObservableList<String> statusList = FXCollections.observableArrayList();
        statusList.add("All Status"); // Add "All" option
        statusList.add("Available");
        statusList.add("Not Available");
        
        filterStatus_comboBox.setItems(statusList);
        filterStatus_comboBox.setValue("All Status"); // Default selection
    }

    // Handle ComboBox selection change
    @FXML
    public void onRevenueFilterChanged(){
        String selectedPeriod = revenueFilterComboBox.getValue();
        System.out.println("Filter changed to: " + selectedPeriod); // Debug line
        if(selectedPeriod != null){
            updateRevenueChart(selectedPeriod, null, null);
        }
    }

    // Handle Apply button for date range filtering
    @FXML
    public void onApplyRevenueFilter(){
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();
        
        if(startDate != null && endDate != null){
            if(startDate.isAfter(endDate)){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Date Range");
                alert.setHeaderText(null);
                alert.setContentText("Start date cannot be after end date");
                alert.showAndWait();
                return;
            }
            updateRevenueChart("Custom", startDate, endDate);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Missing Dates");
            alert.setHeaderText(null);
            alert.setContentText("Please select both start and end dates");
            alert.showAndWait();
        }
    }

    public void updateRevenueChart(String period, LocalDate startDate, LocalDate endDate){
        System.out.println("Updating chart for period: " + period); // Debug
        dashboard_chart.getData().clear();

        String sql = "";

        switch (period) {
            case "Weekly":
                sql = "SELECT DATE(createdDate) AS period, SUM(total) AS revenue " +
                    "FROM history " +
                    "WHERE createdDate >= DATE_SUB(CURDATE(), INTERVAL 7 DAY) " +
                    "AND createdDate <= CURDATE() " +
                    "GROUP BY DATE(createdDate) " +
                    "ORDER BY DATE(createdDate) ASC";
                break;

            case "Monthly":
                sql = "SELECT DATE(createdDate) AS period, SUM(total) AS revenue " +
                    "FROM history " +
                    "WHERE createdDate >= DATE_SUB(CURDATE(), INTERVAL 30 DAY) " +
                    "AND createdDate <= CURDATE() " +
                    "GROUP BY DATE(createdDate) " +
                    "ORDER BY DATE(createdDate) ASC";
                break;

            case "Yearly":
                sql = "SELECT DATE(createdDate) AS period, SUM(total) AS revenue " +
                    "FROM history " +
                    "WHERE createdDate >= DATE_SUB(CURDATE(), INTERVAL 365 DAY) " +
                    "AND createdDate <= CURDATE() " +
                    "GROUP BY DATE(createdDate) " +
                    "ORDER BY DATE(createdDate) ASC";
                break;

            case "Custom":
                if (startDate != null && endDate != null) {
                    sql = "SELECT DATE(createdDate) AS period, SUM(total) AS revenue " +
                        "FROM history " +
                        "WHERE createdDate BETWEEN ? AND ? " +
                        "GROUP BY DATE(createdDate) " +
                        "ORDER BY DATE(createdDate) ASC";
                }
                break;
        }

        connect = database.connectDb();

        try {
            XYChart.Series<String, Number> chart = new XYChart.Series<>();
            chart.setName("Revenue");

            prepare = connect.prepareStatement(sql);

            // Set parameters for custom date range
            if (period.equals("Custom") && startDate != null && endDate != null) {
                prepare.setString(1, startDate.toString());
                prepare.setString(2, endDate.toString());
            }

            result = prepare.executeQuery();

            double totalRevenue = 0;
            int dataCount = 0;

            System.out.println("=== Query Results for " + period + " ===");

            while (result.next()) {
                String periodLabel = result.getString("period"); // e.g. 2025-08-01
                double revenue = result.getDouble("revenue");
                totalRevenue += revenue;
                dataCount++;

                String formattedLabel = periodLabel; // ← No formatting, use full date string

                System.out.println("Formatted label: " + formattedLabel + ", Revenue: " + revenue);
                chart.getData().add(new XYChart.Data<>(formattedLabel, revenue));
            }

            if (dataCount == 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("No Data");
                alert.setHeaderText(null);
                alert.setContentText("No revenue data found for the selected period.");
                alert.showAndWait();
            }

            dashboard_chart.getData().add(chart);

            // Update total revenue label
            DecimalFormat df = new DecimalFormat("#,##0");
            dashboard_income.setText(df.format(totalRevenue) + " VND");

        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Database Error");
            alert.setHeaderText(null);
            alert.setContentText("Error loading revenue data: " + e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    void addMedicineAdd() {
        String sql = "INSERT INTO medicine (medicine_id, productName, category, quantity, price, status) "
                + "VALUES(?,?,?,?,?,?)";

        connect = database.connectDb();

        try{
            Alert alert;

            if (addMedicines_medicineID.getText().isEmpty()
                    || addMedicines_productName.getText().isEmpty()
                    || addMedicines_category.getSelectionModel().getSelectedItem() == null
                    || addMedicines_quantity.getText().isEmpty()
                    || addMedicines_price.getText().isEmpty()
                    || addMedicines_status.getSelectionModel().getSelectedItem() == null) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else{
                // Validate numeric fields
                if (!isValidPositiveInteger(addMedicines_quantity.getText())) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Quantity must be a non-negative number");
                    alert.showAndWait();
                    return;
                }
                
                if (!isValidPositiveDouble(addMedicines_price.getText())) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Price must be a non-negative number");
                    alert.showAndWait();
                    return;
                }
                
                // CHECK IF THE MEDICINE ID YOU WANT TO INSERT EXIST
                String checkData = "SELECT medicine_id FROM medicine WHERE medicine_id = '"
                        + addMedicines_medicineID.getText() + "'";

                statement = connect.createStatement();
                result = statement.executeQuery(checkData);

                if (result.next()) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Medicine ID: " + addMedicines_medicineID.getText() + " was already exist!");
                    alert.showAndWait();
                } else {
                    int quantity = Integer.parseInt(addMedicines_quantity.getText());
                    String autoStatus = quantity > 0 ? "Available" : "Not Available";
                    
                    prepare = connect.prepareStatement(sql);
                    prepare.setString(1, addMedicines_medicineID.getText());
                    prepare.setString(2, addMedicines_productName.getText());
                    prepare.setString(3, (String) addMedicines_category.getSelectionModel().getSelectedItem());
                    prepare.setString(4, addMedicines_quantity.getText());
                    prepare.setString(5, addMedicines_price.getText());
                    prepare.setString(6, autoStatus);

                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Added!");
                    alert.showAndWait();

                    addMedicineShowListData();
                    addMedicineReset();

                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void addMedicineUpdate() {
        String sql = "UPDATE medicine SET productName = ?, category = ?, quantity = ?, price = ?, status = ? WHERE medicine_id = ?";

        connect = database.connectDb();

        try {
            Alert alert;

            if (addMedicines_medicineID.getText().isEmpty()
                    || addMedicines_productName.getText().isEmpty()
                    || addMedicines_category.getSelectionModel().getSelectedItem() == null
                    || addMedicines_quantity.getText().isEmpty()
                    || addMedicines_price.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields and select a medicine to update");
                alert.showAndWait();
            } else {
                // Validate numeric fields
                if (!isValidPositiveInteger(addMedicines_quantity.getText())) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Quantity must be a non-negative number");
                    alert.showAndWait();
                    return;
                }
                
                if (!isValidPositiveDouble(addMedicines_price.getText())) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Price must be a non-negative number");
                    alert.showAndWait();
                    return;
                }
                
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to UPDATE Medicine ID:" + addMedicines_medicineID.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    int quantity = Integer.parseInt(addMedicines_quantity.getText());
                    String autoStatus = quantity > 0 ? "Available" : "Not Available";
                    
                    prepare = connect.prepareStatement(sql);
                    prepare.setString(1, addMedicines_productName.getText());
                    prepare.setString(2, (String) addMedicines_category.getSelectionModel().getSelectedItem());
                    prepare.setString(3, addMedicines_quantity.getText());
                    prepare.setString(4, addMedicines_price.getText());
                    prepare.setString(5, autoStatus);
                    prepare.setString(6, addMedicines_medicineID.getText());

                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Updated!");
                    alert.showAndWait();

                    addMedicineShowListData();
                    addMedicineReset();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void addMedicineDelete() {
        String sql = "DELETE FROM medicine WHERE medicine_id = '" + addMedicines_medicineID.getText() + "'";

        connect = database.connectDb();

        try {
            Alert alert;
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to DELETE Medicine ID:" + addMedicines_medicineID.getText() + "?");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {
                statement = connect.createStatement();
                statement.executeUpdate(sql);

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Successfully Deleted!");
                alert.showAndWait();

                addMedicineShowListData();
                addMedicineReset();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void addMedicineReset() {
        addMedicines_medicineID.setText("");
        addMedicines_productName.setText("");
        addMedicines_category.getSelectionModel().clearSelection();
        addMedicines_quantity.setText("");
        addMedicines_price.setText("");
        addMedicines_status.getSelectionModel().clearSelection();
    }

    private String[] addMedicineListC = {"Hydrocodone", "Antibiotics", "Metformin", "Losartan", "Albuterol"};

    @FXML
    void addMedicineListCategory() {
        List<String> listC = new ArrayList<>();

        for (String data : addMedicineListC) {
            listC.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(listC);
        addMedicines_category.setItems(listData);
    }

    private String[] addMedicineListS = {"Available", "Not Available"};

    @FXML
    void addMedicineListStatus() {
        List<String> listS = new ArrayList<>();

        for (String data : addMedicineListS) {
            listS.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(listS);
        addMedicines_status.setItems(listData);
    }

    @FXML
    ObservableList<medicineData> addMedicineListData(){
        String sql = "SELECT * FROM medicine";

        ObservableList<medicineData> listData = FXCollections.observableArrayList();

        connect = database.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            medicineData medData;

            while (result.next()) {
                medData = new medicineData(result.getString("medicine_id"),
                        result.getString("productName"),
                        result.getString("category"),
                        result.getInt("quantity"),
                        result.getInt("price"),
                        result.getString("status"));

                listData.add(medData);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }

    private ObservableList<medicineData> addMedicineList;

    @FXML
    void addMedicineShowListData(){
        addMedicineList = addMedicineListData();

        addMedicines_col_medicineID.setCellValueFactory(new PropertyValueFactory<>("medicineId"));
        addMedicines_col_productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        addMedicines_col_category.setCellValueFactory(new PropertyValueFactory<>("category"));
        addMedicines_col_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        addMedicines_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        addMedicines_col_status.setCellValueFactory(new PropertyValueFactory<>("status"));

        addMedicines_tableView.setItems(addMedicineList);
    }

    @FXML
    void addMedicineSearch() {
        applyFilters();
    }

    @FXML
    void filterByCategory() {
        if (filterCategory_comboBox != null && addMedicines_tableView != null) {
            applyFilters();
        }
    }

    @FXML
    void filterByStatus() {
        if (filterStatus_comboBox != null && addMedicines_tableView != null) {
            applyFilters();
        }
    }

    @FXML
    void clearFilters() {
        // Reset all filters to default values
        if (filterCategory_comboBox != null) {
            filterCategory_comboBox.setValue("All Categories");
        }
        if (filterStatus_comboBox != null) {
            filterStatus_comboBox.setValue("All Status");
        }
        if (addMedicines_search != null) {
            addMedicines_search.clear();
        }
        
        // Show all medicines
        addMedicineShowListData();
    }

    private void applyFilters() {
        // Debug: Check if components are properly initialized
        if (addMedicines_tableView == null) {
            System.err.println("TableView is null - cannot apply filters");
            return;
        }
        
        String searchText = addMedicines_search != null ? addMedicines_search.getText() : "";
        String selectedCategory = filterCategory_comboBox != null ? filterCategory_comboBox.getValue() : "All Categories";
        String selectedStatus = filterStatus_comboBox != null ? filterStatus_comboBox.getValue() : "All Status";
        
        // If no filters are applied, show all data
        if ((searchText == null || searchText.trim().isEmpty()) && 
            (selectedCategory == null || selectedCategory.equals("All Categories")) &&
            (selectedStatus == null || selectedStatus.equals("All Status"))) {
            addMedicineShowListData();
            return;
        }
        
        // Build SQL query based on filters
        StringBuilder sql = new StringBuilder("SELECT * FROM medicine WHERE 1=1");
        
        // Add search filter
        if (searchText != null && !searchText.trim().isEmpty()) {
            sql.append(" AND (medicine_id LIKE ? OR productName LIKE ? OR category LIKE ? OR price LIKE ? OR status LIKE ?)");
        }
        
        // Add category filter
        if (selectedCategory != null && !selectedCategory.equals("All Categories")) {
            sql.append(" AND category = ?");
        }
        
        // Add status filter
        if (selectedStatus != null && !selectedStatus.equals("All Status")) {
            sql.append(" AND status = ?");
        }
        
        Connection localConnect = null;
        PreparedStatement localPrepare = null;
        ResultSet localResult = null;
        
        try {
            // Use local connection to avoid conflicts
            localConnect = database.connectDb();
            if (localConnect == null) {
                System.err.println("Database connection failed - showing all data instead");
                addMedicineShowListData();
                return;
            }
            
            localPrepare = localConnect.prepareStatement(sql.toString());
            
            int paramIndex = 1;
            
            // Set search parameters
            if (searchText != null && !searchText.trim().isEmpty()) {
                String searchPattern = "%" + searchText + "%";
                localPrepare.setString(paramIndex++, searchPattern);
                localPrepare.setString(paramIndex++, searchPattern);
                localPrepare.setString(paramIndex++, searchPattern);
                localPrepare.setString(paramIndex++, searchPattern);
                localPrepare.setString(paramIndex++, searchPattern);
            }
            
            // Set category parameter
            if (selectedCategory != null && !selectedCategory.equals("All Categories")) {
                localPrepare.setString(paramIndex++, selectedCategory);
            }
            
            // Set status parameter
            if (selectedStatus != null && !selectedStatus.equals("All Status")) {
                localPrepare.setString(paramIndex++, selectedStatus);
            }
            
            localResult = localPrepare.executeQuery();
            
            ObservableList<medicineData> filteredList = FXCollections.observableArrayList();
            
            while (localResult.next()) {
                medicineData medData = new medicineData(
                    localResult.getString("medicine_id"),
                    localResult.getString("productName"),
                    localResult.getString("category"),
                    localResult.getInt("quantity"),
                    localResult.getInt("price"),
                    localResult.getString("status")
                );
                filteredList.add(medData);
            }
            
            addMedicines_tableView.setItems(filteredList);
            
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error applying filters: " + e.getMessage());
            // Fallback to showing all data
            addMedicineShowListData();
        } finally {
            // Clean up local resources
            try {
                if (localResult != null) localResult.close();
                if (localPrepare != null) localPrepare.close();
                if (localConnect != null) localConnect.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void addMedicineSelect(MouseEvent event) {
        medicineData medData = addMedicines_tableView.getSelectionModel().getSelectedItem();
        int num = addMedicines_tableView.getSelectionModel().getSelectedIndex();

        if ((num - 1) < - 1) {
            return;
        }

        addMedicines_medicineID.setText(String.valueOf(medData.getMedicineId()));
        addMedicines_productName.setText(medData.getProductName());
        addMedicines_quantity.setText(String.valueOf(medData.getQuantity()));
        addMedicines_price.setText(String.valueOf(medData.getPrice()));
        
        updateButtonStates();
    }

    public ObservableList<customerData> customerListData() {
        String sql = "SELECT * FROM Customer";

        ObservableList<customerData> listData = FXCollections.observableArrayList();

        connect = database.connectDb();

        try{
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            customerData cusData;
            while (result.next()) {
                cusData = new customerData(
                        result.getInt("id"),
                        result.getString("fullName"),
                        result.getString("phoneNum"),
                        result.getDate("registrationDate"),
//                        result.getDouble("total"),
                        result.getInt("loyaltyPoints")
                );

                listData.add(cusData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listData;
    }

    private ObservableList<customerData> customerList;
    public void customerShowListData(){
        customerList = customerListData();

        customer_col_id.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customer_col_fullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        customer_col_phoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        customer_col_date.setCellValueFactory(new PropertyValueFactory<>("registrationDate"));
//        customer_col_total.setCellValueFactory(new PropertyValueFactory<>("total"));
        customer_col_points.setCellValueFactory(new PropertyValueFactory<>("loyaltyPoints"));

        customer_tableView.setItems(customerList);
    }

    public void customerSelect(){
        customerData cusData = customer_tableView.getSelectionModel().getSelectedItem();
        int num = customer_tableView.getSelectionModel().getSelectedIndex();

        if( (num-1) < -1) return;

        customerFullName.setText(cusData.getFullName());
        customerPhoneNumber.setText(cusData.getPhoneNumber());
        customerPoints.setText(String.valueOf(cusData.getLoyaltyPoints()));
        
        updateButtonStates();
    }

    public void customerAdd(){
        String sql = "INSERT INTO Customer (fullName,phoneNum,loyaltyPoints)"
                + " VALUES (?,?,?)";

        connect = database.connectDb();

        try{
            Alert alert;

            if(customerFullName.getText().isEmpty() || customerPhoneNumber.getText().isEmpty() || customerTotal.getText().isEmpty()){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all the blank fields");
                alert.showAndWait();
            } else if (!isValidPhoneNumber(customerPhoneNumber.getText())) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Phone number must be exactly 10 digits");
                alert.showAndWait();
            } else {
                String checkData = "SELECT * FROM Customer WHERE phoneNum = '" + customerPhoneNumber.getText() + "'";

                statement = connect.createStatement();
                result = statement.executeQuery(checkData);

                if(result.next()){
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Customer already exists");
                    alert.showAndWait();
                }else{
                    double total = Double.parseDouble(customerTotal.getText());
                    int loyaltyPoints = (int) (total / 100000);

                    prepare = connect.prepareStatement(sql);

                    prepare.setString(1, customerFullName.getText());
                    prepare.setString(2, customerPhoneNumber.getText());
//                    prepare.setDouble(3, Double.parseDouble(customerTotal.getText()));
                    prepare.setInt(3, loyaltyPoints);

                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully added!");
                    alert.showAndWait();

                    customerShowListData();
                    customerReset();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

    }

    public void customerReset(){
        customerFullName.setText("");
        customerPhoneNumber.setText("");
        customerTotal.setText("");
        customerPoints.setText("");
    }

    public Integer getSelectedCustomerId() {
        customerData selectedCustomer = customer_tableView.getSelectionModel().getSelectedItem();
        return (selectedCustomer != null) ? selectedCustomer.getCustomerId() : null;
    }

    public void customerUpdate(){
        String sql = "UPDATE Customer SET fullName = ?, phoneNum = ? WHERE id = ?";

        connect = database.connectDb();

        try{
            Alert alert;

            if(customerFullName.getText().isEmpty() || customerPhoneNumber.getText().isEmpty()){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all the blank fields");
                alert.showAndWait();
            } else if (!isValidPhoneNumber(customerPhoneNumber.getText())) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Phone number must be exactly 10 digits");
                alert.showAndWait();
            } else {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to update this customer?");
                Optional<ButtonType> option= alert.showAndWait();

                if(option.get() == ButtonType.OK){

                    Integer customerId = getSelectedCustomerId();

                    prepare = connect.prepareStatement(sql);
                    prepare.setString(1, customerFullName.getText());
                    prepare.setString(2, (String) customerPhoneNumber.getText());
                    prepare.setInt(3, customerId);

                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully updated!");
                    alert.showAndWait();

                    customerShowListData();
                    customerReset();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void customerDelete(){
        String sql = "DELETE FROM Customer WHERE phoneNum = '" + customerPhoneNumber.getText() + "'";
        connect = database.connectDb();

        try{
            Alert alert;

            if(customerFullName.getText().isEmpty() || customerPhoneNumber.getText().isEmpty() || customerPoints.getText().isEmpty()){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all the blank fields");
                alert.showAndWait();
            } else {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to delete this customer?");
                Optional<ButtonType> option= alert.showAndWait();

                if(option.get() == ButtonType.OK){
                    statement = connect.prepareStatement(sql);
                    statement.executeUpdate(sql);

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully deleted!");
                    alert.showAndWait();

                    customerShowListData();
                    customerReset();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void customerSearch(){
        String sql = "SELECT * FROM Customer WHERE fullName LIKE ? or phoneNum LIKE ?";

        connect = database.connectDb();

        try {
            prepare = connect.prepareStatement(sql);

            prepare.setString(1, "%" + customer_search.getText() + "%");
            prepare.setString(2, "%" + customer_search.getText() + "%");

            result = prepare.executeQuery();

            ObservableList<customerData> listData = FXCollections.observableArrayList();

            customerData cusData;

            while (result.next()) {
                cusData = new customerData(
                        result.getInt("id"),
                        result.getString("fullName"),
                        result.getString("phoneNum"),
                        result.getDate("registrationDate"),
                        result.getInt("loyaltyPoints")
                );


                listData.add(cusData);
            }

            customer_tableView.setItems(listData);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void customerSearch1(){
        String sql = "SELECT * FROM Customer WHERE fullName LIKE ? or phoneNum LIKE ?";

        connect = database.connectDb();

        try {
            prepare = connect.prepareStatement(sql);

            prepare.setString(1, "%" + customer_search1.getText() + "%");
            prepare.setString(2, "%" + customer_search1.getText() + "%");

            result = prepare.executeQuery();

            ObservableList<customerData> listData = FXCollections.observableArrayList();

            customerData cusData;

            while (result.next()) {
                cusData = new customerData(
                        result.getInt("id"),
                        result.getString("fullName"),
                        result.getString("phoneNum"),
                        result.getDate("registrationDate"),
                        result.getInt("loyaltyPoints")
                );


                listData.add(cusData);
            }

            customer_tableView1.setItems(listData);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void purchaseCategory(){
        String sql = "SELECT DISTINCT category FROM medicine WHERE status = 'Available' ";

        connect = database.connectDb();

        try{
            ObservableList listData = FXCollections.observableArrayList();

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                listData.add(result.getString("category"));
            }

            purchase_category.setItems(listData);

            purchaseProductName();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void purchaseProductName(){

        String sql = "SELECT * FROM medicine WHERE category = '"
                +purchase_category.getSelectionModel().getSelectedItem()+"'";

        connect = database.connectDb();

        try{
            ObservableList listData = FXCollections.observableArrayList();

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                listData.add(result.getString("productName"));
            }

            purchase_productName.setItems(listData);

            purchaseMedicineId();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void purchaseMedicineId(){

        String sql = "SELECT * FROM medicine WHERE productName = '"
                +purchase_productName.getSelectionModel().getSelectedItem()+"'";

        connect = database.connectDb();

        try{
            ObservableList listData = FXCollections.observableArrayList();

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while(result.next()){
                listData.add(result.getString("medicine_id"));
            }

            purchase_medID.setItems(listData);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public ObservableList<purchaseData> purchaseListData(){

        String sql = "SELECT * FROM purchase";

        ObservableList<purchaseData> listData = FXCollections.observableArrayList();
        connect = database.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            purchaseData purData;
            while (result.next()) {
                purData = new purchaseData(result.getInt("customer_id")
                        , result.getString("medicine_id")
                        , result.getString("productName")
                        , result.getString("category")
                        , result.getInt("quantity")
                        , result.getInt("price"));

                listData.add(purData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }

    private ObservableList<purchaseData> purchaseList;

    public void purchaseShowListData(){
        purchaseList = purchaseListData();

        purchase_col_productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        purchase_col_category.setCellValueFactory(new PropertyValueFactory<>("category"));
        purchase_col_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        purchase_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));

        purchase_tableView.setItems(purchaseList);

    }

    public ObservableList<customerData> customerListData1() {
        String sql = "SELECT * FROM Customer";

        ObservableList<customerData> listData1 = FXCollections.observableArrayList();

        connect = database.connectDb();

        try{
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            customerData cusData1;
            while (result.next()) {
                cusData1 = new customerData(
                        result.getInt("id"),
                        result.getString("fullName"),
                        result.getString("phoneNum"),
                        result.getDate("registrationDate"),
//                        result.getDouble("total"),
                        result.getInt("loyaltyPoints")
                );

                listData1.add(cusData1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listData1;
    }

    private ObservableList<customerData> customerList1;
    public void customerShowListData1(){
        customerList1 = customerListData1();

        customer_col_id1.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customer_col_fullName1.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        customer_col_phoneNumber1.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        customer_col_date1.setCellValueFactory(new PropertyValueFactory<>("registrationDate"));
        customer_col_points1.setCellValueFactory(new PropertyValueFactory<>("loyaltyPoints"));

        customer_tableView1.setItems(customerList1);
    }

    public ObservableList<historyData> historyListData(){
        String sql = "SELECT * FROM history";
        ObservableList<historyData> listData = FXCollections.observableArrayList();
        connect = database.connectDb();
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            historyData hisData;
            while (result.next()) {
                hisData = new historyData(result.getInt("id")
                        , result.getInt("customer_id")
                        , result.getString("customerName")
                        , result.getString("staffName")
                        , result.getInt("total")
                        , result.getDate("createdDate")
                );
                listData.add(hisData);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return listData;
    }

    private ObservableList<historyData> historyList;
    public void historyShowListData(){
        historyList = historyListData();

        history_col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        history_col_cusName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        history_col_staffName.setCellValueFactory(new PropertyValueFactory<>("staffName"));
        history_col_total.setCellValueFactory(new PropertyValueFactory<>("total"));
        history_col_date.setCellValueFactory(new PropertyValueFactory<>("createdDate"));

        // Add action column with "View Details" button
        Callback<TableColumn<historyData, Void>, TableCell<historyData, Void>> cellFactory = new Callback<TableColumn<historyData, Void>, TableCell<historyData, Void>>() {
            @Override
            public TableCell<historyData, Void> call(final TableColumn<historyData, Void> param) {
                final TableCell<historyData, Void> cell = new TableCell<historyData, Void>() {
                    
                    private final Button btn = new Button("View Details");
                    
                    {
                        btn.setStyle("-fx-background-color: #c85f77; -fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 10px;");
                        btn.setOnAction((ActionEvent event) -> {
                            historyData data = getTableView().getItems().get(getIndex());
                            showInvoiceDetails(data);
                        });
                    }
                    
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };
        
        history_col_action.setCellFactory(cellFactory);

        history_tableView.setItems(historyList);
    }

    private int historyId;
    public void historySelect(){
        historyData hisData = history_tableView.getSelectionModel().getSelectedItem();
        int num = history_tableView.getSelectionModel().getSelectedIndex();

        if ((num - 1) < - 1) {
            return;
        }

        historyId = hisData.getId();
    }

    public void showInvoiceDetails(historyData historyData) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("invoiceDetail.fxml"));
            Parent root = loader.load();
            
            InvoiceDetailController controller = loader.getController();
            controller.setInvoiceData(historyData);
            
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Invoice Details - ID: " + historyData.getId());
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.showAndWait();
            
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Failed to load invoice details: " + e.getMessage());
            alert.showAndWait();
        }
    }

    public void historySearch(){
        String sql = "SELECT * FROM history WHERE customerName LIKE ? or staffName LIKE ? or createdDate LIKE ?";

        connect = database.connectDb();

        try {
            prepare = connect.prepareStatement(sql);

            prepare.setString(1, "%" + history_search.getText() + "%");
            prepare.setString(2, "%" + history_search.getText() + "%");
            prepare.setString(3, "%" + history_search.getText() + "%");

            result = prepare.executeQuery();

            ObservableList<historyData> listData = FXCollections.observableArrayList();

            historyData hisData;

            while (result.next()) {
                hisData = new historyData(result.getInt("id"),
                        result.getInt("customer_id"),
                        result.getString("customerName"),
                        result.getString("staffName"),
                        result.getInt("total"),
                        result.getDate("createdDate"));

                listData.add(hisData);
            }

            history_tableView.setItems(listData);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void historyDelete(){
        String sql = "DELETE FROM history WHERE id = ?";
        connect = database.connectDb();

        try{
            Alert alert;
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure to delete this record?");

            Optional<ButtonType> option = alert.showAndWait();
            if(option.get() == ButtonType.OK){
                prepare = connect.prepareStatement(sql);
                prepare.setInt(1, historyId);
                prepare.executeUpdate();

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information message");
                alert.setHeaderText(null);
                alert.setContentText("Successfully deleted");
                alert.showAndWait();

                historyShowListData();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private int customerId;
    public void purchaseCustomerId(){
        customerData cusData = customer_tableView1.getSelectionModel().getSelectedItem();
        int num = customer_tableView1.getSelectionModel().getSelectedIndex();

        if( (num-1) < -1) return;

        customerId = cusData.getCustomerId();
    }

    private int totalP;
    public void purchaseAdd(){
        purchaseCustomerId();

        String sql = "INSERT INTO purchase (customer_id, medicine_id, productName, category, quantity, price)"
                + " VALUES(?,?,?,?,?,?)";
        
        String updateSql = "UPDATE purchase SET quantity = quantity + ?, price = price + ? WHERE customer_id = ? AND medicine_id = ?";
        String checkExistingSql = "SELECT quantity, price FROM purchase WHERE customer_id = ? AND medicine_id = ?";

        connect = database.connectDb();

        try{
            Alert alert;

            if(customerId == 0) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error message");
                alert.setHeaderText(null);
                alert.setContentText("Please select a customer first");
                alert.showAndWait();
                return;
            }

            if(purchase_category.getSelectionModel().getSelectedItem() == null ||
                    purchase_productName.getSelectionModel().getSelectedItem() == null ||
                    purchase_medID.getSelectionModel().getSelectedItem() == null ||
                    purchase_quantity.getText().isEmpty()){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {
                // Validate quantity input
                if (!isValidPositiveInteger(purchase_quantity.getText())) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error message");
                    alert.setHeaderText(null);
                    alert.setContentText("Quantity must be a positive number");
                    alert.showAndWait();
                    return;
                }

                int quantity = Integer.parseInt(purchase_quantity.getText());
                
                if (quantity <= 0) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error message");
                    alert.setHeaderText(null);
                    alert.setContentText("Quantity must be greater than 0");
                    alert.showAndWait();
                    return;
                }

                String checkStock = "SELECT quantity FROM medicine WHERE medicine_id = '"
                        + purchase_medID.getSelectionModel().getSelectedItem() + "'";

                statement = connect.createStatement();
                result = statement.executeQuery(checkStock);
                int stockQuantity = 0;
                if(result.next()){
                    stockQuantity = result.getInt("quantity");
                }

                if (quantity > stockQuantity) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error message");
                    alert.setHeaderText(null);
                    alert.setContentText("Not enough stock available. Current stock: " + stockQuantity);
                    alert.showAndWait();
                } else {
                    String medicineId = (String)purchase_medID.getSelectionModel().getSelectedItem();
                    
                    // Check if medicine already exists in current purchase
                    prepare = connect.prepareStatement(checkExistingSql);
                    prepare.setInt(1, customerId);
                    prepare.setString(2, medicineId);
                    result = prepare.executeQuery();
                    
                    String checkData = "SELECT price FROM medicine WHERE medicine_id = '"
                            + medicineId + "'";
                    statement = connect.createStatement();
                    ResultSet priceResult = statement.executeQuery(checkData);
                    int priceD = 0;
                    if(priceResult.next()){
                        priceD = priceResult.getInt("price");
                    }
                    
                    totalP = priceD * quantity;
                    
                    if (result.next()) {
                        // Medicine already exists, update quantity and price
                        prepare = connect.prepareStatement(updateSql);
                        prepare.setInt(1, quantity);
                        prepare.setInt(2, totalP);
                        prepare.setInt(3, customerId);
                        prepare.setString(4, medicineId);
                        prepare.executeUpdate();
                    } else {
                        // New medicine, insert new record
                        prepare = connect.prepareStatement(sql);
                        prepare.setInt(1, customerId);
                        prepare.setString(2, medicineId);
                        prepare.setString(3, (String)purchase_productName.getSelectionModel().getSelectedItem());
                        prepare.setString(4, (String)purchase_category.getSelectionModel().getSelectedItem());
                        prepare.setInt(5, quantity);
                        prepare.setInt(6, totalP);
                        prepare.executeUpdate();
                    }

                    purchaseInProgress = true;
                    purchaseShowListData();
                    totalItems();
                    if (checkDiscount.isSelected()){
                        purchaseDiscount();
                    }else{
                        discount = 0;
                        purchase_discount.setText("0 VND");
                    }
                    calculateFinalPrice();
                    
                    // Clear selection after adding
                    purchase_medID.getSelectionModel().clearSelection();
                    purchase_category.getSelectionModel().clearSelection();
                    purchase_productName.getSelectionModel().clearSelection();
                    purchase_quantity.setText("");
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private int totalItems;
    private void totalItems(){
        String sql = "SELECT SUM(price) FROM purchase WHERE customer_id = '"+customerId+"'";
        connect = database.connectDb();

        try{
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if(result.next()){
                totalItems = result.getInt("SUM(price)");
            }
            purchase_items.setText(String.valueOf(totalItems) + " VND");

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private int discount;
    public void purchaseDiscount(){
        String sql = "SELECT loyaltyPoints FROM customer WHERE id = '"+customerId+"'";
        connect = database.connectDb();

        try{
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if(result.next()){
                discount = result.getInt("loyaltyPoints") * 1000;
            }
            purchase_discount.setText(String.valueOf(discount) + " VND");

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private int finalPrice;
    public void calculateFinalPrice() {
        finalPrice = totalItems - discount;
        if (finalPrice < 0) {
            finalPrice = 0;
        }
        purchase_total.setText(String.valueOf(finalPrice) + " VND");
    }

    public void purchasePay(){
        String sql = "INSERT INTO history (customer_id, customerName, staffName, total, createdDate) "
                + "VALUES(?,?,?,?,?)";

        String sql1 = "SELECT fullName FROM customer WHERE id = ?";

        connect = database.connectDb();

        try{
            Alert alert;

            if(customerId == 0){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please select a customer to create invoice. Customer registration is required.");
                alert.showAndWait();
                return;
            }
            
            if(!purchaseInProgress || finalPrice == 0){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("No items in cart or invalid total amount");
                alert.showAndWait();
                return;
            }
            
            if(staffName.getText().isEmpty()){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please enter staff name");
                alert.showAndWait();
                return;
            }
            
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to process this payment?");
            Optional<ButtonType> option = alert.showAndWait();

            if(option.get().equals(ButtonType.OK)){
                prepare = connect.prepareStatement(sql1);
                prepare.setInt(1, customerId);
                result = prepare.executeQuery();

                String name = null;
                if (result.next()) {
                    name = result.getString("fullName");
                }

                prepare = connect.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                prepare.setInt(1, customerId);
                prepare.setString(2, name);

                String staffNameText = this.staffName.getText();
                prepare.setString(3, staffNameText);
                prepare.setInt(4, finalPrice);

                Date date = new Date();
                java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                prepare.setString(5, String.valueOf(sqlDate));

                prepare.executeUpdate();

                // Get the generated history ID
                ResultSet generatedKeys = prepare.getGeneratedKeys();
                int historyId = 0;
                if (generatedKeys.next()) {
                    historyId = generatedKeys.getInt(1);
                }

                // Save invoice details
                saveInvoiceDetails(historyId);

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Payment processed successfully!");
                alert.showAndWait();

                purchaseUpdateQuantity();
                transferToLoyaltyPoints();
                purchaseReset();
                purchaseInProgress = false;
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void saveInvoiceDetails(int historyId) {
        String sql = "INSERT INTO invoice_detail (history_id, medicine_id, productName, category, quantity, unit_price, total_price) VALUES(?,?,?,?,?,?,?)";
        
        // Get unit price for each medicine
        String getPriceSql = "SELECT price FROM medicine WHERE medicine_id = ?";
        
        connect = database.connectDb();
        
        try {
            for (purchaseData purchase : purchaseList) {
                // Get unit price
                PreparedStatement priceStmt = connect.prepareStatement(getPriceSql);
                priceStmt.setString(1, purchase.getMedicine_id());
                ResultSet priceResult = priceStmt.executeQuery();
                
                int unitPrice = 0;
                if (priceResult.next()) {
                    unitPrice = priceResult.getInt("price");
                }
                
                // Insert detail record
                prepare = connect.prepareStatement(sql);
                prepare.setInt(1, historyId);
                prepare.setString(2, purchase.getMedicine_id());
                prepare.setString(3, purchase.getProductName());
                prepare.setString(4, purchase.getCategory());
                prepare.setInt(5, purchase.getQuantity());
                prepare.setInt(6, unitPrice);
                prepare.setInt(7, purchase.getPrice()); // This is the total price for this item
                
                prepare.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void transferToLoyaltyPoints() {
        int pointsToAdd = finalPrice / 100000;
        int updatedLoyaltyPoints = pointsToAdd - discount/1000;

        String updatePointsSQL = "UPDATE customer SET loyaltyPoints = loyaltyPoints + ? WHERE id = ?";
        connect = database.connectDb();

        try {
            prepare = connect.prepareStatement(updatePointsSQL);
            prepare.setInt(1, updatedLoyaltyPoints); // Adjust loyalty points
            prepare.setInt(2, customerId);
            prepare.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Loyalty points updated successfully!");
            alert.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void purchaseUpdateQuantity() {
        String sql = "UPDATE medicine SET quantity = quantity - ?, status = CASE WHEN (quantity - ?) <= 0 THEN 'Not Available' ELSE 'Available' END WHERE medicine_id = ?";

        connect = database.connectDb();

        try {
            prepare = connect.prepareStatement(sql);

            for (purchaseData purchase : purchaseList) {
                prepare.setInt(1, purchase.getQuantity());
                prepare.setInt(2, purchase.getQuantity());
                prepare.setString(3, purchase.getMedicine_id());
                prepare.executeUpdate();
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Inventory updated successfully!");
            alert.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void purchaseReset(){
        if (customerId != 0) {
            String sql = "DELETE FROM purchase WHERE customer_id = '"+customerId+"'";
            connect = database.connectDb();

            try {
                prepare = connect.prepareStatement(sql);
                int rowsDeleted = prepare.executeUpdate();

                if (rowsDeleted > 0) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText(null);
                    alert.setContentText("Ready for a new bill.");
                    alert.showAndWait();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Reset all form fields
        staffName.setText("");
        purchase_medID.getSelectionModel().clearSelection();
        purchase_category.getSelectionModel().clearSelection();
        purchase_productName.getSelectionModel().clearSelection();
        purchase_quantity.setText("");
        purchase_items.setText("0 VND");
        purchase_discount.setText("0 VND");
        purchase_total.setText("0 VND");
        checkDiscount.setSelected(false);
        
        // Reset customer selection
        customer_tableView1.getSelectionModel().clearSelection();
        customerId = 0;
        
        // Reset purchase state
        purchaseInProgress = false;
        totalItems = 0;
        totalP = 0;
        discount = 0;
        finalPrice = 0;
        
        // Refresh the UI
        purchaseShowListData();
    }

    @FXML
    void switchForm(ActionEvent event) {
        // Check if user is switching away from purchase form with pending items
        if (purchaseInProgress && !purchase_form.isVisible()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("You have items in your cart. Switching tabs will clear your current purchase. Continue?");
            
            ButtonType continueBtn = new ButtonType("Continue");
            ButtonType stayBtn = new ButtonType("Stay", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(continueBtn, stayBtn);
            
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == stayBtn) {
                return; // Don't switch tabs
            } else {
                // Clear purchase data if user chooses to continue
                purchaseReset();
                purchaseInProgress = false;
            }
        }
        
        if(event.getSource() == dashboard_btn){
            dashboard_form.setVisible(true);
            addMedicines_form.setVisible(false);
            home_form.setVisible(false);
            customer_form.setVisible(false);
            history_form.setVisible(false);
            purchase_form.setVisible(false);

            dashboard_btn.setStyle("-fx-background-color: #fff; -fx-text-fill: #C85F77; -fx-background-radius: 40;");
            medicines_btn.setStyle("-fx-background-color: #333856");
            customer_btn.setStyle("-fx-background-color: #333856;");
            purchase_btn.setStyle("-fx-background-color: #333856;");

            // Refresh dashboard data including chart with current filter
            homeChart();
            homeTC();
            homeTI();
            
            // Refresh chart with current filter selection
            String currentFilter = revenueFilterComboBox.getValue();
            if(currentFilter != null){
                updateRevenueChart(currentFilter, null, null);
            }
        }

        if(event.getSource() == medicines_btn){
            dashboard_form.setVisible(false);
            addMedicines_form.setVisible(true);
            home_form.setVisible(false);
            customer_form.setVisible(false);
            history_form.setVisible(false);
            purchase_form.setVisible(false);

            dashboard_btn.setStyle("-fx-background-color: #333856;");
            medicines_btn.setStyle("-fx-background-color: #fff; -fx-text-fill: #C85F77; -fx-background-radius: 40;");
            customer_btn.setStyle("-fx-background-color: #333856;");
            purchase_btn.setStyle("-fx-background-color: #333856;");

            addMedicineShowListData();
            addMedicineListCategory();
            addMedicineListStatus();
            addMedicineSearch();
            addMedicineReset();
            updateButtonStates();
        }

        if(event.getSource() == customer_btn){
            dashboard_form.setVisible(false);
            addMedicines_form.setVisible(false);
            home_form.setVisible(false);
            customer_form.setVisible(true);
            history_form.setVisible(false);
            purchase_form.setVisible(false);

            dashboard_btn.setStyle("-fx-background-color: #333856;");
            medicines_btn.setStyle("-fx-background-color: #333856;");
            customer_btn.setStyle("-fx-background-color: #fff; -fx-text-fill: #C85F77; -fx-background-radius: 40;");
            purchase_btn.setStyle("-fx-background-color: #333856;");

            customerShowListData();
            customerSearch();
            customerReset();
            updateButtonStates();
        }

        if(event.getSource() == purchase_btn){
            dashboard_form.setVisible(false);
            addMedicines_form.setVisible(false);
            home_form.setVisible(false);
            customer_form.setVisible(false);
            history_form.setVisible(true);
            purchase_form.setVisible(false);

            dashboard_btn.setStyle("-fx-background-color: #333856;");
            medicines_btn.setStyle("-fx-background-color: #333856;");
            customer_btn.setStyle("-fx-background-color: #333856;");
            purchase_btn.setStyle("-fx-background-color: #fff; -fx-text-fill: #C85F77; -fx-background-radius: 40;");

            historyShowListData();
            historySearch();
        }

        if(event.getSource() == add_invoice_btn){
            dashboard_form.setVisible(false);
            addMedicines_form.setVisible(false);
            home_form.setVisible(false);
            customer_form.setVisible(false);
            history_form.setVisible(false);
            purchase_form.setVisible(true);

            dashboard_btn.setStyle("-fx-background-color: #333856;");
            medicines_btn.setStyle("-fx-background-color: #333856;");
            customer_btn.setStyle("-fx-background-color: #333856;");
            purchase_btn.setStyle("-fx-background-color: #fff; -fx-text-fill: #C85F77; -fx-background-radius: 40;");

            customerShowListData1();
            purchaseShowListData();
            purchaseCategory();
            purchaseProductName();
            purchaseMedicineId();
            customerSearch1();
        }
    }

    @FXML
    void close(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    public void minimize(ActionEvent event) {
        Stage stage = (Stage)main_form.getScene().getWindow();
        stage.setIconified(true);
    }

    public void displayUsername(){
        String user = getData.username;

        //the first letter is big, the others are small
        username.setText(user.substring(0, 1).toUpperCase() + user.substring(1));
    }

    @FXML
    void logout() throws Exception {
        try{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to log out?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get().equals(ButtonType.OK)) {
                logout.getScene().getWindow().hide();


                Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                root.setOnMouseClicked(event -> {
                    x = event.getSceneX();
                    y = event.getSceneY();
                });

                root.setOnMouseDragged(event -> {
                    stage.setX(event.getScreenX() - x);
                    stage.setY(event.getScreenY() - y);

                    stage.setOpacity(.8);
                });

                root.setOnMouseReleased(event -> {
                    stage.setOpacity(1);
                });
                stage.initStyle(StageStyle.TRANSPARENT);

                stage.setScene(scene);
                stage.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void homeTodaySales() {
        String sql = "SELECT SUM(total) FROM history WHERE DATE(createdDate) = CURDATE()";
        
        connect = database.connectDb();
        int todaySales = 0;
        
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            
            if (result.next()) {
                todaySales = result.getInt(1);
            }
            
            if (dashboard_today_sales != null) {
                dashboard_today_sales.setText(String.format("%,d VND", todaySales));
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        displayUsername();

        homeChart();
        homeTC();
        homeTI();
        homeTodaySales();

        // Initialize revenue filter ComboBox
        initializeRevenueFilter();
        
        // Initialize medicine filter ComboBoxes (with delay to ensure FXML components are loaded)
        javafx.application.Platform.runLater(() -> {
            if (filterCategory_comboBox != null && filterStatus_comboBox != null) {
                initializeMedicineFilters();
            }
        });

        addMedicineShowListData();
        addMedicineListCategory();
        addMedicineListStatus();

        historyShowListData();

        customerShowListData();
        customerShowListData1();

        purchaseShowListData();
        purchaseCategory();
        purchaseProductName();
        purchaseMedicineId();

        dashboard_form.setVisible(false);
        addMedicines_form.setVisible(false);
        home_form.setVisible(true);
        customer_form.setVisible(false);
        history_form.setVisible(false);
        purchase_form.setVisible(false);
        
        // Initialize button states
        updateButtonStates();

    }
}