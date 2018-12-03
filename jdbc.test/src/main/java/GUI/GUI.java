package GUI;

import Models.Account;
import javafx.application.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.*;

public class GUI extends Application{
	
	public void start(final Stage stage) {
		showLogin(stage);
	}
	
	private void showLogin(final Stage stage) {
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
				Account account = new Account();
				boolean login = true;
				if(login){
					showProfile(stage, account);
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
	
	public void showProfile(final Stage stage, Account account) {
		Scene scene = new Scene(new Group());
		
		Label profileLabel = new Label("Profile");
		
		
		final VBox main = new VBox();
		main.setSpacing(10);
		main.setPadding(new Insets(10,0,0,65));
		main.setAlignment(Pos.CENTER);
		main.getChildren().addAll(profileLabel);
		
		((Group) scene.getRoot()).getChildren().addAll(main);
		stage.setWidth(800);
		stage.setHeight(500);
		stage.setTitle("Profile");
		stage.setScene(scene);
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
