package org.minisim.view;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

public final class LabelFactory {

    private static LabelFactory instance = null;

    public static LabelFactory getInstance() {
        if (instance == null) {
            instance = new LabelFactory();
        }
        return instance;
    }

    public Label newLabel(final String text, final Font font) {
        return new Label(text) {
            {
                setMaxWidth(Double.MAX_VALUE);
                setAlignment(Pos.CENTER);
                setFont(font);
            }
        };
    }

    public Label newLabel(final String text) {
        return newLabel(text, MinisimFonts.normal(12));
    }
}
