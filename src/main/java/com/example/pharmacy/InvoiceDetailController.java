package com.example.pharmacy;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class InvoiceDetailController implements Initializable {
    
    @FXML
    private Label invoiceIdLabel;
    
    @FXML
    private Label customerNameLabel;
    
    @FXML
    private Label staffNameLabel;
    
    @FXML
    private Label totalLabel;
    
    @FXML
    private Label dateLabel;
    
    @FXML
    private TableView<invoiceDetailData> detailTableView;
    
    @FXML
    private TableColumn<invoiceDetailData, String> col_medicineId;
    
    @FXML
    private TableColumn<invoiceDetailData, String> col_productName;
    
    @FXML
    private TableColumn<invoiceDetailData, String> col_category;
    
    @FXML
    private TableColumn<invoiceDetailData, Integer> col_quantity;
    
    @FXML
    private TableColumn<invoiceDetailData, Integer> col_unitPrice;
    
    @FXML
    private TableColumn<invoiceDetailData, Integer> col_totalPrice;
    
    @FXML
    private Button closeButton;
    
    @FXML
    private Button printButton;

    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;
    
    private int invoiceId;
    
    public void setInvoiceData(historyData historyData) {
        this.invoiceId = historyData.getId();
        
        invoiceIdLabel.setText("Invoice ID: " + historyData.getId());
        customerNameLabel.setText("Customer: " + historyData.getCustomerName());
        staffNameLabel.setText("Staff: " + historyData.getStaffName());
        totalLabel.setText("Total: " + String.format("%,d VND", historyData.getTotal()));
        dateLabel.setText("Date: " + historyData.getCreatedDate().toString());
        
        loadInvoiceDetails();
    }
    
    private ObservableList<invoiceDetailData> detailList = FXCollections.observableArrayList();
    
    private void loadInvoiceDetails() {
        detailList.clear();
        
        String sql = "SELECT * FROM invoice_detail WHERE history_id = ?";
        connect = database.connectDb();
        
        try {
            prepare = connect.prepareStatement(sql);
            prepare.setInt(1, invoiceId);
            result = prepare.executeQuery();
            
            while (result.next()) {
                invoiceDetailData detail = new invoiceDetailData(
                    result.getInt("id"),
                    result.getInt("history_id"),
                    result.getString("medicine_id"),
                    result.getString("productName"),
                    result.getString("category"),
                    result.getInt("quantity"),
                    result.getInt("unit_price"),
                    result.getInt("total_price")
                );
                detailList.add(detail);
            }
            
            detailTableView.setItems(detailList);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void closeModal() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    private void printInvoice() {
        // Implement print functionality here
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Print Invoice");
        alert.setHeaderText(null);
        alert.setContentText("Print functionality will be implemented here.");
        alert.showAndWait();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        col_medicineId.setCellValueFactory(new PropertyValueFactory<>("medicineId"));
        col_productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        col_category.setCellValueFactory(new PropertyValueFactory<>("category"));
        col_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        col_unitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        col_totalPrice.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        
        // Format price columns
        col_unitPrice.setCellFactory(column -> new TableCell<invoiceDetailData, Integer>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(String.format("%,d VND", item));
                }
            }
        });
        
        col_totalPrice.setCellFactory(column -> new TableCell<invoiceDetailData, Integer>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(String.format("%,d VND", item));
                }
            }
        });
    }
}
