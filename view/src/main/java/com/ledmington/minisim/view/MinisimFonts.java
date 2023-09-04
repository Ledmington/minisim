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

import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public final class MinisimFonts {
    private MinisimFonts() {}

    private static final String family = "Helvetica";

    public static Font normal(final int size) {
        return Font.font(family, FontWeight.NORMAL, FontPosture.REGULAR, size);
    }

    public static Font bold(final int size) {
        return Font.font(family, FontWeight.BOLD, FontPosture.REGULAR, size);
    }

    public static Font italic(final int size) {
        return Font.font(family, FontWeight.NORMAL, FontPosture.ITALIC, size);
    }
}
