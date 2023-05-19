// Curtis Cheshire
// ID: 010713063
// cchesh3@wgu.edu

package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

import javax.swing.table.TableColumn;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/** Controller for the main screen. */
public class MainScreenController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private TableColumn<Part, Integer> partIdCol;

    @FXML
    private TableColumn<Part, Integer> partInventoryCol;

    @FXML
    private TableColumn<Part, String> partNameCol;

    @FXML
    private TableColumn<Part, Double> partPriceCol;

    @FXML
    private TextField partSearch;

    @FXML
    private TableView<Part> partsTableView;

    @FXML
    private TableColumn<Product, Integer> productIdCol;

    @FXML
    private TableColumn<Product, Integer> productInventoryCol;

    @FXML
    private TableColumn<Product, String> productNameCol;

    @FXML
    private TableColumn<Product, Double> productPriceCol;

    @FXML
    private TextField productSearch;

    @FXML
    private TableView<Product> productsTableView;

    /** Add button that switches to the Add Part screen. */
    @FXML
    void onAddPart(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/cheshire/cheshirec482pa/addPart.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** Add button that switches to the Add Product screen. */
    @FXML
    void onAddProduct(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/cheshire/cheshirec482pa/addProduct.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** Delete Button to delete selected Parts. Throws error if nothing is selected and confirms deletion. */
    @FXML
    void onDeletePart(ActionEvent event) {

        Part selectedPart = (Part) partsTableView.getSelectionModel().getSelectedItem();
        if (selectedPart == null) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Selection Error");
            alert.setContentText("Please select a part to delete.");
            alert.showAndWait();

        } else {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Delete Part");
            alert.setContentText("This part will be deleted. Continue?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {

                Inventory.deletePart(selectedPart);
            }
        }

    }

    /** Delete Button that deletes the selected Product. Will not delete the product if there are any associated parts and also confirms deletion. */
    @FXML
    void onDeleteProduct(ActionEvent event) {

        Product selectedProduct = (Product)productsTableView.getSelectionModel().getSelectedItem();
        if (selectedProduct != null && selectedProduct.getAllAssociatedParts().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Delete Product");
            alert.setContentText("This will delete the product. Continue?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                Inventory.deleteProduct(selectedProduct);
            }
        } else if (selectedProduct != null && selectedProduct.getAllAssociatedParts().size() > 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Associated Parts");
            alert.setContentText("This product cannot be deleted because it has parts associated with it. Please delete the parts before deleting the product.");
            alert.showAndWait();
        } else {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Selection Error");
            alert.setContentText("Please select a product to delete.");
            alert.showAndWait();
        }
    }

    /** Exit Button exits the application. Confirms whether the user wants to exit. */
    @FXML
    void onExit(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Exit Application");
        alert.setContentText("This will close the application. Continue?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK){

            System.exit(0);
        }
    }

    /** Modify Button switches to modify part screen. Throws error if a part is not selected. */
    @FXML
    void onModifyPart(ActionEvent event) throws IOException {

        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/cheshire/cheshirec482pa/modifyPart.fxml"));
            loader.load();

            ModifyPartController MPController = loader.getController();
            MPController.sendModifyPart(partsTableView.getSelectionModel().getSelectedItem());

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        } catch (Exception e) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Selection error");
            alert.setContentText("Please select a part to modify.");
            alert.showAndWait();
        }
    }

    /** Modify Button switches to the modify product screen. Throws error if no product is selected. */
    @FXML
    void onModifyProduct(ActionEvent event) throws IOException {

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/cheshire/cheshirec482pa/modifyProduct.fxml"));
            loader.load();

            ModifyProductController MPController = loader.getController();
            MPController.sendModifyProduct(productsTableView.getSelectionModel().getSelectedItem());

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        } catch (Exception e) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Selection error");
            alert.setContentText("Please select a product to modify.");
            alert.showAndWait();
        }
    }

    /** Search field for the parts table. */
    @FXML
    void onPartSearch(ActionEvent event) {

        String searchText = partSearch.getText();

        ObservableList<Part> parts = Inventory.lookupPart(searchText);

        if (parts.isEmpty()) {
            try {
                int searchId = Integer.parseInt(searchText);
                Part searchPart = Inventory.lookupPart(searchId);
                if (searchPart != null) {
                    parts.add(searchPart);
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning");
                    alert.setHeaderText("No Match");
                    alert.setContentText("No part matches found.");
                    alert.showAndWait();
                }
            }
            catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("No Match");
                alert.setContentText("No part matches found.");
                alert.showAndWait();
            }
        }

        partsTableView.setItems(parts);
        partSearch.clear();
    }

    /** Search field for the product table. */
    @FXML
    void onProductSearch(ActionEvent event) {

        String searchText = productSearch.getText();

        ObservableList<Product> products = Inventory.lookupProduct(searchText);

        if (products.isEmpty()) {
            try {
                int searchId = Integer.parseInt(searchText);
                Product searchProduct = Inventory.lookupProduct(searchId);
                if (searchProduct != null) {
                    products.add(searchProduct);
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning");
                    alert.setHeaderText("No Match");
                    alert.setContentText("No part matches found.");
                    alert.showAndWait();
                }
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("No Match");
                alert.setContentText("No part matches found.");
                alert.showAndWait();
            }
        }
        productsTableView.setItems(products);
        productSearch.clear();
    }

    /** Initialization method that populates the tables on the main screen. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        partsTableView.setItems(Inventory.getAllParts());
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        productsTableView.setItems(Inventory.getAllProducts());
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}
