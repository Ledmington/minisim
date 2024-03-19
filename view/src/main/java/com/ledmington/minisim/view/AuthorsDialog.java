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

import java.util.List;

import javafx.application.HostServices;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public final class AuthorsDialog extends Stage {

    private record Contributor(String name, String link, String description) {}

    private static final List<Contributor> contributions = List.of(
            new Contributor("Ledmington", "https://github.com/Ledmington", "Main author and developer"),
            new Contributor("Endum", "https://github.com/Endum", "Contributions to the simulation-updater thread"));

    public AuthorsDialog(final HostServices hostServices) {
        setTitle("MiniSim's authors and contributions");
        final BorderPane pane = new BorderPane();

        final Label title = new Label("Authors and Contributions");
        title.setMaxWidth(Double.MAX_VALUE);
        title.setAlignment(Pos.CENTER);
        title.setFont(MinisimFonts.bold(20));
        pane.setTop(title);

        final GridPane grid = new GridPane();
        for (int i = 0; i < contributions.size(); i++) {
            final Contributor c = contributions.get(i);
            final Hyperlink name = new Hyperlink(c.name()) {
                {
                    // TODO: maybe refactor these lines inside a Factory?
                    setMaxWidth(Double.MAX_VALUE);
                    setAlignment(Pos.CENTER);
                    setFont(MinisimFonts.normal(16));
                }
            };
            name.setOnAction(e -> hostServices.showDocument(c.link()));
            grid.addRow(i, name, new Label() {
                {
                    setText(c.description());
                    setFont(MinisimFonts.italic(12));
                }
            });
        }
        grid.setPadding(new Insets(15));
        grid.setVgap(10);
        pane.setCenter(grid);

        setResizable(false);
        final Scene scene = new Scene(pane);
        setScene(scene);
    }
}
