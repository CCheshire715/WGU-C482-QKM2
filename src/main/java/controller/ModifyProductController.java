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

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/** Controller for the Modify Product screen. */
public class ModifyProductController implements Initializable {

    Stage stage;
    Parent scene;

    private int productId;

    private Product selectedProduct;

    @FXML
    private TableColumn<Part, Integer> modifyProductAssocInvCol;

    @FXML
    private TableColumn<Part, Integer> modifyProductAssocPartIdCol;

    @FXML
    private TableColumn<Part, String> modifyProductAssocPartNameCol;

    @FXML
    private TableColumn<Part, Double> modifyProductAssocPriceCol;

    @FXML
    private TableView<Part> modifyProductAssociatedTableView;

    @FXML
    private TextField modifyProductIdTxt;

    @FXML
    private TableColumn<Part, Integer> modifyProductInvCol;

    @FXML
    private TextField modifyProductInvTxt;

    @FXML
    private TextField modifyProductMaxTxt;

    @FXML
    private TextField modifyProductMinTxt;

    @FXML
    private TextField modifyProductNameTxt;

    @FXML
    private TableColumn<Part, Integer> modifyProductPartIdCol;

    @FXML
    private TableColumn<Part, String> modifyProductPartNameCol;

    @FXML
    private TableColumn<Part, Double> modifyProductPriceCol;

    @FXML
    private TextField modifyProductPriceTxt;

    @FXML
    private TextField modifyProductSearchTxt;

    @FXML
    private TableView<Part> modifyProductTableView;

    /** Cancel Button on Modify Product screen. Confirms cancellation, clears input fields, and returns back to the main screen. */
    @FXML
    void onDisplayMainScreen(ActionEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Modify Product");
        alert.setHeaderText("Product Cancellation");
        alert.setContentText("This will cancel all changes and return you to the main menu. Continue?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/cheshire/cheshirec482pa/mainScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /** Add Button for associated parts table on Modify Product screen. Adds selected part from the table and adds it to the associated parts table. */
    @FXML
    void onModifyProductAdd(ActionEvent event) {

        Part selectedPart = modifyProductTableView.getSelectionModel().getSelectedItem();
        selectedProduct.addAssociatedParts(selectedPart);
        modifyProductAssociatedTableView.setItems(selectedProduct.getAllAssociatedParts());
    }

    /** Remove Associated Part Button on Modify Product screen. Confirms part deletion and throws an error if no part is selected. */
    @FXML
    void onModifyProductDeleteAssocPart(ActionEvent event) {

        Part partToRemove = (Part)modifyProductAssociatedTableView.getSelectionModel().getSelectedItem();

        if (modifyProductAssociatedTableView.getSelectionModel().getSelectedItem() == null) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Selection Error");
            alert.setContentText("Please select a part to delete.");
            alert.showAndWait();
        } else {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Associated Parts");
            alert.setHeaderText("Part Removal");
            alert.setContentText("This will remove an associated part from the product. Continue?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {

                Part selectedPart = modifyProductAssociatedTableView.getSelectionModel().getSelectedItem();

                selectedProduct.deleteAssociatedPart(selectedPart);
            }
        }
    }

    /** Search field for the parts table on the Modify Product screen. */
    @FXML
    void onModifyProductPartSearch(ActionEvent event) {

        String searchText = modifyProductSearchTxt.getText();

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

        modifyProductTableView.setItems(parts);
        modifyProductSearchTxt.clear();
    }

    /** Save Button on the Modify Product screen. Saves input data and returns to the main screen, also throws errors for invalid data/logical errors. */
    @FXML
    void onModifyProductSave(ActionEvent event) throws IOException {

        try {

            selectedProduct.setId(Integer.parseInt(modifyProductIdTxt.getText()));
            selectedProduct.setName(modifyProductNameTxt.getText());
            selectedProduct.setPrice(Double.parseDouble(modifyProductPriceTxt.getText()));
            selectedProduct.setStock(Integer.parseInt(modifyProductInvTxt.getText()));
            selectedProduct.setMin(Integer.parseInt(modifyProductMinTxt.getText()));
            selectedProduct.setMax(Integer.parseInt(modifyProductMaxTxt.getText()));

            if (selectedProduct.getName().isEmpty()) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Product name error");
                alert.setContentText("Product name cannot be left blank.");
                alert.showAndWait();
                return;
            }

            if (selectedProduct.getMin() > selectedProduct.getMax()) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Min/Max Error");
                alert.setContentText("Minimum amount in inventory cannot be more than the maximum amount.");
                alert.showAndWait();
                return;
            }

            if (selectedProduct.getStock() > selectedProduct.getMax() || selectedProduct.getStock() < selectedProduct.getMin()) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Inventory error");
                alert.setContentText("Product inventory amount must be between the minimum and maximum values.");
                alert.showAndWait();
                return;
            }

            Inventory.updateProduct(productId, selectedProduct);

            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/cheshire/cheshirec482pa/mainScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();

        } catch (NumberFormatException e) {

            Alert alert = new Alert (Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Data input error");
            alert.setContentText("Please enter a valid value for each text field.");
            alert.showAndWait();
        }


    }

    /** Method to populate product table in Modify Product form.
     @param product Product selected
     */
    public void sendModifyProduct(Product product) {
        selectedProduct = product;

        productId = Inventory.getAllProducts().indexOf(selectedProduct);
        modifyProductIdTxt.setText(String.valueOf(selectedProduct.getId()));
        modifyProductNameTxt.setText(selectedProduct.getName());
        modifyProductInvTxt.setText(String.valueOf(selectedProduct.getStock()));
        modifyProductPriceTxt.setText(String.valueOf(selectedProduct.getPrice()));
        modifyProductMaxTxt.setText(String.valueOf(selectedProduct.getMax()));
        modifyProductMinTxt.setText(String.valueOf(selectedProduct.getMin()));
        modifyProductAssociatedTableView.setItems(selectedProduct.getAllAssociatedParts());
    }

    /** Initialization method for the Modify Product screen. Populates the parts tables. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        modifyProductAssocPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        modifyProductAssocPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        modifyProductAssocInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modifyProductAssocPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        modifyProductTableView.setItems(Inventory.getAllParts());
        modifyProductPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        modifyProductPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        modifyProductInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modifyProductPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}