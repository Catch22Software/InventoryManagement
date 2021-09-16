package CONTROLLER;

import INV.InHouse;
import INV.Inventory;
import INV.Outsourced;
import UTIL.AlertBox;
import UTIL.InventoryHolder;
import UTIL.Utility;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ResourceBundle;

/***
 * Loads the Modify Part Screen.
 */
public class Modifypartscreencontroller implements Initializable {


    @FXML
    private TextField modpartID;
    @FXML
    private TextField modpartname;
    @FXML
    private TextField modpartinv;
    @FXML
    private TextField modpartcost;
    @FXML
    private TextField modpartmin;
    @FXML
    private TextField modpartmax;
    @FXML
    private TextField modmachineid;
    @FXML
    private Label compnamelabel;
    @FXML
    private Label machineidlabel;
    @FXML
    private TextField modcompname;
    @FXML
    private RadioButton inhousebutton;
    @FXML
    private ToggleGroup partgroup;
    @FXML
    private RadioButton outsourcebutton;

    /***
     * Gets Textfield info from Modify Part ID field.
     * @param event Event that calls the method.
     * @return Textfield info.
     */
    public String getPartIdAction(ActionEvent event) {

       return modpartID.getText();
    }

    /***
     * Gets Textfield info from Modify Part Name field.
     * @param event Event that calls the method.
     * @return Textfield info.
     */
    public String getPartNameAction(ActionEvent event) {

        return modpartname.getText();
    }

    /***
     * Gets Textfield info from Modify Part Inventory field.
     * @param event Event that calls the method.
     * @return Textfield info formatted into an Integer.
     */
    public int getPartInvAction(ActionEvent event) {

        return Integer.parseInt(modpartinv.getText());
    }

    /***
     * Gets Textfield info from Modify Part Price field.
     * @param event Event that calls the method.
     * @return Textfield info formatted into a Double.
     */
    public double getPartPriceAction(ActionEvent event) {

        return Double.parseDouble(modpartcost.getText());
    }

    /***
     * Gets Textfield info from Modify Part Min field.
     * @param event Event that calls the method.
     * @return Textfield info formatted into an Integer.
     */
    public int getPartMinAction(ActionEvent event) {

        return Integer.parseInt(modpartmin.getText());
    }

    /***
     * Gets Textfield info from Modify Part Max field.
     * @param event Event that calls the method.
     * @return Textfield info formatted into an Integer.
     */
    public int getPartMaxAction(ActionEvent event) {

        return Integer.parseInt(modpartmax.getText());
    }

    /***
     * Gets Textfield info from Modify Part Machine ID field.
     * @param event Event that calls the method.
     * @return Textfield info formatted into an Integer.
     */
    public int getMachineIdAction(ActionEvent event) {

        return Integer.parseInt(modmachineid.getText());
    }

    /***
     * Gets Textfield info from Modify Part Company Name field.
     * @param event Event that calls the method.
     * @return Textfield info.
     */
    public String getCompNameAction(ActionEvent event) {

        return modcompname.getText();
    }

