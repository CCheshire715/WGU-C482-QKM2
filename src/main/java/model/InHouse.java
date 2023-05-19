// Curtis Cheshire
// ID: 010713063
// cchesh3@wgu.edu

package model;

/** Class to create In House parts. */
public class InHouse extends Part{

    private int machineId;

    /** InHouse constructor */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /** Machine ID getter.
     @return machineId
     */
    public int getMachineId() {
        return machineId;
    }
    /** Machine ID setter.
     @param machineId In house part machine ID
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}
