package minisim.view;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class TopBar extends MenuBar {
	public TopBar() {
		final Menu fileMenu = new Menu("File");
		fileMenu.getItems().add(new MenuItem("New Simulation"));
		fileMenu.getItems().add(new MenuItem("Save Simulation"));
		fileMenu.getItems().add(new MenuItem("Load Simulation"));
		final MenuItem exitButton = new MenuItem("Exit");
		exitButton.setOnAction(ViewUtils.showExitDialog());
		fileMenu.getItems().add(exitButton);

		final Menu optionsMenu = new Menu("Options");

		final Menu helpMenu = new Menu("Help");
		final MenuItem authorsButton = new MenuItem("Authors & Contributions");
		authorsButton.setOnAction(ViewUtils.showAuthorsDialog());
		helpMenu.getItems().add(authorsButton);
		final MenuItem aboutButton = new MenuItem("About");
		aboutButton.setOnAction(ViewUtils.showAboutDialog());
		helpMenu.getItems().add(aboutButton);

		this.getMenus().addAll(fileMenu, optionsMenu, helpMenu);
	}
}
