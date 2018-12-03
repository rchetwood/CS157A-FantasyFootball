package GUI;

import Models.Account;
import Models.Manager;
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
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.collections.*;
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
		
		
		
		//Get leagues
		ObservableList<Manager> managers = FXCollections.observableArrayList();
		managers.add(new Manager());
		managers.add(new Manager());
		managers.add(new Manager());
		managers.add(new Manager());
		managers.add(new Manager());
		managers.add(new Manager());
		
		
		Scene scene = new Scene(new Group());
		
		Label profileLabel = new Label("Profile");
		
		Button logoutButton = new Button("Logout");
		
		logoutButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				showLogin(stage);
			}
		});
		
		Label leagueLabel = new Label("Leagues");
		
		
		Region topRowCenterRegion = new Region();
		HBox.setHgrow(topRowCenterRegion, Priority.ALWAYS);
		
		final HBox topRow = new HBox(profileLabel, topRowCenterRegion, logoutButton);
		
		
		topRow.setSpacing(10);

		
		
		final VBox main = new VBox();
		
		ScrollPane leagues = new ScrollPane();
		
		HBox leaguesHbox = new HBox();
		
		leaguesHbox.setPrefHeight(250);
		leaguesHbox.setPadding(new Insets(10,10,10,10));
		leaguesHbox.setSpacing(30);
		
		
		for(Manager m : managers) {
			Label leagueTitle = new Label("Sample Title");
			VBox leagueTile = new VBox(leagueTitle);
			leagueTile.setAlignment(Pos.TOP_CENTER);
			leagueTile.setPadding(new Insets(10,10,10,10));
			leagueTile.setBorder(new Border(new BorderStroke(Color.GREY, BorderStrokeStyle.SOLID, new CornerRadii(5.0), BorderWidths.DEFAULT)));
			leaguesHbox.getChildren().add(leagueTile);
		}
		
		
		leagues.setContent(leaguesHbox);
		
		
		
		main.setSpacing(10);
		main.setPadding(new Insets(10,15,10,10));
		main.setAlignment(Pos.TOP_CENTER);
		main.prefWidthProperty().bind(stage.widthProperty());
		main.prefHeightProperty().bind(stage.heightProperty());
		main.getChildren().addAll(topRow, leagueLabel, leagues);
		
		((Group) scene.getRoot()).getChildren().addAll(main);
		stage.setWidth(600);
		stage.setHeight(400);
		stage.setTitle("Profile");
		stage.setScene(scene);
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
