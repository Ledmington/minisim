package minisim.view;

import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.stage.Window;

import static minisim.AppConstants.*;

// TODO: maybe this should be a singleton?
public class AboutButton extends Button {
	public AboutButton() {
		super("About");
		setOnAction(e -> {
			final Dialog<String> dialog = new Dialog<>();
			dialog.setHeaderText("About MiniSim");
			dialog.setContentText(String.join("\n", "MiniSim is running on:", OSName + " " + OSVersion,
					"Java " + javaVersion, "JVM " + jvmVersion, "JavaFX " + javafxVersion));
			dialog.setResizable(false);
			Window window = dialog.getDialogPane().getScene().getWindow();
			window.setOnCloseRequest(event -> window.hide());
			dialog.showAndWait();
		});
	}
}
