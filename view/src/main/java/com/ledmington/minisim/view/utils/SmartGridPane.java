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
package com.ledmington.minisim.view.utils;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;

/**
 * This class exists only to allow calls to add() and addRow() without passing an index.
 */
public class SmartGridPane extends GridPane {

    private int rowIndex = 0;

    public SmartGridPane() {
        super();
    }

    /**
     * Adds a single row containing only the given element with the given column span.
     * @param element
     *      The element to be added.
     * @param colspan
     *      The number of columns it should span in width.
     */
    public void add(final Node element, final int colspan) {
        add(element, 0, rowIndex, colspan, 1);
        rowIndex++;
    }

    /**
     * Appends the given row of Nodes to the bottom.
     * @param children
     * 		The new row of elements.
     */
    public void addRow(final Node... children) {
        addRow(rowIndex, children);
        rowIndex++;
    }
}
