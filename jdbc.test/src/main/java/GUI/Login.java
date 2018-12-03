package GUI;

import javafx.application.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.event.*;

@SuppressWarnings("restriction")
public class Login extends Application {
	
	public void start(final Stage stage) throws Exception {
		Scene scene = new Scene(new Group());
		
		final Label title = new Label("Welcome to Fantasy Football!!");
		
		final Label usernameLabel = new Label("Username:  ");
		final TextField username = new TextField("");
		
		final Label passwordLabel = new Label("Password: ");
		final TextField password = new TextField("");
		
		Button loginButton = new Button("Login");
		
		loginButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				//Check credentials
				boolean login = true;
				if(login){
					
					stage.close();
				} else {
					System.out.println("login failed");
				}
			}
		});
		
		VBox vbox = new VBox();
		vbox.setSpacing(10);
		vbox.setPadding(new Insets(10, 0, 0, 65));
		vbox.setAlignment(Pos.CENTER);
		vbox.getChildren().addAll(title, usernameLabel, username, passwordLabel, password, loginButton);
		
		((Group) scene.getRoot()).getChildren().addAll(vbox);
		
		stage.setWidth(300);
		stage.setHeight(235);
		stage.setTitle("Login");
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	
}