    /***
     * Saves modified part over original Master Inventory part list. Input Validation completed.
     * @param event Event that calls the method.
     */
    public void modPartAction(ActionEvent event) {


        int
                stock = 0
                ,min = 0
                ,max = 0
                ,machineid= 0;
        double cost = 0.0;
        String name = getPartNameAction(event);
        String compname = "stuff";
        boolean flag = false;



        //Checks to see which RadioButton is selected.
        if(inhousebutton.isSelected()){
            //Checks for anthing but numbers in the machine id field.
            {try{
                machineid = getMachineIdAction(event);
            }
            catch (NumberFormatException e){

                String title = "You can't do this";
                String message = "You can't use letters, symbols or spaces for 'machine id' . \n You can" +
                        " only use numbers";
                flag = true;
                AlertBox.display(title,message);
            }
            }
        }
        else
            compname = getCompNameAction(event);
        //Checks for incorrect input in the Numerical values.
        {try{
            stock = getPartInvAction(event);
            min = getPartMinAction(event);
            max = getPartMaxAction(event);
            cost = getPartPriceAction(event);
        }
        catch (NumberFormatException e){
            String title = "You can't do this";
            String message = "You can't use letters, symbols spaces for 'Inv, Min, Max and Cost' . \n You can" +
                    " only use numbers";
            flag = true;
            AlertBox.display(title,message);
        }}
        //Check if part name and / or company name is blank.
        if(name.equals("") || compname.equals("")){ ;
            String title = "You can't do this";
            String message = "You can't name a part or company nothing. ";
            flag = true;
            AlertBox.display(title,message);
        }
        //Check if stock is between min and max.
        if(min > stock || max < stock){
            String title = "You can't do this";
            String message = "The stock level must be between the minimum and maximum values. ";
            AlertBox.display(title,message);
            flag = true;
        }
        //Check if negative numbers used.
        if(min <0 || max < 0|| stock < 0 || cost < 0.00 || machineid < 0){
            String title = "You can't do this";
            String message = "You can't use negative numbers for any numerical values";
            AlertBox.display(title,message);
            flag = true;
        }
        //Check if both machineID and company name contain values.
        if(!(modcompname.getText().isEmpty()) && !(modmachineid.getText().isEmpty())){
            String title = "You can't do this";
            String message = "You can't have values in both machineid and company name";
            AlertBox.display(title,message);
            flag = true;
        }
        //If any of the above error messages are executed, method stops.
        if(flag)
            return;
        //Saves modified part under appropriate InHouse or OutSourced based on selection.
        if(inhousebutton.isSelected()){
            InventoryHolder inventoryHolder = InventoryHolder.getInstance();
            { try {
                Inventory.updatePart((Inventory.getAllParts().indexOf(inventoryHolder.getInHouse())), new InHouse((
                        Integer.parseInt(
                                modpartID.getText())
                ), name, cost, stock, min, max, machineid));
            }
            catch (IndexOutOfBoundsException e){
                Inventory.updatePart((Inventory.getAllParts().indexOf(inventoryHolder.getOutsourced())), new InHouse((
                        Integer.parseInt(
                                modpartID.getText())
                ), name, cost, stock, min, max, machineid));
            }
            }
        }
        else{
            InventoryHolder inventoryHolder = InventoryHolder.getInstance();
            { try {
                Inventory.updatePart((Inventory.getAllParts().indexOf(inventoryHolder.getOutsourced())), new Outsourced((
                        Integer.parseInt(
                                modpartID.getText())
                ), name, cost, stock, min, max, compname));
            }
            catch (IndexOutOfBoundsException e){
                Inventory.updatePart((Inventory.getAllParts().indexOf(inventoryHolder.getInHouse())), new Outsourced((
                        Integer.parseInt(
                                modpartID.getText())
                ), name, cost, stock, min, max, compname));
            }
            }
        }
        InventoryHolder holder = InventoryHolder.getInstance();
        holder.nullifyObjects();
        Utility.changeScene(1);
    }

    /***
     * Goes back to Main Screen.
     * @param event Event that calls the method.
     */
    public void cancelAction(ActionEvent event) {

        InventoryHolder holder = InventoryHolder.getInstance();
        holder.nullifyObjects();
        Utility.changeScene(1);
    }

    /***
     * Overloaded initialize method
     * @param url Overloaded parameter.
     * @param resourceBundle Overloaded parameter.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Adds listener to check which RadioButton is selected and disables appropriate textfields.
        partgroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle toggle, Toggle t1) {
                if(partgroup.getSelectedToggle().equals(inhousebutton)){
                     machineidlabel.setDisable(false);
                    modmachineid.setDisable(false);
                    modcompname.setDisable(true);
                    compnamelabel.setDisable(true);
                }
                else{
                    compnamelabel.setDisable(false);
                    modcompname.setDisable(false);
                    machineidlabel.setDisable(true);
                    modmachineid.setDisable(true);
                }
            }
        });

        //Loads the selected part from main screen in the fields when scene if first displayed.
        InventoryHolder inv = InventoryHolder.getInstance();
        try {
            Class<?> clazz = Class.forName("INV.InHouse");
            Field field = clazz.getDeclaredField("machineId");
            InHouse part = inv.getInHouse();
            modpartID.setText(Integer.toString(part.getId()));
            modpartname.setText(part.getName());
            modpartcost.setText(Double.toString(part.getPrice()));
            modpartinv.setText(Integer.toString(part.getStock()));
            modpartmin.setText(Integer.toString(part.getMin()));
            modpartmax.setText(Integer.toString(part.getMax()));
            modmachineid.setText(Integer.toString(part.getMachineId()));
        } catch (ClassNotFoundException | NoSuchFieldException | ClassCastException  |
        NullPointerException e) {
            outsourcebutton.fire();
            Outsourced part = inv.getOutsourced();
            modpartID.setText(Integer.toString(part.getId()));
            modpartname.setText(part.getName());
            modpartcost.setText(Double.toString(part.getPrice()));
            modpartinv.setText(Integer.toString(part.getStock()));
            modpartmin.setText(Integer.toString(part.getMin()));
            modpartmax.setText(Integer.toString(part.getMax()));
            modcompname.setText(part.getCompanyName());
        }
    }
}
