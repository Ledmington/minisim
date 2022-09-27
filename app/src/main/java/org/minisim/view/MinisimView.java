package org.minisim.view;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.minisim.Minisim;

public final class MinisimView {
    public MinisimView(final Minisim model, final Stage stage) {
        final BorderPane bPane = new BorderPane();

        final BorderPane topPane = new BorderPane();
        topPane.setTop(new TopBar());
        topPane.setBottom(new IconsToolBar(model));

        bPane.setTop(topPane);
        bPane.setBottom(new BottomBar());
        bPane.setCenter(new SimulationView(model));

        final Scene scene = new Scene(bPane);

        stage.setTitle("MiniSim");
        stage.setScene(scene);
        stage.show();
    }
}
