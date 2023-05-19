// Curtis Cheshire
// ID: 010713063
// cchesh3@wgu.edu

package model;

/** Class to create Outsourced parts. */
public class OutSourced extends Part{

    private String companyName;

    /** Outsourced part constructor. */
    public OutSourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /** Company name getter.
     @return companyName
     */
    public String getCompanyName() {
        return companyName;
    }

    /** Company name setter.
     @param companyName Company name of outsourced part
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
