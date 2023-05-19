// Curtis C
// ID: *********
// myemail@wgu.edu

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

/** Controller for the Add Product screen. */
public class AddProductController implements Initializable {

    Stage stage;
    Parent scene;


    @FXML
    private TableColumn<Part, Integer> addProductAssocInvCol;

    @FXML
    private TableColumn<Part, Integer> addProductAssocPartIdCol;

    @FXML
    private TableColumn<Part, String> addProductAssocPartNameCol;

    @FXML
    private TableColumn<Part, Double> addProductAssocPriceCol;

    @FXML
    private TableView<Part> addProductAssociatedPartsTableView;

    @FXML
    private TextField addProductIdTxt;

    @FXML
    private TableColumn<Part, Integer> addProductInvCol;

    @FXML
    private TextField addProductInvTxt;

    @FXML
    private TextField addProductMaxTxt;

    @FXML
    private TextField addProductMinTxt;

    @FXML
    private TextField addProductNameTxt;

    @FXML
    private TableColumn<Part, Integer> addProductPartIdCol;

    @FXML
    private TableColumn<Part, String> addProductPartNameCol;

    @FXML
    private TableView<Part> addProductPartTableView;

    @FXML
    private TableColumn<Part, Double> addProductPriceCol;

    @FXML
    private TextField addProductPriceTxt;

    @FXML
    private TextField addProductSearchTxt;

    Product newProduct = new Product(0, null, 0.0, 0, 0, 0);

    /** Add Button for the parts/associated parts tables. Adds a part from the available parts to the associated parts table. */
    @FXML
    void onAddProductAdd(ActionEvent event) {

        Part selectedPart = addProductPartTableView.getSelectionModel().getSelectedItem();
        newProduct.addAssociatedParts(selectedPart);
        addProductAssociatedPartsTableView.setItems(newProduct.getAllAssociatedParts());
    }

    /** Remove Associated Part Button for Add Product screen. Removes selected part from the associated parts table and throws error if no part is selected to remove. */
    @FXML
    void onAddProductDelete(ActionEvent event) {

        Part partToRemove = (Part)addProductAssociatedPartsTableView.getSelectionModel().getSelectedItem();

        if (addProductAssociatedPartsTableView.getSelectionModel().getSelectedItem() == null) {

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

                Part selectedPart = addProductAssociatedPartsTableView.getSelectionModel().getSelectedItem();

                newProduct.deleteAssociatedPart(selectedPart);
            }
        }
    }

    /** Search field for the parts table on the Add Product screen. */
    @FXML
    void onAddProductPartSearch(ActionEvent event) {

        String searchText = addProductSearchTxt.getText();

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

        addProductPartTableView.setItems(parts);
        addProductSearchTxt.clear();
    }

    /** Save Button on Add Product screen. Saves input information and returns to the main screen, also throws errors if input data is invalid or has logical errors. */
    @FXML
    void onAddProductSave(ActionEvent event) throws IOException {

        try {

            newProduct.setId(Inventory.getNewProductId());
            newProduct.setName(addProductNameTxt.getText());
            newProduct.setStock(Integer.parseInt(addProductInvTxt.getText()));
            newProduct.setPrice(Double.parseDouble(addProductPriceTxt.getText()));
            newProduct.setMax(Integer.parseInt(addProductMaxTxt.getText()));
            newProduct.setMin(Integer.parseInt(addProductMinTxt.getText()));

            if (newProduct.getName().isEmpty()) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Product name error");
                alert.setContentText("Product name cannot be left blank.");
                alert.showAndWait();
                return;
            }

            if (newProduct.getMin() > newProduct.getMax()) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Min/Max Error");
                alert.setContentText("Minimum amount in inventory cannot be more than the maximum amount.");
                alert.showAndWait();
                return;
            }

            if (newProduct.getStock() > newProduct.getMax() || newProduct.getStock() < newProduct.getMin()) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Inventory error");
                alert.setContentText("Product inventory amount must be between the minimum and maximum values.");
                alert.showAndWait();
                return;
            }

            Inventory.addProduct(newProduct);

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

    /** Cancel Button on Add Product screen. Confirms cancellation, clears input data, and returns back to the main screen. */
    @FXML
    void onDisplayMainScreen(ActionEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Add Product");
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

    /** Initialization method for the Add Product screen. Populates the parts tables.
     * <p><b>
     *     I had a couple issues trying to implement this section. The first issue wasn't necessarily an error, but more of an oversight on my part. The ID of the tables were the only column that would populate and it was populating it with the price.
     *     I realized that I forgot to change the column names individually after I copied/pasted them. I also had a small issue with misspelling the value names.
     * </b></p>
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        addProductPartTableView.setItems(Inventory.getAllParts());
        addProductPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        addProductPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addProductInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addProductPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));


        addProductAssocPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        addProductAssocPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addProductAssocInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addProductAssocPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}
