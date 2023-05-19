// Curtis Cheshire
// ID: 010713063
// cchesh3@wgu.edu

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
import model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/** Controller for the Modify Part screen. */
public class ModifyPartController implements Initializable {

    Stage stage;
    Parent scene;

    private int partId;

    private Part part;

    @FXML
    private ToggleGroup inHouseTG;

    @FXML
    private Label modifyMachCompanyId;

    @FXML
    private TextField modifyMachCompanyTxt;

    @FXML
    private TextField modifyPartIdTxt;

    @FXML
    private RadioButton modifyPartInHouseRBtn;

    @FXML
    private TextField modifyPartInvTxt;

    @FXML
    private TextField modifyPartMaxTxt;

    @FXML
    private TextField modifyPartMinTxt;

    @FXML
    private TextField modifyPartNameTxt;

    @FXML
    private RadioButton modifyPartOutsourcedRBtn;

    @FXML
    private TextField modifyPartPriceTxt;

    /** Cancel Button for modify part screen. Confirms cancellation, clears input data, and returns back to the main screen. */
    @FXML
    void onDisplayMainScreen(ActionEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Part Cancellation");
        alert.setContentText("This will cancel all changes and return you to the main menu. Continue?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/cheshire/cheshirec482pa/mainScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /** Radio Button for the Modify Part screen. Toggles between In House and Outsourced parts. */
    @FXML
    void onModifyInHouseRBtn(ActionEvent event) {

        modifyMachCompanyId.setText("Machine ID");
    }

    /** Radio Button for the Modify Part screen. Toggles between In House and Outsourced parts. */
    @FXML
    void onModifyOutsourcedRBtn(ActionEvent event) {

        modifyMachCompanyId.setText("Company Name");
    }

    /** Save Button for Modify Parts screen. Saves input data and returns back to main screen, throws error for blank fields or logical errors. */
    @FXML
    void onModifySave(ActionEvent event) throws IOException {

        try {
            int id = Integer.parseInt(modifyPartIdTxt.getText());
            String name = modifyPartNameTxt.getText();
            double price = Double.parseDouble(modifyPartPriceTxt.getText());
            int stock = Integer.parseInt(modifyPartInvTxt.getText());
            int min = Integer.parseInt(modifyPartMinTxt.getText());
            int max = Integer.parseInt(modifyPartMaxTxt.getText());

            if (modifyPartNameTxt.getText().isEmpty()) {

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

            if (modifyPartInHouseRBtn.isSelected()) {

                int machineId = Integer.parseInt(modifyMachCompanyTxt.getText());
                InHouse inHouse = new InHouse(id, name, price, stock, min, max, machineId);
                Inventory.updatePart(partId, inHouse);
            } else if (modifyPartOutsourcedRBtn.isSelected()) {

                String companyName = modifyMachCompanyTxt.getText();

                if (modifyMachCompanyTxt.getText().isEmpty()) {

                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Company Name Error");
                    alert.setContentText("Company name cannot be blank.");
                    alert.showAndWait();
                    return;
                }
                OutSourced outSourced = new OutSourced(id, name, price, stock, min, max, companyName);
                Inventory.updatePart(partId, outSourced);
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

    /** Method to populate part table in Modify Part form.
     @param part Part selected
     */
    public void sendModifyPart(Part part) {

        partId = Inventory.getAllParts().indexOf(part);
        modifyPartIdTxt.setText(String.valueOf(part.getId()));
        modifyPartNameTxt.setText(part.getName());
        modifyPartInvTxt.setText(String.valueOf(part.getStock()));
        modifyPartPriceTxt.setText(String.valueOf(part.getPrice()));
        modifyPartMaxTxt.setText(String.valueOf(part.getMax()));
        modifyPartMinTxt.setText(String.valueOf(part.getMin()));

        if (part instanceof InHouse) {

            InHouse inHouse = (InHouse) part;
            modifyPartInHouseRBtn.setSelected(true);
            modifyMachCompanyId.setText("Machine ID");
            modifyMachCompanyTxt.setText(Integer.toString(inHouse.getMachineId()));

        } else if (part instanceof OutSourced){

            OutSourced outSourced = (OutSourced) part;
            modifyPartOutsourcedRBtn.setSelected(true);
            modifyMachCompanyId.setText("Company Name");
            modifyMachCompanyTxt.setText(outSourced.getCompanyName());
        }
    }

    /** Initialization method for Modify Part screen. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }
}
