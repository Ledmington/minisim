package org.minisim.view;

import javafx.application.HostServices;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public final class TopBar extends MenuBar {
    public TopBar(final HostServices hostServices) {
        final Menu fileMenu = new Menu("File");
        fileMenu.getItems().add(new MenuItem("New Simulation"));
        fileMenu.getItems().add(new MenuItem("Save Simulation"));
        fileMenu.getItems().add(new MenuItem("Load Simulation"));
        final MenuItem exitButton = new MenuItem("Exit");
        exitButton.setOnAction(ViewUtils.showExitDialog());
        fileMenu.getItems().add(exitButton);

        final Menu optionsMenu = new Menu("Options");
        optionsMenu.getItems().add(new MenuItem("Appearance"));

        final Menu helpMenu = new Menu("Help");
        final MenuItem authorsButton = new MenuItem("Authors & Contributions");
        authorsButton.setOnAction(e -> new AuthorsDialog(hostServices).showAndWait());
        helpMenu.getItems().add(authorsButton);
        final MenuItem aboutButton = new MenuItem("About");
        aboutButton.setOnAction(ViewUtils.showAboutDialog());
        helpMenu.getItems().add(aboutButton);

        this.getMenus().addAll(fileMenu, optionsMenu, helpMenu);
    }
}
