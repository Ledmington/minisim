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
import minisim.border.SolidBorders;

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

		final Canvas canvas = new Canvas(500, 500);
		final GraphicsContext gc = canvas.getGraphicsContext2D();

		bPane.setCenter(canvas);

		final Scene scene = new Scene(bPane);

		stage.setTitle("MiniSim");
		stage.setScene(scene);
		stage.show();

		final Simulation sim = new Simulation(1000, new SolidBorders(500, 500), 1e-4, 0.99);

		final Task<Integer> task = new Task<Integer>() {
			@Override
			protected Integer call() throws Exception {
				int iterations;
				for (iterations = 0; iterations < 1000; iterations++) {
					if (isCancelled()) {
						updateMessage("Cancelled");
						System.out.println("cancelled");
						break;
					}
					System.out.println("iteration: " + iterations);
					updateMessage("Iteration " + iterations);
					updateProgress(iterations, 1000);

					sim.update();
					sim.render(gc);

					// Now block the thread for a short time, but be sure
					// to check the interrupted exception for cancellation!
					// try {
					// Thread.sleep(100);
					// } catch (InterruptedException interrupted) {
					// if (isCancelled()) {
					// updateMessage("Cancelled");
					// break;
					// }
					// }
				}
				return iterations;
			}
		};

		final Thread th = new Thread(task);
		th.setDaemon(true);
		th.start();
	}
	public static void main(String args[]) {
		logger.info("MiniSim is running on:");
		logger.info(" - Java " + javaVersion);
		logger.info(" - JavaFX " + javafxVersion);
		launch(args);
	}
}