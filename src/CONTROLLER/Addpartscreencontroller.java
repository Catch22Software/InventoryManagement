package CONTROLLER;

import INV.InHouse;
import INV.Inventory;
import INV.Outsourced;
import UTIL.AlertBox;
import UTIL.Utility;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

/***
 * Controller that runs the Add Part Screen.
 */
public class Addpartscreencontroller implements Initializable {


    @FXML
    private ToggleGroup partgroup;

    @FXML
    private Label addmachineidlabel;

    @FXML
    private Label addpartcompnamelabel;

    @FXML
    private RadioButton inhousebutton;

    @FXML
    private TextField addpartname;

    @FXML
    private TextField addpartinv;

    @FXML
    private TextField addpartcost;

    @FXML
    private TextField addpartmin;

    @FXML
    private TextField addpartmax;

    @FXML
    private TextField addmachineid;

    @FXML
    private TextField addcompname;

    /***
     * Gets the textfield info from the Part Name field.
     * @param actionEvent Event that caused the method to be called.
     * @return text field information.
     */
    public String addPartNameAction(ActionEvent actionEvent) {

        return addpartname.getText();
    }

    /***
     * Gets the textfield info from the Part Inventory field.
     * @param actionEvent Event that caused the method to be called.
     * @return text field information formatted to an Integer.
     */
    public int addPartInvAction(ActionEvent actionEvent) {

        return Integer.parseInt(addpartinv.getText());
    }

    /***
     * Gets the textfield info from the Part Price field.
     * @param actionEvent Event that caused the method to be called.
     * @return text field information formatted to a Double.
     */
    public double addPartCostAction(ActionEvent actionEvent) {

        return Double.parseDouble(addpartcost.getText());
    }

    /***
     * Gets the textfield info from the Part Min field.
     * @param actionEvent Event that caused the method to be called.
     * @return text field information formatted to an Integer.
     */
    public int addPartMinAction(ActionEvent actionEvent) {

        return Integer.parseInt(addpartmin.getText());
    }

    /***
     * Gets the textfield info from the Part Max field.
     * @param actionEvent Event that caused the method to be called.
     * @return text field information formatted to an Integer.
     */
    public int addPartMaxAction(ActionEvent actionEvent) {

        return Integer.parseInt(addpartmax.getText());
    }

    /***
     * Gets the textfield info from the Part Machine ID field.
     * @param actionEvent Event that caused the method to be called.
     * @return text field information formatted to an Integer.
     */
    public int addMachineIDAction(ActionEvent actionEvent) {

        return Integer.parseInt(addmachineid.getText());
    }

    /***
     * Gets the textfield info from the Part Company Name field.
     * @param actionEvent Event that caused the method to be called.
     * @return text field information.
     */
    public String addCompanyNameAction(ActionEvent actionEvent) {

        return addcompname.getText();
    }

    /***
     * Saves information from textfields as new part if information successfully validates.
     * @param actionEvent Event that caused the method to be called.
     */
    public void saveNewPartAction(ActionEvent actionEvent) {


        int
                stock = 0
                ,min = 0
                ,max = 0
                ,machineid= 0;
        double cost = 0.0;
        String name = addPartNameAction(actionEvent);
        String compname = "stuff";
        boolean flag = false;

        //Checks to see if part will be inHouse or Outsourced.
        if(inhousebutton.isSelected()){
            {try{
                machineid = addMachineIDAction(actionEvent);
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
            compname = addCompanyNameAction(actionEvent);
        //Checks to see if fields contain anything but numbers in them.
        {try{
            stock = addPartInvAction(actionEvent);
            min = addPartMinAction(actionEvent);
            max = addPartMaxAction(actionEvent);
            cost = addPartCostAction(actionEvent);
        }
        catch (NumberFormatException e){
            String title = "You can't do this";
            String message = "You can't use letters, symbols spaces for 'Inv, Min, Max and Cost' . \n You can" +
                    " only use numbers";
            flag = true;
            AlertBox.display(title,message);
        }}
        name = addPartNameAction(actionEvent);
        //Checks to see if part name and / or company name is blank.
        if(name.equals("") || compname.equals("")){ ;
            String title = "You can't do this";
            String message = "You can't name a part or company nothing. ";
            flag = true;
            AlertBox.display(title,message);
        }
        //Checks that the inventory is between min and max.
        if(min > stock || max < stock){
            String title = "You can't do this";
            String message = "The stock level must be between the minimum and maximum values. ";
            AlertBox.display(title,message);
            flag = true;
        }
        //Checks to see if any numbers entered are negative.
        if(min <0 || max < 0|| stock < 0 || cost < 0.00 || machineid < 0){
            String title = "You can't do this";
            String message = "You can't use negative numbers for any numerical values";
            AlertBox.display(title,message);
            flag = true;
        }
        if(flag)
            return;
        //Adds the part to the Inventory list as an Inhouse or Outsourced based on info entered.
            if(compname.equalsIgnoreCase("stuff"))
                Inventory.addPart(new InHouse(Utility.addMasterPartID(), name, cost, stock, min, max, machineid ));
            else
                Inventory.addPart(new Outsourced(Utility.addMasterPartID(), name, cost, stock, min, max, compname));
            Utility.changeScene(1);
    }

    /***
     * Goes back to Main Screen.
     * @param actionEvent Event that caused the method to be callled.
     */
    public void cancelButtonAction(ActionEvent actionEvent) {

        Utility.changeScene(1);

    }

    /***
     * Overloaded initialze method called each time the scene is loaded.
     * @param url Overloaded method parameter.
     * @param resourceBundle Overloaded method parameter.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //adds listener to see which of the two radiobuttons is selected and will then clear and disable the
        // appropriate one.
        partgroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            /***
             * Overloaded method called to set up the listener
             * @param observableValue Overloaded method parameter.
             * @param toggle Overloaded method parameter.
             * @param t1 Overloaded method parameter.
             */
            @Override
            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle toggle, Toggle t1) {
                if(partgroup.getSelectedToggle().equals(inhousebutton)){
                    addmachineidlabel.setDisable(false);
                    addmachineid.setDisable(false);
                    addpartcompnamelabel.setDisable(true);
                    addcompname.clear();
                    addcompname.setDisable(true);
                }
                else{
                    addpartcompnamelabel.setDisable(false);
                    addcompname.setDisable(false);
                    addmachineidlabel.setDisable(true);
                    addmachineid.clear();
                    addmachineid.setDisable(true);
                }
            }
        });
    }
}
