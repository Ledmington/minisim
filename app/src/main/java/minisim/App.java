package minisim;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.stage.Stage;
import javafx.stage.Window;

public class App extends Application {

	public static Logger logger = null;

	public static final String javaVersion = System.getProperty("java.version");
	public static final String javafxVersion = System.getProperty("javafx.version");

	static {
		InputStream stream = App.class.getClassLoader().getResourceAsStream("logging.properties");
		try {
			LogManager.getLogManager().readConfiguration(stream);
			logger = Logger.getLogger("minisim");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void start(Stage stage) {
		BorderPane bPane = new BorderPane();

		final Button aboutButton = new Button("About");
		aboutButton.setOnAction(e -> {
			final Dialog<String> dialog = new Dialog<>();
			dialog.setHeaderText("About MiniSim");
			dialog.setContentText(
					String.join("\n", "MiniSim is running on:", "Java " + javaVersion, "JavaFX " + javafxVersion));
			dialog.setResizable(false);
			Window window = dialog.getDialogPane().getScene().getWindow();
			window.setOnCloseRequest(event -> window.hide());
			dialog.showAndWait();
		});

		bPane.setTop(aboutButton);
		bPane.setBottom(new Label("MiniSim v0.1.0"));

		final Canvas canvas = new Canvas(300, 300);
		final GraphicsContext gc = canvas.getGraphicsContext2D();
		Stop[] stops;
		LinearGradient gradient;

		stops = new Stop[]{new Stop(0, Color.RED), new Stop(1, Color.BLUE)};
		gradient = new LinearGradient(0.5, 0, 0.5, 1, true, CycleMethod.NO_CYCLE, stops);
		gc.setFill(gradient);
		gc.fillRect(0, 0, 300, 300);
		gc.fill();
		gc.stroke();

		bPane.setCenter(canvas);

		Scene scene = new Scene(bPane);

		stage.setTitle("MiniSim");
		stage.setScene(scene);
		stage.show();
	}
	public static void main(String args[]) {
		logger.info("MiniSim is running on:");
		logger.info(" - Java " + javaVersion);
		logger.info(" - JavaFX " + javafxVersion);
		launch(args);
	}
}