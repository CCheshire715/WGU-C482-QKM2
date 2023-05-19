// Curtis C
// ID: *********
// myemail@wgu.edu

package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** Class that holds saved parts and products. */
public class Inventory {

    private static int newPartId = 1;

    private static int newProductId = 1;

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();

    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /** Part ID getter.
     @return newPartId++
     */
    public static int getNewPartId() {

        return newPartId++;
    }

    /** Product ID getter.
     @return newProductId++
     */
    public static int getNewProductId() {

        return newProductId++;
    }

    /** Adds part to allParts list.
     @param newPart Part to be added
     */
    public static void addPart(Part newPart) {

        allParts.add(newPart);
    }

    /** Adds product to allProducts list.
     @param newProduct Product to be added
     */
    public static void addProduct(Product newProduct) {

        allProducts.add(newProduct);
    }

    /** Gets part from allParts list.
     @param partId Part ID searched
     @return part
     */
    public static Part lookupPart(int partId) {

        for (Part part : allParts) {
            if (part.getId() == partId) {
                return part;
            }
        }
        return null;
    }

    /** Gets product from allProducts list, ID.
     @param productId Product ID searched
     @return product
     */
    public static Product lookupProduct(int productId) {

        for (Product product : allProducts) {
            if (product.getId() == productId) {
                return product;
            }
        }
        return null;
    }

    /** Gets parts from allParts List, name.
     @param partName Part name searched
     @return partMatches
     */
    public static ObservableList<Part> lookupPart(String partName) {

        ObservableList<Part> partMatches = FXCollections.observableArrayList();
        for (Part part : allParts) {
            if (part.getName().toLowerCase().contains(partName.toLowerCase())) {
                partMatches.add(part);
            }
        }
        return partMatches;
    }

    /** Gets products from allProducts List, name.
     @param productName Product name searched
     @return productMatches
     */
    public static ObservableList<Product> lookupProduct(String productName) {

        ObservableList<Product> productMatches = FXCollections.observableArrayList();
        for (Product product : allProducts) {
            if (product.getName().toLowerCase().contains(productName.toLowerCase())) {
                productMatches.add(product);
            }
        }
        return productMatches;
    }

    /** Updates part from allParts list.
     @param index Part index
     @param selectedPart Part to be updated
     */
    public static void updatePart(int index, Part selectedPart) {

        allParts.set(index, selectedPart);
    }

    /** Updates product from allProducts list.
     @param index Product index
     @param newProduct Product to be updated
     */
    public static void updateProduct(int index, Product newProduct) {

        allProducts.set(index, newProduct);
    }

    /** Deletes part from allParts list.
     @param selectedPart Selected part to delete
     */
    public static boolean deletePart(Part selectedPart) {

        return allParts.remove(selectedPart);
    }

    /** Deletes product from allProducts list.
     @param selectedProduct Selected product to delete
     */
    public static boolean deleteProduct(Product selectedProduct) {

        return allProducts.remove(selectedProduct);
    }

    /** allParts getter.
     @return allParts
     */
    public static ObservableList<Part> getAllParts() {

        return allParts;
    }

    /** allProducts getter.
     @return allProducts
     */
    public static ObservableList<Product> getAllProducts() {

        return allProducts;
    }
}
