
import UTIL.Utility;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 *
 * Main class runs the Inventory Management Program
 * @author James Mills
 * */
public class Main extends Application {

    /***
     * Sets up the stage to be ran within the application.
     * @param primarystage initial stage for the program.
     */
    @Override
    public void start(Stage primarystage) {

        Utility.getInstance();
        Utility.setMainwindowstage(primarystage);
        Utility.getMainwindowstage().setTitle(Utility.title);
        Utility.getMainwindowstage().setScene(Utility.getMain());
        Utility.getMainwindowstage().show();
    }
    /***
     * Primary method to be ran within application.
     * @param args Runs anything sent over from the command line.
     */
    //JavaDocs are located in .....JAVADOCS folder along with Project Zip file.
    public static void main(String[] args) {

       //Utility.makeFakeData(); //Loads fake simulated data into the Inventory List.
        launch(args);
    }
}