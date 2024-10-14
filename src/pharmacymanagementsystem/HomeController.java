/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package pharmacymanagementsystem;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author PC
 */
public class HomeController implements Initializable {

    @FXML
    private AnchorPane main_form;

    @FXML
    private AnchorPane home_form;

    @FXML
    private AnchorPane addMedicines_form;

    @FXML
    private AnchorPane customer_form;

    @FXML
    private AnchorPane purchase_form;

    @FXML
    private Label username;

    @FXML
    private Button medicines_btn;

    @FXML
    private Button customer_btn;

    @FXML
    private Button purchase_btn;

    @FXML
    private Button logout;

    @FXML
    private TextField addMedicines_medicineID;

    @FXML
    private TextField addMedicines_productName;

    @FXML
    private TextField addMedicines_price;

    @FXML
    private TextField addMedicines_search;

    @FXML
    private ComboBox<?> addMedicines_category;

    @FXML
    private ComboBox<?> addMedicines_status;

    @FXML
    private Button addMedicines_clearBtn;

    @FXML
    private Button addMedicines_addBtn;

    @FXML
    private Button addMedicines_updateBtn;

    @FXML
    private Button addMedicines_deleteBtn;

    @FXML
    private TableView<medicineData> addMedicines_tableView;

    @FXML
    private TableColumn<medicineData, String> addMedicines_col_category;

    @FXML
    private TableColumn<medicineData, String> addMedicines_col_date;

    @FXML
    private TableColumn<medicineData, String> addMedicines_col_medicineID;

    @FXML
    private TableColumn<medicineData, String> addMedicines_col_price;

    @FXML
    private TableColumn<medicineData, String> addMedicines_col_productName;

    @FXML
    private TableColumn<medicineData, String> addMedicines_col_status;

    //DATABASE TOOLS
    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;

