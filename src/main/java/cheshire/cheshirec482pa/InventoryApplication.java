// Curtis C
// ID: *********
// myemail@wgu.edu

package cheshire.cheshirec482pa;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;

/** Main class that starts the application.
 * <p><b>
 *     Future enhancement: This was a challenging project and I feel that for the most part, it is pretty good as it is. However, if I were to improve it in the future, I would add just a bit of extra functionality to the associated parts tables.
 * Some products require more than 1 of a part. Instead of adding multiple of the same part, add in a function that increments a count on how many of a particular part.
 * </b></p>
 */

public class InventoryApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(InventoryApplication.class.getResource("mainScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 500);
        stage.setTitle("Inventory");
        stage.setScene(scene);
        stage.show();
    }

    /** Main method that pre-populates part and product tables. */
    public static void main(String[] args) {

        Part engine = new InHouse(Inventory.getNewPartId(), "Engine", 5599.99, 5, 1, 10, 1001);
        Part frame = new InHouse(Inventory.getNewPartId(), "Frame", 1299.99, 7, 1, 10, 1002);
        Part transmission = new InHouse(Inventory.getNewPartId(), "Transmission", 3499.99, 3, 1, 10, 1003);
        Part exhaust = new OutSourced(Inventory.getNewPartId(), "Exhaust System", 1000.00, 3, 1, 10, "Auto Zone");
        Part windshield = new OutSourced(Inventory.getNewPartId(), "Windshield", 159.99, 2, 1, 10, "Pete's Parts");

        Inventory.addPart(engine);
        Inventory.addPart(frame);
        Inventory.addPart(transmission);
        Inventory.addPart(exhaust);
        Inventory.addPart(windshield);

        Product sedan = new Product(Inventory.getNewProductId(), "Sedan", 13500.00, 6, 1, 10);
        Product coupe = new Product(Inventory.getNewProductId(), "Coupe", 12750.00, 3, 1, 10);
        Product suv = new Product(Inventory.getNewProductId(), "SUV", 23500.00, 9, 1, 10);

        sedan.addAssociatedParts(engine);
        sedan.addAssociatedParts(transmission);

        coupe.addAssociatedParts(frame);
        coupe.addAssociatedParts(engine);

        suv.addAssociatedParts(windshield);
        suv.addAssociatedParts(exhaust);

        Inventory.addProduct(sedan);
        Inventory.addProduct(coupe);
        Inventory.addProduct(suv);

        launch();
    }
}
