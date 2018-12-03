package GUI;

import Models.League;
import Models.Manager;
import Models.Player;
import javafx.application.*;
import javafx.scene.*;
import javafx.scene.text.*;
import javafx.scene.input.*;
import javafx.scene.control.*;
import javafx.scene.paint.*;
import javafx.stage.*;
import javafx.util.Callback;
import javafx.scene.layout.*;
import javafx.geometry.Insets;
import javafx.scene.control.cell.*;
import javafx.collections.*;
import javafx.event.EventHandler;
import javafx.beans.property.*;

public class Drafting extends Application {
	private League league;
	private TableView<Player> playerTable = new TableView<>();
	private TableView<Manager> managerTable = new TableView<>();
	private TableView<Player> rosterTable = new TableView<>();
	
	private final ObservableList<Manager> managers =
			FXCollections.observableArrayList();
	
	private final ObservableList<Player> players =
            FXCollections.observableArrayList();
	
	private final ObservableList<Player> roster =
            FXCollections.observableArrayList();
	
	public Drafting(League l) {
		this.league = l;
	}

	public void start(Stage stage) throws Exception {
			
			Scene scene = new Scene(new Group());
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			// Create Labels
			final Label playerLabel = new Label("Players");
			playerLabel.setFont(new Font("Arial", 20));
			
			final Label managerLabel = new Label("Managers");
			managerLabel.setFont(new Font("Arial", 20));
			
			final Label rosterLabel = new Label("Roster");
			rosterLabel.setFont(new Font("Arial", 20));
			
			final Label selectedPlayerLabel = new Label("");
			selectedPlayerLabel.setFont(new Font("Arial", 20));
			
			final Label selectedStatsLabel = new Label("");
			selectedStatsLabel.setFont(new Font("Arial", 14));

			// Name Columns
			TableColumn<Manager, String> managerFirstName = new TableColumn<>("First Name");
			TableColumn<Manager, String> managerLastName = new TableColumn<>("Last Name");
			
			TableColumn<Player, String> playerFirstName = new TableColumn<>("First Name");
			TableColumn<Player, String> playerLastName = new TableColumn<>("Last Name");
			TableColumn<Player, Integer> playerId = new TableColumn<>("ID");
			TableColumn<Player, String> playerPosition = new TableColumn<>("Position");
			
			TableColumn<Player, String> rosterFirstName = new TableColumn<>("First Name");
			TableColumn<Player, String> rosterLastName = new TableColumn<>("Last Name");
			TableColumn<Player, Integer> rosterPlayerId = new TableColumn<>("ID");
			TableColumn<Player, String> rosterPosition = new TableColumn<>("Position");
			
			// Connect Columns to model attributes
			managerFirstName.setCellValueFactory(new PropertyValueFactory<Manager, String>("firstName"));
			managerLastName.setCellValueFactory(new PropertyValueFactory<Manager, String>("lastName"));
			
			playerFirstName.setCellValueFactory(new PropertyValueFactory<Player, String>("firstName"));
			playerLastName.setCellValueFactory(new PropertyValueFactory<Player, String>("lastName"));
			playerId.setCellValueFactory(new PropertyValueFactory<Player, Integer>("id"));
			playerPosition.setCellValueFactory(new PropertyValueFactory<Player, String>("pos"));
			
			rosterFirstName.setCellValueFactory(new PropertyValueFactory<Player, String>("firstName"));
			rosterLastName.setCellValueFactory(new PropertyValueFactory<Player, String>("lastName"));
			rosterPlayerId.setCellValueFactory(new PropertyValueFactory<Player, Integer>("id"));
			rosterPosition.setCellValueFactory(new PropertyValueFactory<Player, String>("pos"));
			
			// Add data to columns
			playerTable.setEditable(false);
			playerTable.setItems(players);
			playerTable.getColumns().addAll(playerLastName, playerFirstName, playerId, playerPosition);
			
			managerTable.setEditable(false);
			managerTable.setItems(managers);
			managerTable.getColumns().addAll(managerFirstName, managerLastName);
			
			rosterTable.setEditable(false);
			rosterTable.setItems(roster);
			rosterTable.getColumns().addAll(rosterLastName, rosterFirstName, rosterPlayerId, rosterPosition);
			
			managerTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
			playerTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
			rosterTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
			
			// Add event handlers
			// Click on a manager to see their current roster
			managerTable.setRowFactory(new Callback<TableView<Manager>, TableRow<Manager>>() {
				@Override
				public TableRow<Manager> call(TableView<Manager> table) {
				    final TableRow<Manager> row = new TableRow<>();
				    row.setOnMouseClicked(new EventHandler<MouseEvent>() {
						@Override
						public void handle(MouseEvent event) {
						    if (!row.isEmpty() && event.getButton()==MouseButton.PRIMARY) {
						        Manager manager = row.getItem();
						        // SQL Statement here
						        // Get manager's roster
						        System.out.println(league + " " + manager);;
						    }
						}
					});
				    return row ;
				}
			});
			
			// Click on player to see player statistics
			playerTable.setRowFactory(new Callback<TableView<Player>, TableRow<Player>>() {
				@Override
				public TableRow<Player> call(TableView<Player> table) {
				    final TableRow<Player> row = new TableRow<>();
				    row.setOnMouseClicked(new EventHandler<MouseEvent>() {
						@Override
						public void handle(MouseEvent event) {
						    if (!row.isEmpty() && event.getButton()==MouseButton.PRIMARY) {
						        Player player = row.getItem();
						        
						        if (event.getClickCount() == 2) {
						        	roster.add(player);
						        	players.remove(player);
						        }
						        // SQL Statement here
						        selectedPlayerLabel.setText(player.getFirstName() 
						        	+ " " + player.getLastName()
						        	+ " [" + player.getPosition() + "]\n");
						        // Player stats here
						        selectedStatsLabel.setText("Stats here");
						        System.out.println(player);;
						    }
						}
					});
				    return row ;
				}
			});
			
			rosterTable.setRowFactory(new Callback<TableView<Player>, TableRow<Player>>() {
				@Override
				public TableRow<Player> call(TableView<Player> table) {
					final TableRow<Player> row = new TableRow<>();
					row.setOnMouseClicked(new EventHandler<MouseEvent>() {
						@Override
						public void handle(MouseEvent event) {
						    if (!row.isEmpty() && event.getButton()==MouseButton.PRIMARY) {
						        Player player = row.getItem();
						        
						        if (event.getClickCount() == 2) {
						        	players.add(player);
						        	roster.remove(player);
						        }
						        // SQL Statement here
						        selectedPlayerLabel.setText(player.getFirstName() 
						        	+ " " + player.getLastName()
						        	+ " [" + player.getPosition() + "]\n");
						        // Player stats here
						        selectedStatsLabel.setText("Stats here");
						        System.out.println(player);;
						    }
						}
					});
			    return row;
				}
			});
			
			// Group each table with its own label
			final VBox managerBox = new VBox();
	        managerBox.setSpacing(5);
	        managerBox.setPadding(new Insets(10, 0, 0, 10));
	        managerBox.getChildren().addAll(managerLabel, managerTable);
			
			final VBox playerBox = new VBox();
	        playerBox.setSpacing(5);
	        playerBox.setPadding(new Insets(10, 0, 0, 10));
	        playerBox.getChildren().addAll(playerLabel, playerTable);
	        
	        final VBox rosterBox = new VBox();
	        rosterBox.setSpacing(5);
	        rosterBox.setPadding(new Insets(10, 0, 0, 10));
	        rosterBox.getChildren().addAll(rosterLabel, rosterTable);
	        
	        // Groups tables
	        final HBox tableBox = new HBox();
	        tableBox.setSpacing(1);
	        tableBox.getChildren().addAll(managerBox, playerBox, rosterBox);
	        
	        final VBox statsBox = new VBox();
	        statsBox.setSpacing(5);
	        statsBox.setMaxWidth(400);
	        statsBox.setMinHeight(70);
	        statsBox.setPadding(new Insets(10, 0, 0, 10));
	        statsBox.setBorder(new Border(new BorderStroke(Color.BLACK, 
	        		BorderStrokeStyle.SOLID, new CornerRadii(10), new BorderWidths(1))));
	        statsBox.getChildren().addAll(selectedPlayerLabel, selectedStatsLabel);
	        
	        final VBox draftBox = new VBox();
	        draftBox.setSpacing(1);
	        draftBox.setPadding(new Insets(10, 0, 0, 10));
	        draftBox.getChildren().addAll(statsBox, tableBox);
			
	        ((Group) scene.getRoot()).getChildren().addAll(draftBox);
	        
			stage.setWidth(1000);
			stage.setHeight(600);
			stage.setTitle("Drafting");
			stage.setScene(scene);
			stage.show();
	}
}