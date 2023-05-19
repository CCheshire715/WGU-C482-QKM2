// Curtis C
// ID: *********
// myemail@wgu.edu

package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.OutSourced;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/** Controller for the Add Part screen. */
public class AddPartController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private RadioButton addPartInHouseRBtn;

    @FXML
    private RadioButton addPartOutsourcedRBtn;

    @FXML
    private ToggleGroup inHouseTG;

    @FXML
    private Label machCompanyId;

    @FXML
    private TextField machCompanyTxt;

    @FXML
    private TextField partIdTxt;

    @FXML
    private TextField partInvTxt;

    @FXML
    private TextField partMaxTxt;

    @FXML
    private TextField partMinTxt;

    @FXML
    private TextField partNameTxt;

    @FXML
    private TextField partPriceTxt;

    /** Cancel Button on the Add Part screen. Cancels change to the text fields, confirms decision to cancel, and returns to the main screen. */
    @FXML
    void onDisplayMainScreen(ActionEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Add Part Cancellation");
        alert.setContentText("This will cancel all changes and return you to the main menu. Continue?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/cheshire/cheshirec482pa/mainScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /** Radio Button that toggles between In House and Outsourced parts. */
    @FXML
    void onInHouseRBtn(ActionEvent event) {

        machCompanyId.setText("Machine ID");
    }

    /** Radio Button that toggles between In House and Outsourced parts. */
    @FXML
    void onOutsourcedRBtn(ActionEvent event) {

        machCompanyId.setText("Company Name");
    }

    /** Save Button for the Add Part screen. Throws errors for blank fields and logical errors as well as saving data and returning to the main screen. */
    @FXML
    void onSave(ActionEvent event) throws IOException {

        try {
            String name = partNameTxt.getText();
            int stock = Integer.parseInt(partInvTxt.getText());
            double price = Double.parseDouble(partPriceTxt.getText());
            int max = Integer.parseInt(partMaxTxt.getText());
            int min = Integer.parseInt(partMinTxt.getText());

            if (partNameTxt.getText().isEmpty()) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Part name error");
                alert.setContentText("Part name cannot be left blank.");
                alert.showAndWait();
                return;
            }

            if (min > max) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Min/Max Error");
                alert.setContentText("Minimum amount in inventory cannot be more than the maximum amount.");
                alert.showAndWait();
                return;
            }

            if (stock > max || stock < min) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Inventory error");
                alert.setContentText("Part inventory amount must be between the minimum and maximum values.");
                alert.showAndWait();
                return;
            }

            if (addPartInHouseRBtn.isSelected()) {

                int machineId = Integer.parseInt(machCompanyTxt.getText());

                Inventory.addPart(new InHouse(Inventory.getNewPartId(), name, price, stock, min, max, machineId));

            } else {

                String companyName = machCompanyTxt.getText();

                if (machCompanyTxt.getText().isEmpty()) {

                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Company Name Error");
                    alert.setContentText("Company name cannot be blank.");
                    alert.showAndWait();
                    return;
                }
                Inventory.addPart(new OutSourced(Inventory.getNewPartId(), name, price, stock, min, max, companyName));
            }

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

    /** Initialization method for the Add Parts screen. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
