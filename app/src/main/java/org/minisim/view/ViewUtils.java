package org.minisim.view;

import static org.minisim.AppConstants.*;

import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

// TODO: maybe this should be a singleton?
public final class ViewUtils {
    private ViewUtils() {}

    public static EventHandler<ActionEvent> showAboutDialog() {
        return event -> {
            final Stage stage = new Stage();
            stage.setTitle("About MiniSim");
            final BorderPane pane = new BorderPane();
            pane.setTop(new Label() {
                {
                    setText("About Minisim");
                    setFont(MinisimFonts.bold(12));
                }
            });
            pane.setCenter(new Label(String.join(
                    "\n",
                    "MiniSim is running on:",
                    " - " + OSName + " " + OSVersion,
                    " - Java " + javaVersion,
                    " - JVM " + jvmVersion,
                    " - JavaFX " + javafxVersion)));
            stage.setResizable(false);
            final Scene scene = new Scene(pane);
            stage.setScene(scene);
            stage.showAndWait();
        };
    }

    public static EventHandler<ActionEvent> showExitDialog() {
        return event -> {
            final ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            final ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
            final Alert alert = new Alert(
                    Alert.AlertType.WARNING,
                    "If you exit now, all your unsaved data will be lost.\nDo you wish to exit?",
                    yes,
                    no);
            alert.setTitle("Exit");
            final Optional<ButtonType> result = alert.showAndWait();
            if (result.orElse(no) == yes) {
                System.exit(0);
            }
        };
    }
}
