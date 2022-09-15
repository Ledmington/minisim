package minisim.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import static minisim.AppConstants.*;

// TODO: maybe this should be a singleton?
public class ViewUtils {
	public static EventHandler<ActionEvent> showAboutDialog() {
		return event -> {
			final Stage stage = new Stage();
			stage.setTitle("About MiniSim");
			final BorderPane pane = new BorderPane();
			pane.setTop(new Label("About MiniSim"));
			pane.setCenter(new Label(String.join("\n", "MiniSim is running on:", " - " + OSName + " " + OSVersion,
					" - Java " + javaVersion, " - JVM " + jvmVersion, " - JavaFX " + javafxVersion)));
			stage.setResizable(false);
			final Scene scene = new Scene(pane);
			stage.setScene(scene);
			stage.showAndWait();
			/*
			 * final Dialog<String> dialog = new Dialog<>();
			 * dialog.setHeaderText("About MiniSim");
			 * dialog.setContentText(String.join("\n", "MiniSim is running on:", OSName +
			 * " " + OSVersion, "Java " + javaVersion, "JVM " + jvmVersion, "JavaFX " +
			 * javafxVersion)); dialog.setResizable(false); Window window =
			 * dialog.getDialogPane().getScene().getWindow(); window.setOnCloseRequest(e ->
			 * window.hide()); dialog.showAndWait();
			 */
		};
	}
}
