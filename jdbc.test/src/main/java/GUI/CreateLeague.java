package GUI;

import javafx.application.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.event.*;

public class CreateLeague extends Application {
	
	public void start(final Stage stage) throws Exception {
		Scene scene = new Scene(new Group());
		
		final Label numTeamsLabel = new Label("Number of Teams: ");
		final TextField numTeams = new TextField("");
		
		Button createBtn = new Button("Create League");
		
		createBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				String num = numTeams.getText();
				if (!num.isEmpty()) {
					// SQL statement here
					// Add League to DB
					System.out.println(numTeams.getText());
					stage.close();
				} else {
					System.out.println("Empty");
				}
			}
		});
		
		VBox createBox = new VBox();
		createBox.setSpacing(10);
		createBox.setPadding(new Insets(10, 0, 0, 65));
		createBox.setAlignment(Pos.CENTER);
		createBox.getChildren().addAll(numTeamsLabel, numTeams, createBtn);
		
		((Group) scene.getRoot()).getChildren().addAll(createBox);
		
		stage.setWidth(300);
		stage.setHeight(200);
		stage.setTitle("Create League");
		stage.setScene(scene);
		stage.show();
	}
}
