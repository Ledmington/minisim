/*
* minisim - Minimalistic N-Body simulation
* Copyright (C) 2022-2023 Filippo Barbari <filippo.barbari@gmail.com>
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
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import com.ledmington.minisim.Minisim;

public final class MinisimView {
    public MinisimView(final Minisim model, final HostServices hostServices, final Stage stage) {
        final BorderPane bPane = new BorderPane();

        final BorderPane topPane = new BorderPane();
        topPane.setTop(new TopBar(hostServices));
        topPane.setBottom(new IconsToolBar(model));

        bPane.setTop(topPane);
        bPane.setBottom(new BottomBar());
        final BorderPane mainPane = new BorderPane();
        final BodyInfoPane bodyInfo = new BodyInfoPane();
        mainPane.setLeft(new SimulationView(model, bodyInfo));
        mainPane.setRight(bodyInfo);
        bPane.setCenter(mainPane);

        final Scene scene = new Scene(bPane);

        stage.setTitle("MiniSim");
        stage.setScene(scene);
        stage.show();
    }
}
