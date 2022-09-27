package org.minisim;

import static org.minisim.AppConstants.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.stage.Stage;

public final class App extends Application {

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
        new Minisim(stage);
    }

    public static void main(final String[] args) {
        launch(args);
    }
}
