package pt.ul.fc.di.css.thesisman;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import pt.ul.fc.di.css.thesisman.presentation.control.EditorController;
import pt.ul.fc.di.css.thesisman.presentation.control.ListController;
import pt.ul.fc.di.css.thesisman.presentation.control.LoginController;
import pt.ul.fc.di.css.thesisman.presentation.control.MenuController;
import pt.ul.fc.di.css.thesisman.presentation.model.DataModel;

public class ThesisMan extends Application {

	@Override
	public void start(Stage primaryStage) {
		try {
	    	String prefix = "/pt/ul/fc/di/css/thesisman/presentation/view/";

			primaryStage.setTitle("ThesisMan");

			double height = Screen.getPrimary().getBounds().getHeight();
			double width = Screen.getPrimary().getBounds().getWidth();

			FXMLLoader loader = new FXMLLoader(getClass().getResource(prefix + "login.fxml"));
			StackPane root = loader.load();
			LoginController controller = loader.<LoginController>getController();
			Scene scene = new Scene(root, width * (2.0 / 3), height * (2.0 / 3));
			controller.setUp(primaryStage);

			primaryStage.setMinHeight(height * (7.0 / 12));
			primaryStage.setMinWidth(width * (7.0 / 12));
			primaryStage.setScene(scene);
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
    
    public static void main(String[] args) { launch(args); }
}
