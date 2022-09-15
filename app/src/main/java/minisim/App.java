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
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import minisim.simulation.Simulation;
import minisim.simulation.force.Friction;
import minisim.simulation.force.Gravity;
import minisim.view.BottomBar;
import minisim.view.TopBar;

import static minisim.AppConstants.*;

public class App extends Application {

	public static Logger logger = null;

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

		bPane.setTop(new TopBar());
		bPane.setBottom(new BottomBar());

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
				final int maxIterations = 10;
				for (iterations = 0; iterations < maxIterations; iterations++) {
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