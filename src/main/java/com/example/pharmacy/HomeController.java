package com.example.pharmacy;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
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
    private AreaChart<?, ?> dashboard_chart;

    @FXML
    private Label dashboard_customer;

    @FXML
    private Label dashboard_income;

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

    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;

    private double x = 0;
    private double y = 0;

    public void homeChart(){
        dashboard_chart.getData().clear();

        String sql = "SELECT createdDate, SUM(total) FROM history"
                + " GROUP BY createdDate ORDER BY TIMESTAMP(createdDate) ASC LIMIT 9";

        connect = database.connectDb();

        try{
            XYChart.Series chart = new XYChart.Series();

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while(result.next()){
                chart.getData().add(new XYChart.Data(result.getString(1), result.getInt(2)));
            }

            dashboard_chart.getData().add(chart);

        }catch(Exception e){e.printStackTrace();}

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
                    prepare = connect.prepareStatement(sql);
                    prepare.setString(1, addMedicines_medicineID.getText());
                    prepare.setString(2, addMedicines_productName.getText());
                    prepare.setString(3, (String) addMedicines_category.getSelectionModel().getSelectedItem());
                    prepare.setString(4, addMedicines_quantity.getText());
                    prepare.setString(5, addMedicines_price.getText());
                    prepare.setString(6, (String) addMedicines_status.getSelectionModel().getSelectedItem());

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
                    || addMedicines_price.getText().isEmpty()
                    || addMedicines_status.getSelectionModel().getSelectedItem() == null) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to UPDATE Medicine ID:" + addMedicines_medicineID.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    prepare = connect.prepareStatement(sql);
                    prepare.setString(1, addMedicines_productName.getText());
                    prepare.setString(2, (String) addMedicines_category.getSelectionModel().getSelectedItem());
                    prepare.setString(3, addMedicines_quantity.getText());
                    prepare.setString(4, addMedicines_price.getText());
                    prepare.setString(5, (String) addMedicines_status.getSelectionModel().getSelectedItem());
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
        String sql = "SELECT * FROM medicine WHERE medicine_id LIKE ? or productName LIKE ? or category LIKE ? or price LIKE ? or status LIKE ?";

        connect = database.connectDb();

        try {
            prepare = connect.prepareStatement(sql);

            prepare.setString(1, "%" + addMedicines_search.getText() + "%");
            prepare.setString(2, "%" + addMedicines_search.getText() + "%");
            prepare.setString(3, "%" + addMedicines_search.getText() + "%");
            prepare.setString(4, "%" + addMedicines_search.getText() + "%");
            prepare.setString(5, "%" + addMedicines_search.getText() + "%");

            result = prepare.executeQuery();

            ObservableList<medicineData> listData = FXCollections.observableArrayList();

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

            addMedicines_tableView.setItems(listData);

        } catch (Exception e) {
            e.printStackTrace();
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

        connect = database.connectDb();

        try{
            Alert alert;

            if(purchase_category.getSelectionModel().getSelectedItem() == null ||
                    purchase_productName.getSelectionModel().getSelectedItem() == null ||
                    purchase_medID.getSelectionModel().getSelectedItem() == null ||
                    purchase_quantity.getText().isEmpty()){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            }else{

                int quantity = Integer.parseInt(purchase_quantity.getText());

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
                    prepare = connect.prepareStatement(sql);
                    prepare.setInt(1, customerId);
                    prepare.setString(2, (String)purchase_medID.getSelectionModel().getSelectedItem());
                    prepare.setString(3, (String)purchase_productName.getSelectionModel().getSelectedItem());
                    prepare.setString(4, (String)purchase_category.getSelectionModel().getSelectedItem());
                    prepare.setInt(5, quantity);

                    String checkData = "SELECT price FROM medicine WHERE medicine_id = '"
                            +purchase_medID.getSelectionModel().getSelectedItem()+"'";

                    result = statement.executeQuery(checkData);
                    int priceD = 0;
                    if(result.next()){
                        priceD = result.getInt("price");
                    }

                    totalP = priceD * quantity;

                    prepare.setInt(6, totalP);

                    prepare.executeUpdate();

                    purchaseShowListData();
                    totalItems();
                    if (checkDiscount.isSelected()){
                        purchaseDiscount();
                    }else{
                        discount = 0;
                        purchase_discount.setText("0 VND");
                    }
                    calculateFinalPrice();
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

            if(finalPrice == 0){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Something wrong :3");
                alert.showAndWait();
            }else{
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText(null);
                alert.setContentText("Are you sure?");
                Optional<ButtonType> option = alert.showAndWait();

                if(option.get().equals(ButtonType.OK)){
                    prepare = connect.prepareStatement(sql1);
                    prepare.setInt(1, customerId);
                    result = prepare.executeQuery();

                    String name = null;
                    if (result.next()) {
                        name = result.getString("fullName");
                    }

                    prepare = connect.prepareStatement(sql);
                    prepare.setInt(1, customerId);
                    prepare.setString(2, name);

                    String staffName = this.staffName.getText();
                    prepare.setString(3, staffName);
                    prepare.setInt(4, finalPrice);

                    Date date = new Date();
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                    prepare.setString(5, String.valueOf(sqlDate));

                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setContentText("Successful!");
                    alert.showAndWait();

                    purchaseUpdateQuantity();
                    transferToLoyaltyPoints();
                    purchaseReset();
                }
            }

        }catch(Exception e){
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
        String sql = "UPDATE medicine SET quantity = quantity - ? WHERE medicine_id = ?";

        connect = database.connectDb();

        try {
            prepare = connect.prepareStatement(sql);

            for (purchaseData purchase : purchaseList) {
                prepare.setInt(1, purchase.getQuantity());
                prepare.setString(2, purchase.getMedicine_id());
                prepare.executeUpdate();
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Quantity of drugs updated successfully!");
            alert.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void purchaseReset(){
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

            // Refresh the UI to reflect the cleared data
            staffName.setText("");
            purchase_medID.getSelectionModel().clearSelection();
            purchase_category.getSelectionModel().clearSelection();
            purchase_productName.getSelectionModel().clearSelection();
            purchase_quantity.setText("");
            purchase_items.setText("0 VND");
            purchase_discount.setText("0 VND");
            purchase_total.setText("0 VND");
            purchaseShowListData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void switchForm(ActionEvent event) {
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

            homeChart();
            homeTC();
            homeTI();
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
            purchaseReset();
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        displayUsername();

        homeChart();
        homeTC();
        homeTI();

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

    }
}