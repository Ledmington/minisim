package org.minisim.view;

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
