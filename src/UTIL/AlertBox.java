package UTIL;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.*;
import javafx.geometry.*;

/***
 * Class that holds customer alert pop up window with error message displayed.
 */
public class AlertBox {
   /***
    * Displays the alert pop up window with specific title and error message.
    * @param title Title of error window.
    * @param message Error message displayed.
    */
   public static void display(String title, String message){

      Stage stage = new Stage();
      stage.initModality(Modality.APPLICATION_MODAL);
      stage.setTitle(title);
      stage.setHeight(200);
      stage.setWidth(400);
      //Sets the window owner to the parent stage.
      stage.initOwner(Utility.getMainwindowstage());

      Label label = new Label();
      label.setText(message);
      label.isWrapText();
      label.setAlignment(Pos.CENTER);
      Button closeButton = new Button("Close this window");
      closeButton.setOnAction(event -> stage.close());

      VBox layout = new VBox(10);
      layout.getChildren().addAll(label,closeButton);
      layout.setAlignment(Pos.CENTER);

      Scene scene = new Scene(layout);
      stage.setScene(scene);
      stage.showAndWait();
   }
}