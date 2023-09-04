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

import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;

import com.ledmington.minisim.Minisim;
import com.ledmington.minisim.view.images.ImageManager;
import com.ledmington.minisim.view.images.ImageName;

public final class IconsToolBar extends ToolBar {
    public IconsToolBar(final Minisim model) {
        super();

        final Button save = new IconButton(ImageManager.getImage(ImageName.SAVE), 20, 20);
        save.setTooltip(new Tooltip("Save simulation"));
        save.setOnAction(event -> model.getFrameManager().createVideo());

        final Button load = new IconButton(ImageManager.getImage(ImageName.SAVE), 20, 20);
        load.setTooltip(new Tooltip("Load simulation"));
        load.setOnAction(event -> System.out.println("Clicked load"));

        final Separator sep = new Separator();

        final Button settings = new IconButton(ImageManager.getImage(ImageName.SETTINGS), 20, 20);
        settings.setTooltip(new Tooltip("Open settings"));
        settings.setOnAction(event -> System.out.println("Clicked settings"));

        getItems().addAll(save, load, sep, settings);
    }
}