    public void addMedicineAdd() {

        String sql = "INSERT INTO medicine (medicine_id, productName, category, status, price, date) "
                + "VALUES(?,?,?,?,?,?)";

        connect = database.connectDb();

        try {

            Alert alert;

            if (addMedicines_medicineID.getText().isEmpty()
                    || addMedicines_productName.getText().isEmpty()
                    || addMedicines_category.getSelectionModel().getSelectedItem() == null
                    || addMedicines_status.getSelectionModel().getSelectedItem() == null
                    || addMedicines_price.getText().isEmpty()) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {
                // CHECK IF THE MEDICINE ID YOU WANT TO INSERT EXIST
                String checkData = "SELECT medicine_id FROM medicine WHERE medicine_id = '"
                        + addMedicines_medicineID.getText() + "'";

                statement = connect.createStatement();
                result = statement.executeQuery(checkData);

                if (result.next()) {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Medicine ID: " + addMedicines_medicineID.getText() + " was already exist!");
                    alert.showAndWait();
                } else {
                    prepare = connect.prepareStatement(sql);
                    prepare.setString(1, addMedicines_medicineID.getText());
                    prepare.setString(2, addMedicines_productName.getText());
                    prepare.setString(3, (String) addMedicines_category.getSelectionModel().getSelectedItem());
                    prepare.setString(4, (String) addMedicines_status.getSelectionModel().getSelectedItem());
                    prepare.setString(5, addMedicines_price.getText());

                    Date date = new Date();
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());

                    prepare.setString(6, String.valueOf(sqlDate));

                    prepare.executeUpdate();

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Added!");
                    alert.showAndWait();

                    addMedicineShowListData();
                    addMedicineReset();

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addMedicineUpdate() {

        String sql = "UPDATE medicine SET productName = '"
                + addMedicines_productName.getText() + "', type = '"
                + addMedicines_category.getSelectionModel().getSelectedItem() + "', status = '"
                + addMedicines_status.getSelectionModel().getSelectedItem() + "', price = '"
                + addMedicines_price.getText() + "', WHERE medicine_id = '"
                + addMedicines_medicineID.getText() + "'";

        connect = database.connectDb();

        try {
            Alert alert;

            if (addMedicines_medicineID.getText().isEmpty()
                    || addMedicines_productName.getText().isEmpty()
                    || addMedicines_category.getSelectionModel().getSelectedItem() == null
                    || addMedicines_status.getSelectionModel().getSelectedItem() == null
                    || addMedicines_price.getText().isEmpty()) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to UPDATE Medicine ID:" + addMedicines_medicineID.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    statement = connect.createStatement();
                    statement.executeUpdate(sql);

                    alert = new Alert(AlertType.INFORMATION);
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

    public void addMedicineDelete() {

        String sql = "DELETE FROM medicine WHERE medicine_id = '" + addMedicines_medicineID.getText() + "'";

        connect = database.connectDb();

        try {
            Alert alert;
            alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to DELETE Medicine ID:" + addMedicines_medicineID.getText() + "?");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {
                statement = connect.createStatement();
                statement.executeUpdate(sql);

                alert = new Alert(AlertType.INFORMATION);
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

    public void addMedicineReset() {
        addMedicines_medicineID.setText("");
        addMedicines_productName.setText("");
        addMedicines_price.setText("");
        addMedicines_category.getSelectionModel().clearSelection();
        addMedicines_status.getSelectionModel().clearSelection();
    }

    private String[] addMedicineListC = {"Hydrocodone", "Antibiotics", "Metformin", "Losartan", "Albuterol"};

    public void addMedicineListCategory() {
        List<String> listC = new ArrayList<>();

        for (String data : addMedicineListC) {
            listC.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(listC);
        addMedicines_category.setItems(listData);

    }

    private String[] addMedicineListS = {"Available", "Not Available"};

    public void addMedicineListStatus() {
        List<String> listS = new ArrayList<>();

        for (String data : addMedicineListS) {
            listS.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(listS);
        addMedicines_status.setItems(listData);
    }

    public ObservableList<medicineData> addMedicinesListData() {

        String sql = "SELECT * FROM medicine";

        ObservableList<medicineData> listData = FXCollections.observableArrayList();

        connect = database.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            medicineData medData;

            while (result.next()) {
                medData = new medicineData(result.getInt("medicine_id"),
                         result.getString("productName"),
                         result.getString("category"),
                         result.getString("status"),
                         result.getInt("price"),
                         result.getDate("date"));

                listData.add(medData);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }

    private ObservableList<medicineData> addMedicineList;

    public void addMedicineShowListData() {
        addMedicineList = addMedicinesListData();

        addMedicines_col_medicineID.setCellValueFactory(new PropertyValueFactory<>("medicineId"));
        addMedicines_col_productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        addMedicines_col_category.setCellValueFactory(new PropertyValueFactory<>("category"));
        addMedicines_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        addMedicines_col_status.setCellValueFactory(new PropertyValueFactory<>("status"));
        addMedicines_col_date.setCellValueFactory(new PropertyValueFactory<>("date"));

        addMedicines_tableView.setItems(addMedicineList);

    }

    public void addMedicineSearch() {
        String sql = "SELECT * FROM medicine WHERE medicine_id LIKE ? or productName LIKE ? or category LIKE ? or status LIKE ? or price LIKE ? or date LIKE ?";

        connect = database.connectDb();

        try {
            prepare = connect.prepareStatement(sql);

            prepare.setString(1, "%" + addMedicines_search.getText() + "%");
            prepare.setString(2, "%" + addMedicines_search.getText() + "%");
            prepare.setString(3, "%" + addMedicines_search.getText() + "%");
            prepare.setString(4, "%" + addMedicines_search.getText() + "%");
            prepare.setString(5, "%" + addMedicines_search.getText() + "%");
            prepare.setString(6, "%" + addMedicines_search.getText() + "%");

            result = prepare.executeQuery();

            ObservableList<medicineData> listData = FXCollections.observableArrayList();

            medicineData medData;

            while (result.next()) {
                medData = new medicineData(result.getInt("medicine_id"),
                         result.getString("productName"),
                         result.getString("category"),
                         result.getString("status"),
                         result.getInt("price"),
                         result.getDate("date"));

                listData.add(medData);
            }

            addMedicines_tableView.setItems(listData);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addMedicineSelect() {
        medicineData medData = addMedicines_tableView.getSelectionModel().getSelectedItem();
        int num = addMedicines_tableView.getSelectionModel().getSelectedIndex();

        if ((num - 1) < - 1) {
            return;
        }

        addMedicines_medicineID.setText(String.valueOf(medData.getMedicineId()));
        addMedicines_productName.setText(medData.getProductName());
        addMedicines_price.setText(String.valueOf(medData.getPrice()));

    }

    public void switchForm(ActionEvent event) {

        if (event.getSource() == medicines_btn) {
            home_form.setVisible(false);
            addMedicines_form.setVisible(true);
            customer_form.setVisible(false);
            purchase_form.setVisible(false);

            medicines_btn.setStyle("-fx-background-color: #fff; -fx-text-fill: #c85f77;-fx-background-radius: 40;");
            customer_btn.setStyle("-fx-background-color:transparent");
            purchase_btn.setStyle("-fx-background-color: transparent");

        } else if (event.getSource() == customer_btn) {
            home_form.setVisible(false);
            addMedicines_form.setVisible(false);
            customer_form.setVisible(true);
            purchase_form.setVisible(false);

            medicines_btn.setStyle("-fx-background-color:transparent");
            customer_btn.setStyle("-fx-background-color: #fff; -fx-text-fill: #c85f77;-fx-background-radius: 40;");
            purchase_btn.setStyle("-fx-background-color: transparent");

            addMedicineShowListData();
            addMedicineListCategory();
            addMedicineListStatus();
            addMedicineSearch();

        } else if (event.getSource() == purchase_btn) {
            home_form.setVisible(false);
            addMedicines_form.setVisible(false);
            customer_form.setVisible(false);
            purchase_form.setVisible(true);

            medicines_btn.setStyle("-fx-background-color:transparent");
            customer_btn.setStyle("-fx-background-color:transparent");
            purchase_btn.setStyle("-fx-background-color: #fff; -fx-text-fill: #c85f77;-fx-background-radius: 40;");

        }

    }

    public void displayUsername() {
        String user = getUser.username;
        // BIG LETTER THE FIRST LETTER THEN THE REST ARE SMALL LETTER
        username.setText(user.substring(0, 1).toUpperCase() + user.substring(1));

    }

    private double x = 0;
    private double y = 0;

    public void logout() {

        try {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to logout?");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {
                // HIDE THE DASHBOARD FORM
                logout.getScene().getWindow().hide();
                // LINK YOUR LOGIN FORM
                Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root);

                root.setOnMousePressed((MouseEvent event) -> {
                    x = event.getSceneX();
                    y = event.getSceneY();
                });

                root.setOnMouseDragged((MouseEvent event) -> {
                    stage.setX(event.getScreenX() - x);
                    stage.setY(event.getScreenY() - y);

                    stage.setOpacity(.8);
                });

                root.setOnMouseReleased((MouseEvent event) -> {
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

    public void minimize() {
        Stage stage = (Stage) main_form.getScene().getWindow();
        stage.setIconified(true);
    }

    public void close() {
        System.exit(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        displayUsername();
        addMedicineShowListData();
        addMedicineListCategory();
        addMedicineListStatus();
    }

}
