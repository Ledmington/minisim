/*
* minisim - Minimalistic N-Body simulation
* Copyright (C) 2022-2024 Filippo Barbari <filippo.barbari@gmail.com>
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package com.ledmington.minisim.view;

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
