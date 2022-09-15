package minisim.view;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class TopBar extends MenuBar {
	public TopBar() {
		final Menu fileMenu = new Menu("File");
		final Menu optionsMenu = new Menu("Options");

		final Menu helpMenu = new Menu("Help");
		final MenuItem aboutButton = new MenuItem("About");
		aboutButton.setOnAction(ViewUtils.showAboutDialog());
		helpMenu.getItems().add(aboutButton);

		this.getMenus().addAll(fileMenu, optionsMenu, helpMenu);
	}
}
