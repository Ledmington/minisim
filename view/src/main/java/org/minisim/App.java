package org.minisim;

import javafx.application.Application;
import javafx.stage.Stage;

public final class App extends Application {

    @Override
    public void start(final Stage stage) {
        new Minisim(getHostServices(), stage);
    }

    public static void main(final String[] args) {
        Application.launch(args);
    }
}
