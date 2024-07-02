package pt.ul.fc.di.css.thesisman.presentation.control;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;


public class LoginController {
	private double height;
	private double width;
	private Stage primaryStage;
	private String numeroAluno;

	@FXML
	private StackPane pane;

	@FXML
	private HBox topHbox;

	@FXML
	private Label title;

	@FXML
	private VBox mainVbox;

	@FXML
	private Label loginLabel;

	@FXML
	private TextField nAluno;
	
	@FXML
	private TextField password;

	@FXML
	private Button loginButton;

	public void setUp(Stage primaryStage) {
		this.primaryStage = primaryStage;

		height = Screen.getPrimary().getBounds().getHeight();
		width = Screen.getPrimary().getBounds().getWidth();

		setUpTopHbox();
		setUpLoginButton();
		setUpMainVbox();
		setUpNAluno();
		setUpPassword();
	}

	private void setUpTopHbox() {
		StackPane.setMargin(topHbox, new Insets(height * 0.06, 0, 0, width * 0.04));
		HBox.setMargin(title, new Insets(0, 0, 0, width * 0.04));
	}

	private void setUpLoginButton() {
		loginButton.setPrefSize(width / 20, height / 20);
	}

	private void setUpMainVbox() {
		mainVbox.setMaxHeight(height / 5); 
		mainVbox.setSpacing(height * 0.02);
	}

	private void setUpNAluno() {
		nAluno.setMaxWidth(width * 0.2);
	}
	
	private void setUpPassword() {
		password.setMaxWidth(width * 0.2);
	}

	@FXML
	public void loginHandler() throws Exception {
		if(login(nAluno.getText(), password.getText())){
//			FXMLLoader loader = new FXMLLoader(Main.class.getResource("/menu.fxml"));
//			StackPane root = loader.load();
//			MenuController controller = loader.<MenuController>getController();
//			controller.setUp(primaryStage);
//			primaryStage.getScene().setRoot(root);
		}
	}
	
	public boolean login(String username, String password) {
		
		
		try {
			URL url = new URL("http://localhost:8080/api/alunos/loginAluno");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json");
			con.setDoOutput(true);

			String requestBody = "{\"username\": \"" + username + "\"}";
			OutputStream outputStream = con.getOutputStream();
			outputStream.write(requestBody.getBytes("UTF-8"));
			outputStream.close();

			int responseCode = con.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				this.numeroAluno = username;
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Sucesso");
				alert.setHeaderText("Login efetuado com sucesso");
				alert.show();
				return true;
			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Erro");
				alert.setHeaderText("Utilizador nao existe");
				alert.show();
				return false;
			}
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erro");
			alert.setHeaderText("Erro ao estabelecer conexao com o servidor");
			alert.show();
			return false;
		}
	}
}