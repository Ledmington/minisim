package org.minisim.utils;

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
