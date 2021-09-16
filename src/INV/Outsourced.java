package INV;

/***
 * Outsourced Class holds Part objects that contain Company Name.
 */
public class Outsourced extends Part{


    private String companyName;

    /***
     * Constructor to create an Outsourced object.
     * @param id Part ID.
     * @param name Part name.
     * @param price Part cost.
     * @param stock Part inventory level.
     * @param min Part min inventory level.
     * @param max Part max inventory level.
     * @param companyName Part company name.
     */
    public Outsourced(int id, String name, double price,
                      int stock, int min, int max,
                      String companyName){
        super(id,name,price,stock,min,max);
        setCompanyName(companyName);
    }

    /***
     * Gets company name from Outsourced object.
     * @return Company name.
     */
    public String getCompanyName() {
        return companyName;
    }

    /***
     * Sets company name for Outsourced object.
     * @param companyName Company name to set.
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}