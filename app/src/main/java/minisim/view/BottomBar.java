package minisim.view;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class BottomBar extends BorderPane {
    public BottomBar() {
        final Label versionLabel = new Label("MiniSim v0.1.0");

        setRight(versionLabel);
    }
}
