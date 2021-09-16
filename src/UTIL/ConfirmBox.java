package UTIL;

import INV.Inventory;
import INV.Part;
import INV.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.*;
import javafx.geometry.*;

/***
 * Class that creates confirmation boxes upon deletion of an Inventory part or product.
 */
public class ConfirmBox {
    /***
     * Confirmation pop up box for deleting a selected part.
     * @param table parts table the part is selected from.
     */
    public static void displaypart(TableView table){

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("ARE YOU SURE?");
        stage.setHeight(200);
        stage.setWidth(400);
        //sets stage owner to be parent window.
        stage.initOwner(Utility.getMainwindowstage());

        Button yes = new Button("CONFIRM DELETE");
        Button no = new Button("GO BACK");
        //if yes is pressed, item is deleted and user sent back to parent screen. Otherwise if no is selected, user sent
        //to parent screen.
        yes.setOnAction(event ->
        {
            Inventory.deletePart(
                (Part) table.getSelectionModel().getSelectedItem());
        stage.close();});
        no.setOnAction(event -> stage.close());

        HBox layout = new HBox(10);
        layout.getChildren().addAll(yes, no);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        stage.setScene(scene);
        stage.showAndWait();
    }

    /***
     * Confirmation pop up box for deleting a selected product.
     * @param table product table the product is selected from.
     */
    public static void displayprod(TableView table){

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("ARE YOU SURE?");
        stage.setHeight(400);
        stage.setWidth(400);
        //sets stage owner to be parent window.
        stage.initOwner(Utility.getMainwindowstage());

        Label label = new Label("WARNING, THIS WILL ALSO REMOVE THE \n FOLLOWING ASSOCIATED" +
                " PARTS THAT ARE \n ATTACHED TO THE SELECTED PRODUCT!!!");
        label.isWrapText();
        label.setAlignment(Pos.CENTER);
        Product p1 = ((Product) table.getSelectionModel().getSelectedItem());
        TableView<Part> assocparts = new TableView<>();
        ObservableList<Part> list = FXCollections.observableArrayList();
        for(int i = 0; i <p1.getAllAssociatedParts().size() ;i++) {

            //Checks to see if any of the saved associated parts have been deleted when the product is modifed.
            if ((Inventory.lookupPart(p1.getAllAssociatedParts().get(i).getId())) != null)
                list.add(p1.getAllAssociatedParts().get(i));
        }
        assocparts.setItems(list);
        TableColumn<Part,?> partnamecol = new TableColumn<>("Name");
        TableColumn<Part,?> partidcol = new TableColumn<>("ID");
        TableColumn<Part, ?> partinvcol = new TableColumn<>("INV");
        partnamecol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partidcol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partinvcol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        assocparts.getColumns().addAll(partidcol,partnamecol,partinvcol);

        //displays associated parts for the prod if applicable. If yes is pressed, parts are removed and product
        // is deleted. If no is pressed, user is taken back to parent screen.
        Button yes = new Button("CONFIRM DELETE");
        Button no = new Button("GO BACK");
        yes.setOnAction(event -> {Inventory.deleteProduct(
                (Product) table.getSelectionModel().getSelectedItem());
            stage.close();});
        no.setOnAction(event -> stage.close());

        HBox layout = new HBox(10);
        HBox labelholder = new HBox(10);
        labelholder.getChildren().add(label);
        labelholder.setAlignment(Pos.CENTER);

        BorderPane layout3 = new BorderPane();
        layout.getChildren().addAll(yes, no);
        layout.setAlignment(Pos.CENTER);
        label.setAlignment(Pos.CENTER);
        layout3.setTop(labelholder);
        layout3.setCenter(assocparts);
        layout3.setBottom(layout);
        layout3.setPadding(new Insets(5.0));

        Scene scene = new Scene(layout3);
        stage.setScene(scene);
        stage.showAndWait();
    }
}