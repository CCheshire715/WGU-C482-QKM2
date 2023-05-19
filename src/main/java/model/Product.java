// Curtis C
// ID: *********
// myemail@wgu.edu

package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** Class to create products. */
public class Product {

    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /** Product constructor. */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /** Product ID getter.
     @return id
     */
    public int getId() {
        return id;
    }

    /** Product ID setter.
     @param id Product ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /** Product name getter.
     @return name
     */
    public String getName() {
        return name;
    }

    /** Product name setter.
     @param name Product name
     */
    public void setName(String name) {
        this.name = name;
    }

    /** Product price getter.
     @return price
     */
    public double getPrice() {
        return price;
    }

    /** Product price setter.
     @param price Product price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /** Product inventory getter.
     @return stock
     */
    public int getStock() {
        return stock;
    }

    /** Product inventory setter.
     @param stock Product inventory amount
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /** Product minimum getter.
     @return min
     */
    public int getMin() {
        return min;
    }

    /** Product minimum setter.
     @param min Product minimum inventory amount
     */
    public void setMin(int min) {
        this.min = min;
    }

    /** Product maximum getter.
     @return max
     */
    public int getMax() {
        return max;
    }

    /** Product maximum setter.
     @param max Product maximum inventory amount
     */
    public void setMax(int max) {
        this.max = max;
    }

    /** Product add associated parts.
     @param part Part to add
     */
    public void addAssociatedParts(Part part) {

        associatedParts.add(part);
    }

    /** Delete associated parts.
     @param part Part selected to delete
     */
    public boolean deleteAssociatedPart(Part part) {

        return associatedParts.remove(part);
    }

    /** Associated parts list getter.
     @return associatedParts
     */
    public ObservableList<Part> getAllAssociatedParts() {

        return associatedParts;
    }
}
