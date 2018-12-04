package GUI;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.scene.control.Label;
import DAOs.PlayerDAO;
import DAOs.RosterDAO;
import Exceptions.DPSDAOException;
import Exceptions.OPSDAOException;
import Exceptions.RosterDAOException;
import Models.*;

public class PointCalculator implements Runnable{
	
	private ObservableList<Manager> managers;
	private TableView<Manager> managerTable;
	private Label loadingLabel;
	
	public PointCalculator(ObservableList<Manager> managers, TableView<Manager> managerTable, Label loadingLabel) {
		this.managers = managers;
		this.managerTable = managerTable;
		this.loadingLabel = loadingLabel;
	}
	public void run() {
		loadingLabel.setOpacity(100);
		for(Manager m : this.managers) {
			int totalPoints = 0;
			try {
				for(Player p : RosterDAO.retrieveManagersRoster(m.getManagerID())) {
					System.out.println(p);
					try {
						totalPoints += PlayerDAO.getPoints(p.getPlayerID());
					} catch (OPSDAOException opsdaoe) {
						System.out.println(opsdaoe.getMessage());
					} catch (DPSDAOException dpsdaoe) {
						System.out.println(dpsdaoe.getMessage());
					}
				}
			} catch (RosterDAOException rdaoe) {
				System.out.println(rdaoe.getMessage());
			}
			m.setPoints(totalPoints);
		}
		managerTable.refresh();
		loadingLabel.setOpacity(0);
		System.out.println("Done");
	}
}
