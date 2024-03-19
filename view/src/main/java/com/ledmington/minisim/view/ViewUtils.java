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

import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import com.ledmington.minisim.AppConstants;

// TODO: maybe this should be a singleton?
public final class ViewUtils {
    private ViewUtils() {}

    public static EventHandler<ActionEvent> showAboutDialog() {
        return event -> {
            final Stage stage = new Stage();
            stage.setTitle("About MiniSim");
            final BorderPane pane = new BorderPane();
            pane.setTop(new Label() {
                {
                    setText("About Minisim");
                    setFont(MinisimFonts.bold(12));
                }
            });
            pane.setCenter(new Label(String.join(
                    "\n",
                    "MiniSim is running on:",
                    " - " + AppConstants.OSName + " " + AppConstants.OSVersion,
                    " - Java " + AppConstants.javaVersion,
                    " - JVM " + AppConstants.jvmVersion,
                    " - JavaFX " + AppConstants.javafxVersion)));
            stage.setResizable(false);
            final Scene scene = new Scene(pane);
            stage.setScene(scene);
            stage.showAndWait();
        };
    }

    public static EventHandler<ActionEvent> showExitDialog() {
        return event -> {
            final ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            final ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
            final Alert alert = new Alert(
                    Alert.AlertType.WARNING,
                    "If you exit now, all your unsaved data will be lost.\nDo you wish to exit?",
                    yes,
                    no);
            alert.setTitle("Exit");
            final Optional<ButtonType> result = alert.showAndWait();
            if (result.orElse(no) == yes) {
                System.exit(0);
            }
        };
    }
}
