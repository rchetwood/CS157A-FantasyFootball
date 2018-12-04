package GUI;

import java.time.LocalDate;


import Models.Account;
import Models.League;
import Models.Manager;
import Models.Player;
import DAOs.*;
import Exceptions.*;
import javafx.application.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.collections.*;
import javafx.stage.*;
import javafx.util.Callback;

import java.util.List;
import java.util.Random;
import javafx.util.converter.NumberStringConverter;

@SuppressWarnings("restriction")
public class GUI extends Application{
	
	public void start(final Stage stage) {
		showLogin(stage);
	}
	
	private void showLogin(final Stage stage) {
		Scene scene = new Scene(new Group());
		
		final Label title = new Label("Welcome to Fantasy Football!!");
		
		final Label emailLabel = new Label("Email:  ");
		final TextField email = new TextField("");
		
		final Label passwordLabel = new Label("Password: ");
		final PasswordField password = new PasswordField();
		
		Button loginButton = new Button("Login");
		
		loginButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				//Check credentials
				try {
					Account account = AccountDAO.retrieve(email.getText());
					showProfile(stage, account);
				} catch (AccountDAOException adaoe) {
					System.out.println(adaoe.getMessage());
					email.setText("");
					password.setText("");
				}
			}
		});
		
		Button signUpButton = new Button("Sign Up");

		signUpButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				showSignUp(stage);
			}
		});
		
		HBox bottomRow = new HBox(loginButton, signUpButton);
		bottomRow.setSpacing(10);
		bottomRow.setAlignment(Pos.CENTER);
		
		
		VBox vbox = new VBox();
		vbox.setSpacing(10);
		vbox.setPadding(new Insets(10,50,10,50));
		vbox.setAlignment(Pos.TOP_CENTER);
		vbox.prefWidthProperty().bind(scene.widthProperty());
		vbox.getChildren().addAll(title, emailLabel, email, passwordLabel, password, bottomRow);
		
		((Group) scene.getRoot()).getChildren().addAll(vbox);
		
		stage.setWidth(300);
		stage.setHeight(235);
		stage.setTitle("Login");
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
	}
	
	private void showSignUp(final Stage stage) {
		Scene scene = new Scene(new Group());
		
		Label firstNameLabel = new Label("First Name: ");
		
		final TextField firstName = new TextField("");
		
		Label lastNameLabel = new Label("Last Name: ");
		
		final TextField lastName = new TextField("");
		
		Label emailLabel = new Label("Email: ");
		
		final TextField email = new TextField("");
		
		Label passwordLabel = new Label("Password: ");
		
		final PasswordField password = new PasswordField();
		
		Label repeatPasswordLabel = new Label("Repeat Password: ");
		
		final PasswordField repeatPassword = new PasswordField();
		
		Button createAccountButton = new Button("Create Account");
		
		createAccountButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				if(password.getText().equals(repeatPassword.getText())) {
					Account account = new Account();
					account.setFirstname(firstName.getText());
					account.setLastname(lastName.getText());
					account.setEmail(email.getText());
					account.setPassword(password.getText());
					try {
						AccountDAO.create(account);
						showProfile(stage, account);
					} catch (AccountDAOException adaoe) {
						System.out.println(adaoe.getMessage());
					}
				} else {
					password.setText("");
					repeatPassword.setText("");
				}
			}
		});
		
		Button cancelButton = new Button("Cancel");
		
		cancelButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				showLogin(stage);
			}
		});
		
		
		HBox bottomRow = new HBox(createAccountButton, cancelButton);
		bottomRow.setSpacing(10);
		bottomRow.setAlignment(Pos.CENTER);
		
		
		
		VBox main = new VBox(firstNameLabel, firstName, lastNameLabel, lastName, emailLabel, email, passwordLabel, password, repeatPasswordLabel, repeatPassword, bottomRow);
		main.setSpacing(10);
		main.setAlignment(Pos.TOP_CENTER);
		main.prefWidthProperty().bind(scene.widthProperty());
		main.setPadding(new Insets(10,50,10,50));
		
		((Group) scene.getRoot()).getChildren().add(main);
		
		stage.setTitle("Create Account");
		stage.setScene(scene);
		stage.setHeight(400);
		stage.show();
		
				
	}
	
	private void showProfile(final Stage stage, final Account account) {
		
		
		
		//Get leagues
		List<League> leagues = null;
		try {
			leagues = ManagerDAO.retrieveAllLeaguesFromAccount(account.getEmail());
		} catch (ManagerDAOException ldaoe) {
			System.out.println(ldaoe.getMessage());
		}
		
		
		
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
		
		ScrollPane leagueScrollPane = new ScrollPane();
		
		HBox leaguesHbox = new HBox();
		
		leaguesHbox.setPrefHeight(150);
		leaguesHbox.setPadding(new Insets(10,10,10,10));
		leaguesHbox.setSpacing(30);
		
		
		for(League l : leagues) {
			Label leagueTitle = new Label(l.getLeagueName());
			
			Label numberOfPlayers = new Label("# of players: " + l.getNumber_of_teams());
			
			Button openLeagueButton = new Button("Open");
			
			final League league = new League();
			
			
			Region tileCenterRegion = new Region();
			VBox.setVgrow(tileCenterRegion, Priority.ALWAYS);
			
			openLeagueButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					showLeagueHome(stage, account, league);
				}
			});
			
			
			VBox leagueTile = new VBox(leagueTitle, numberOfPlayers, tileCenterRegion, openLeagueButton);
			leagueTile.setAlignment(Pos.TOP_CENTER);
			leagueTile.setPadding(new Insets(10,10,10,10));
			leagueTile.setBorder(new Border(new BorderStroke(Color.GREY, BorderStrokeStyle.SOLID, new CornerRadii(5.0), BorderWidths.DEFAULT)));
			leaguesHbox.getChildren().add(leagueTile);
		}
		
		
		leagueScrollPane.setContent(leaguesHbox);
		
		Button createLeagueButton = new Button("Create League");
		
		createLeagueButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				showCreateLeague(stage, account);
			}
		});
		
		
		
		main.setSpacing(10);
		main.setPadding(new Insets(10,15,10,10));
		main.setAlignment(Pos.TOP_CENTER);
		main.prefWidthProperty().bind(stage.widthProperty());
		main.prefHeightProperty().bind(stage.heightProperty());
		main.getChildren().addAll(topRow, leagueLabel, leagueScrollPane, createLeagueButton);
		
		((Group) scene.getRoot()).getChildren().addAll(main);
		stage.setWidth(600);
		stage.setHeight(300);
		stage.setTitle("Profile");
		stage.setScene(scene);
		stage.show();
	}
	
	private void showCreateLeague(final Stage stage, final Account account) {
		Scene scene = new Scene(new Group());
		
		final Label leagueNameLabel = new Label("League Name: ");
		
		final TextField leagueName = new TextField("");
		
		final Label numTeamsLabel = new Label("Number of Teams: ");
		
		final TextField numTeams = new TextField();
		
		numTeams.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                for(String s : newValue.split("")) {
                	if(!"0123456789".contains(s)) {
                		numTeams.setText(oldValue);
                	}
                }
            }
        });
		
		final League league = new League();
		
		final DatePicker dp = new DatePicker();
		
		 dp.setOnAction(new EventHandler<ActionEvent>() {
			 @Override
			 public void handle(ActionEvent arg0) {
				 LocalDate draftDate = dp.getValue();
				 league.setDraft_date(new java.sql.Date(draftDate.toEpochDay() * 24 * 60 * 60 * 1000 + (24*60*60*1000)));
			 }
		});
		
		Label draftDateLabel = new Label("Select Draft Date");
		
		Button createBtn = new Button("Create League");
		
		Button cancelButton = new Button("Cancel");
		
		createBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				league.setLeagueName(leagueName.getText());
				league.setNumber_of_teams(Integer.parseInt(numTeams.getText()));
				league.setLeagueID(new Random().nextInt(2147483647));
				try {
					LeagueDAO.create(league);
					Manager manager = new Manager();
					manager.setEmail(account.getEmail());
					manager.setLeague_id(league.getLeagueID());
					try {
						ManagerDAO.create(manager);
						showProfile(stage, account);
					} catch (ManagerDAOException mdaoe) {
						System.out.println(mdaoe.getMessage());
					}
				} catch (LeagueDAOException ldaoe) {
					System.out.println(ldaoe.getMessage());
				}
			}
		});
		
		cancelButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				showProfile(stage, account);
			}
		});
		
		
		VBox createBox = new VBox();
		createBox.setSpacing(10);
		createBox.setPadding(new Insets(10, 10, 10, 10));
		createBox.setAlignment(Pos.TOP_CENTER);
		createBox.prefWidthProperty().bind(scene.widthProperty());
		createBox.getChildren().addAll(leagueNameLabel, leagueName, numTeamsLabel, numTeams, draftDateLabel, dp, createBtn, cancelButton);
		
		((Group) scene.getRoot()).getChildren().addAll(createBox);
		
		stage.setWidth(300);
		stage.setHeight(300);
		stage.setTitle("Create League");
		stage.setScene(scene);
		stage.show();
	}
	
	public void showLeagueHome(final Stage stage, final Account account, final League league) {
		
		Scene scene = new Scene(new Group());
		
		TableView<Manager> managerTable = new TableView<>();
		
		final ObservableList<Manager> managers = FXCollections.observableArrayList();
		
		final Label leagueHomeLabel = new Label("League Home");
		
		TableColumn<Manager, String> managerName = new TableColumn<>("Manager Name");
		TableColumn<Manager, Integer> managerPoints = new TableColumn<>("Points");
		
		managerName.setCellValueFactory(new PropertyValueFactory<Manager, String>("name"));
		managerPoints.setCellValueFactory(new PropertyValueFactory<Manager, Integer>("points"));
		
		managerTable.setEditable(false);
		managerTable.setItems(managers);
		managerTable.getColumns().addAll(managerName, managerPoints);
		managerTable.getSortOrder().add(managerPoints);
		
		Button viewPlayersButton = new Button("View Players");
		
		viewPlayersButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				showDraftPick(stage, account, league);
			}
		});
		
		Region bottomRowRegion = new Region();
		HBox.setHgrow(bottomRowRegion, Priority.ALWAYS);
		
		HBox bottomRow = new HBox(managerTable, bottomRowRegion, viewPlayersButton);
		
		
		
		
		
		VBox main = new VBox(leagueHomeLabel, bottomRow);
		main.setAlignment(Pos.TOP_CENTER);
		main.setSpacing(50);
		main.setPadding(new Insets(10,10,10,10));
		main.prefWidthProperty().bind(stage.widthProperty());
		
		((Group) scene.getRoot()).getChildren().addAll(main);
		stage.setHeight(500);
		stage.setWidth(600);
		stage.setTitle("League Home");
		stage.setScene(scene);
		stage.show();
		
		
	}
	
	public void showDraftPick(final Stage stage, final Account account, final League league) {
		
		
		TableView<Player> playerTable = new TableView<>();
		TableView<Manager> managerTable = new TableView<>();
		TableView<Player> rosterTable = new TableView<>();
		
		final ObservableList<Manager> managers =
				FXCollections.observableArrayList();
		
		final ObservableList<Player> players =
	            FXCollections.observableArrayList();
		
		final ObservableList<Player> roster =
	            FXCollections.observableArrayList();
		
		Scene scene = new Scene(new Group());
		//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
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
		TableColumn<Manager, String> managerEmail = new TableColumn<>("Email");
		
		TableColumn<Player, String> playerFirstName = new TableColumn<>("First Name");
		TableColumn<Player, String> playerLastName = new TableColumn<>("Last Name");
		TableColumn<Player, Integer> playerId = new TableColumn<>("ID");
		TableColumn<Player, String> playerPosition = new TableColumn<>("Position");
		
		TableColumn<Player, String> rosterFirstName = new TableColumn<>("First Name");
		TableColumn<Player, String> rosterLastName = new TableColumn<>("Last Name");
		TableColumn<Player, Integer> rosterPlayerId = new TableColumn<>("ID");
		TableColumn<Player, String> rosterPosition = new TableColumn<>("Position");
		
		// Connect Columns to model attributes
		managerEmail.setCellValueFactory(new PropertyValueFactory<Manager, String>("email"));
		
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
		managerTable.getColumns().addAll(managerEmail);
		
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
        statsBox.setMaxWidth(950);
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
	
	public static void main(String[] args) {
		launch(args);
	}

}
