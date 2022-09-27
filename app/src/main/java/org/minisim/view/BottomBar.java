package org.minisim.view;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public final class BottomBar extends BorderPane {
    public BottomBar() {
        final Label versionLabel = new Label("MiniSim v0.1.0") {
            {
                setFont(MinisimFonts.normal(12));
            }
        };

        setRight(versionLabel);
    }
}
