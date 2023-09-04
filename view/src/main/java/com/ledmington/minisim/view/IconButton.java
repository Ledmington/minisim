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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public final class IconButton extends Button {

    private final double width;
    private final double height;

    public IconButton(final Image img, final double width, final double height) {
        super();
        this.width = width;
        this.height = height;
        changeImage(img);
    }

    public void changeImage(final Image img) {
        final ImageView view = new ImageView(img);
        view.setFitWidth(width);
        view.setFitHeight(height);
        this.setGraphic(view);
    }
}
