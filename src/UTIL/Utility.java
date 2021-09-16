package UTIL;

import INV.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

/***
 * Class that handles misc operations within the program.
 */
public class Utility{

    private static Utility instance =null;
    private static int masterpartindex = 0;
    private static int masterprodindex = 0;
    private static boolean searchindexflag;
    private static Stage mainwindowstage;
    /***
     * Holds the title for the application window.
     */
    public static String title = "The Last Inventory Control System Software You Will Ever Need.";
    private static Scene main;

    private Utility(){

        {
            try {
                Parent mainscreen = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/FXMLS/mainscreen.fxml")));
                main = new Scene(mainscreen);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /***
     * Creates the single object instance of the class or gets the instance for the user.
     */
    public static void getInstance()
    {
        if (instance == null)
            instance = new Utility();
    }

    /***
     * Gets the main screen scene object to load.
     * @return main screen scene object.
     */
    public static Scene getMain() { return main; }

    private static boolean searchFindsPart(Part part, String search){

        return (Integer.valueOf(part.getId()).toString().equals(search.toLowerCase())) ||
                (part.getName().toLowerCase().contains(search.toLowerCase()));
    }

    private static boolean searchFindsProd(Product product, String search){

        return (Integer.valueOf(product.getId()).toString().equals(search.toLowerCase())) ||
                (product.getName().toLowerCase().contains(search.toLowerCase()));
    }

    /***
     * Adds filter to a TableView with Parts objects.
     * @param search Search criteria.
     * @return Returns the filter to update the TableView.
     */
    public static Predicate<Part> createPartPredicate(String search){

        AtomicInteger ispartfound = new AtomicInteger();
        return part -> {
            if(!searchFindsPart(part,search))//Searches for the part.
                ispartfound.getAndIncrement();
            else{
                ispartfound.getAndIncrement();
                ispartfound.getAndIncrement();
            }
                    if (ispartfound.get() > Inventory.getAllParts().size())
                        searchindexflag = false;
            return searchFindsPart(part, search);
            };
    }

    /***
     * Adds filter to a TableView with Product objects.
     * @param search Search criteria.
     * @return Returns the filter to update the TableView.
     */
    public static Predicate<Product> createProdPredicate(String search){

        AtomicInteger isprodfound = new AtomicInteger();
        return product -> {
            if(!searchFindsProd(product,search)) //Searches for the product.
                isprodfound.getAndIncrement();
            else{
                isprodfound.getAndIncrement();
                isprodfound.getAndIncrement();
            }
            if (isprodfound.get() > Inventory.getAllProducts().size())
                searchindexflag = false;
            return searchFindsProd(product, search);
        };
    }

    /***
     * Adds simulated fake data to the Parts and Products listing of the Inventory.
     */
    public static void makeFakeData(){

        Part part1 = new InHouse(addMasterPartID(), "Ordinary Sword Hilt",25.86,
                5,0,99,12345);
        Part part2 = new InHouse(addMasterPartID(),"Decorative Pieces",15.22,10,
                0,99,13449);
        Part part3 =new Outsourced(addMasterPartID(),"Legendary Adamantite",99999.99,
                1,0,1,"FF Explorers Inc.");
        Part part4 = new InHouse(addMasterPartID(),"Leather",5.00,
                10,0,99,35679);
        Part part5 = new InHouse(addMasterPartID(), "Stones",0.50,15,
                5,999,23949);
        Part part6 = new InHouse(addMasterPartID(), "Coal",2.55,0,0,
                50,39586);
        Part part7 = new Outsourced(addMasterPartID(),"Diamond",1000.00,
                5,0,25,"Steve");
        Part part8 = new InHouse(addMasterPartID(), "Junk",0.01,1,0,
                99999,10001);
        Product prod1 = new Product(addMasterProdID(),"Excalibur",0.00,1,0,1 );
        Product prod2 = new Product(addMasterProdID(),"Diamond Sword", 157.50,
                2,1,99);
        Product prod3 = new Product(addMasterProdID(), "Pile of Junk",0.01,
                1,0,999);
        prod1.addAssociatedPart(part1);
        prod1.addAssociatedPart(part2);
        prod1.addAssociatedPart(part3);
        prod2.addAssociatedPart(part1);
        prod2.addAssociatedPart(part2);
        prod2.addAssociatedPart(part7);
        prod3.addAssociatedPart(part8);
        Inventory.addProduct(prod1);
        Inventory.addProduct(prod2);
        Inventory.addProduct(prod3);
        Inventory.addPart(part1);
        Inventory.addPart(part2);
        Inventory.addPart(part3);
        Inventory.addPart(part4);
        Inventory.addPart(part5);
        Inventory.addPart(part6);
        Inventory.addPart(part7);
        Inventory.addPart(part8);
    }

    /***
     * Changes the stage to the appropriate scene.
     * @param x decides which scene to laod.
     */
    public static void changeScene(int x){

        if(x==5){
            //Loads the Modify Product FXML file and sets the scene correctly.
            try {
                Parent modprodscreen = FXMLLoader.load(Objects.requireNonNull(Utility.class.getResource("/FXMLS/modifyproductscreen.fxml")));
                mainwindowstage.setScene(new Scene(modprodscreen));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        else if(x==2) {
            //Loads the Add Part FXML file and sets the scene correctly.
            try {
                Parent addpartscreen = FXMLLoader.load(Objects.requireNonNull(Utility.class.getResource("/FXMLS/addpartscreen.fxml")));
                mainwindowstage.setScene(new Scene(addpartscreen));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(x==3){
            //Loads the Modify Part FXML file and sets the scene correctly.
            try {
                Parent modpartscreen = FXMLLoader.load(Objects.requireNonNull(Utility.class.getResource("/FXMLS/modifypartscreen.fxml")));
                mainwindowstage.setScene(new Scene(modpartscreen));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        else if(x==4){
            //Loads the Add Product FXML file and sets the scene correctly.
            try {
               Parent addprodscreen = FXMLLoader.load(Objects.requireNonNull(Utility.class.getResource("/FXMLS/addproductscreen.fxml")));
               mainwindowstage.setScene(new Scene(addprodscreen));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            //Loads the Main Screen FXML file and sets the scene correctly.
            try {
               Parent mainscreen = FXMLLoader.load(Objects.requireNonNull(Utility.class.getResource("/FXMLS/mainscreen.fxml")));
               mainwindowstage.setScene(new Scene(mainscreen));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        mainwindowstage.setTitle(title);
        mainwindowstage.show();
    }

    /***
     * Keeps the partID information rolling correctly by adding +1 to each part when created.
     * @return PartID index returned.
     */
    public static int addMasterPartID(){ return ++masterpartindex; }

    /***
     * Keeps the productID information rolling correctly by adding +1 to each product when created.
     * @return ProductID index returned.
     */
    public static int addMasterProdID(){ return ++masterprodindex; }

    /***
     * Gives the flag back for a search related method function.
     * @return boolean flag value.
     */
    public static boolean isSearchindexflag() {
        return searchindexflag;
    }

    /***
     * Sets the flag for a search related method function.
     * @param set boolean flag value to set.
     */
    public static void setSearchindexflag(boolean set){
        searchindexflag=set;
    }

    /***
     * Keeps the Stage variable global throughout the program between scenes.
     * @return Main Stage variable.
     */
    public static Stage getMainwindowstage() {
        return mainwindowstage;
    }

    /***
     * Sets the Stage variable to the main window.
     * @param mainwindowstage Main Stage variable to set.
     */
    public static void setMainwindowstage(Stage mainwindowstage) { Utility.mainwindowstage = mainwindowstage; }
}