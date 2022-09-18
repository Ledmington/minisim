package org.minisim.view;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class IconButton extends Button {

    private final double width;
    private final double height;

    public IconButton(final Image img, final double width, final double height) {
        super();
        this.width = width;
        this.height = height;
        changeImage(img);
    }

    public void changeImage(final Image img) {
        final ImageView view = new ImageView(img);
        view.setFitWidth(width);
        view.setFitHeight(height);
        this.setGraphic(view);
    }
}
