package org.minisim.view;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

public final class LabelBuilder {

    private String text = "";
    private Pos alignment = Pos.CENTER;
    private Font font = MinisimFonts.normal(12);

    public LabelBuilder() {}

    public LabelBuilder text(final String text) {
        this.text = text;
        return this;
    }

    public LabelBuilder alignment(final Pos alignment) {
        this.alignment = alignment;
        return this;
    }

    public LabelBuilder font(final Font font) {
        this.font = font;
        return this;
    }

    public Label build() {
        return new Label() {
            {
                setText(text);
                setAlignment(alignment);
                setFont(font);
                setMaxWidth(Double.MAX_VALUE);
            }
        };
    }
}
