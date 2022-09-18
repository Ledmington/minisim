package org.minisim.view;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class IconButton extends Button {

    private final double width;
    private final double height;

    public IconButton(final String iconPath, final double width, final double height) {
        super();
        this.width = width;
        this.height = height;
        changeImage(iconPath);
    }

    public void changeImage(final String iconPath) {
        final ImageView view = new ImageView(new Image(iconPath));
        view.setFitWidth(width);
        view.setFitHeight(height);
        this.setGraphic(view);
    }
}
