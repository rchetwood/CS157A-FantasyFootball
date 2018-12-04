use CS157AFFTestingDB;
CREATE TABLE Account
(
  firstName VARCHAR(15) NOT NULL,
  lastName VARCHAR(15) NOT NULL,
  email VARCHAR(31) NOT NULL,
  password VARCHAR(31) NOT NULL,
  PRIMARY KEY (email)
);

CREATE TABLE League
(
  League_ID INT NOT NULL,
  Number_of_Teams INT NOT NULL CHECK(Number_of_Teams >= 2),
  Draft_Date DATE NOT NULL,
  name VARCHAR(32) NOT NULL,
  PRIMARY KEY (League_ID)
);

CREATE TABLE Manager
(
  Manager_ID INT NOT NULL AUTO_INCREMENT,
  email VARCHAR(31) NOT NULL,
  League_ID INT NOT NULL,
  PRIMARY KEY (Manager_ID),
  FOREIGN KEY (email) REFERENCES Account(email),
  FOREIGN KEY (League_ID) REFERENCES League(League_ID)
);

CREATE TABLE Offensive_Performance_Statistics
(
  Passing_Comp INT NOT NULL DEFAULT 0,
  Passing_Yards INT NOT NULL DEFAULT 0,
  Passing_TD INT NOT NULL DEFAULT 0,
  Passing_Int INT NOT NULL DEFAULT 0,
  Rushes INT NOT NULL DEFAULT 0,
  Rushing_Yards INT NOT NULL DEFAULT 0,
  Rushing_TD INT NOT NULL DEFAULT 0,
  Receiving_Receptions INT NOT NULL DEFAULT 0,
  Receiving_Yards INT NOT NULL DEFAULT 0,
  Receiving_TD INT NOT NULL DEFAULT 0,
  Receiving_Target INT NOT NULL DEFAULT 0,
  Two_point_conversion INT NOT NULL DEFAULT 0,
  Fumbles INT NOT NULL DEFAULT 0,
  misc_TD INT NOT NULL DEFAULT 0,
  Points INT NOT NULL DEFAULT 0,
  Offensive_Stats_ID INT NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (Offensive_Stats_ID)
);

CREATE TABLE Defensive_Performance_Statistics
(
  TD INT NOT NULL DEFAULT 0,
  Interceptions INT NOT NULL DEFAULT 0,
  Fumbles_Recovered INT NOT NULL DEFAULT 0,
  Sacks INT NOT NULL DEFAULT 0,
  Safeties INT NOT NULL DEFAULT 0,
  Blocked_Punts INT NOT NULL DEFAULT 0,
  Points INT NOT NULL DEFAULT 0,
  Defensive_Stats_ID INT NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (Defensive_Stats_ID)
);

CREATE TABLE Player
(
  Player_ID INT NOT NULL AUTO_INCREMENT,
  FIrst_Name VARCHAR(15) NOT NULL,
  Last_Name VARCHAR(31) NOT NULL,
  Position VARCHAR(3) NOT NULL,
  Offense BOOLEAN NOT NULL,
  Defensive_Stats_ID INT NOT NULL,
  Offensive_Stats_ID INT NOT NULL,
  PRIMARY KEY (Player_ID),
  FOREIGN KEY (Defensive_Stats_ID) REFERENCES Defensive_Performance_Statistics(Defensive_Stats_ID),
  FOREIGN KEY (Offensive_Stats_ID) REFERENCES Offensive_Performance_Statistics(Offensive_Stats_ID)
);

CREATE TABLE Roster (
  Manager_ID INT,
  Player_ID INT,
  FOREIGN KEY (Manager_ID) REFERENCES Manager(Manager_ID),
  FOREIGN KEY (Player_ID) REFERENCES Player(Player_ID)
);
