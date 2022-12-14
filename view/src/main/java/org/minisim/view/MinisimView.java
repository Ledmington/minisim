package org.minisim.view;

import javafx.application.HostServices;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.minisim.Minisim;

public final class MinisimView {
    public MinisimView(final Minisim model, final HostServices hostServices, final Stage stage) {
        final BorderPane bPane = new BorderPane();

        final BorderPane topPane = new BorderPane();
        topPane.setTop(new TopBar(hostServices));
        topPane.setBottom(new IconsToolBar(model));

        bPane.setTop(topPane);
        bPane.setBottom(new BottomBar());
        final BorderPane mainPane = new BorderPane();
        final BodyInfoPane bodyInfo = new BodyInfoPane();
        mainPane.setLeft(new SimulationView(model, bodyInfo));
        mainPane.setRight(bodyInfo);
        bPane.setCenter(mainPane);

        final Scene scene = new Scene(bPane);

        stage.setTitle("MiniSim");
        stage.setScene(scene);
        stage.show();
    }
}
