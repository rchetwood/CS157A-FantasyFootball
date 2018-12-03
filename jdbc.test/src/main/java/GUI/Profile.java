package GUI;

import Models.Account;
import javafx.stage.*;
import javafx.scene.*;
import javafx.application.*;
import javafx.scene.input.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

@SuppressWarnings("restriction")
public class Profile extends Application{
	private Account account;
	
	public Profile(String test) {
		System.out.println(test);
	}
	
	public void start(Stage stage) {
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
}
