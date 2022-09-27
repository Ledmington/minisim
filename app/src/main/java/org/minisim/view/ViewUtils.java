package org.minisim.view;

import static org.minisim.AppConstants.*;

import java.awt.*;
import java.util.List;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.minisim.utils.DesktopUtils;

// TODO: maybe this should be a singleton?
public final class ViewUtils {
    private ViewUtils() {}

    private record Contributor(String name, String link, String description) {}

    private static final List<Contributor> contributions = List.of(
            new Contributor("Ledmington", "https://github.com/Ledmington", "Main author and developer"),
            new Contributor("Endum", "https://github.com/Endum", "Contributions to the simulation-updater thread"));

    public static EventHandler<ActionEvent> showAboutDialog() {
        return event -> {
            final Stage stage = new Stage();
            stage.setTitle("About MiniSim");
            final BorderPane pane = new BorderPane();
            pane.setTop(new Label("About MiniSim"));
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

    public static EventHandler<ActionEvent> showAuthorsDialog() {
        return event -> {
            final Stage stage = new Stage();

            stage.setTitle("MiniSim's authors and contributions");
            final BorderPane pane = new BorderPane();

            final Label title = new Label("Authors and Contributions");
            title.setMaxWidth(Double.MAX_VALUE);
            title.setAlignment(Pos.CENTER);
            title.setFont(MinisimFonts.bold(20));
            pane.setTop(title);

            final GridPane grid = new GridPane();
            for (int i = 0; i < contributions.size(); i++) {
                final Contributor c = contributions.get(i);
                final Hyperlink name = new Hyperlink(c.name()) {
                    {
                        setMaxWidth(Double.MAX_VALUE);
                        setAlignment(Pos.CENTER);
                        setFont(MinisimFonts.normal(16));
                    }
                };
                name.setOnAction(e -> DesktopUtils.safeBrowse(c.link()));
                grid.addRow(i, name, new Label(c.description()) {
                    {
                        setMaxWidth(Double.MAX_VALUE);
                        setAlignment(Pos.CENTER);
                        setFont(MinisimFonts.italic(12));
                    }
                });
            }
            grid.setPadding(new Insets(15));
            grid.setVgap(10);
            pane.setCenter(grid);

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
