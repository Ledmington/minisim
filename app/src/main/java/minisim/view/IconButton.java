package minisim.view;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class IconButton extends Button {
	public IconButton(final String iconPath, final double width, final double height) {
		super();
		final ImageView view = new ImageView(new Image(iconPath));
		view.setFitWidth(width);
		view.setFitHeight(height);
		this.setGraphic(view);
	}
}
