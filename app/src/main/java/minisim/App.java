package minisim;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import minisim.simulation.Simulation;
import minisim.simulation.force.Friction;
import minisim.simulation.force.Gravity;

public class App extends Application {

	public static Logger logger = null;

	public static final String OSName = System.getProperty("os.name");
	public static final String OSVersion = System.getProperty("os.version");
	public static final String javaVersion = System.getProperty("java.version");
	public static final String jvmVersion = System.getProperty("java.vm.version");
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
	public void start(final Stage stage) {
		BorderPane bPane = new BorderPane();

		final Button aboutButton = new Button("About");
		aboutButton.setOnAction(e -> {
			final Dialog<String> dialog = new Dialog<>();
			dialog.setHeaderText("About MiniSim");
			dialog.setContentText(String.join("\n", "MiniSim is running on:", OSName + " " + OSVersion,
					"Java " + javaVersion, "JVM " + jvmVersion, "JavaFX " + javafxVersion));
			dialog.setResizable(false);
			Window window = dialog.getDialogPane().getScene().getWindow();
			window.setOnCloseRequest(event -> window.hide());
			dialog.showAndWait();
		});

		bPane.setTop(aboutButton);
		bPane.setBottom(new Label("MiniSim v0.1.0"));

		final Canvas canvas = new Canvas(500, 500);
		final GraphicsContext gc = canvas.getGraphicsContext2D();

		bPane.setCenter(canvas);

		final Scene scene = new Scene(bPane);

		stage.setTitle("MiniSim");
		stage.setScene(scene);
		stage.show();

		final Simulation sim = Simulation.builder().nBodies(1000).width(500).height(500)
				// .addForce(new Gravity(-1e-4))
				.addForce(new Gravity(1e-3))
				// .addForce(new GravityDown(0.1))
				.addForce(new Friction(0.99)).solidBorders().build();

		final Task<Integer> task = new Task<>() {
			@Override
			protected Integer call() {
				int iterations;
				for (iterations = 0; iterations < 1000; iterations++) {
					if (isCancelled()) {
						updateMessage("Cancelled");
						logger.info("cancelled");
						break;
					}
					logger.info("iteration: " + iterations);
					updateMessage("Iteration " + iterations);
					updateProgress(iterations, 1000);

					sim.update();
					sim.render(gc);
				}
				return iterations;
			}
		};

		final Thread th = new Thread(task);
		th.setDaemon(true);
		th.start();
	}
	public static void main(final String[] args) {
		logger.info("MiniSim is running on:");
		logger.info(" - " + OSName + " " + OSVersion);
		logger.info(" - Java " + javaVersion);
		logger.info(" - JVM " + jvmVersion);
		logger.info(" - JavaFX " + javafxVersion);
		launch(args);
	}
}