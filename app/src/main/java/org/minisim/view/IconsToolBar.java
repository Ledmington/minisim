package org.minisim.view;

import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import org.minisim.view.images.ImageManager;
import org.minisim.view.images.ImageName;

public final class IconsToolBar extends ToolBar {
    public IconsToolBar() {
        super();

        final Button save = new IconButton(ImageManager.getImage(ImageName.SAVE), 20, 20);
        save.setTooltip(new Tooltip("Save simulation"));
        save.setOnAction(event -> System.out.println("Clicked save"));

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
