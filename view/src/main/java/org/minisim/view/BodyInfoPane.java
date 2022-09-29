package org.minisim.view;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import org.minisim.simulation.body.Body;
import org.minisim.utils.Pair;
import org.minisim.view.utils.SmartGridPane;

public final class BodyInfoPane extends SmartGridPane {

    private final Pair<Label, Label> position = new Pair<>(getDefaultLabel(), getDefaultLabel());
    private final Pair<Label, Label> speed = new Pair<>(getDefaultLabel(), getDefaultLabel());
    private final Pair<Label, Label> acceleration = new Pair<>(getDefaultLabel(), getDefaultLabel());
    private final Pair<Label, Label> force = new Pair<>(getDefaultLabel(), getDefaultLabel());
    private final Label mass = getDefaultLabel();
    private final Label radius = getDefaultLabel();

    public BodyInfoPane() {
        super();

        add(getBoldLabel("Position"), 2);
        addRow(getDefaultLabel("X:"), position.first());
        addRow(getDefaultLabel("Y:"), position.second());

        add(getBoldLabel("Speed"), 2);
        addRow(getDefaultLabel("X:"), speed.first());
        addRow(getDefaultLabel("Y:"), speed.second());

        add(getBoldLabel("Acceleration"), 2);
        addRow(getDefaultLabel("X:"), acceleration.first());
        addRow(getDefaultLabel("Y:"), acceleration.second());

        add(getBoldLabel("Force"), 2);
        addRow(getDefaultLabel("X:"), force.first());
        addRow(getDefaultLabel("Y:"), force.second());

        addRow(getBoldLabel("Mass:"), mass);

        addRow(getBoldLabel("Radius:"), radius);
    }

    private Label getDefaultLabel() {
        return new Label() {
            {
                setText("N/A");
                setAlignment(Pos.CENTER);
                setMaxWidth(Double.MAX_VALUE);
            }
        };
    }

    private Label getDefaultLabel(final String text) {
        return new Label() {
            {
                setText(text);
                setAlignment(Pos.CENTER);
                setMaxWidth(Double.MAX_VALUE);
            }
        };
    }

    private Label getBoldLabel(final String text) {
        return new Label() {
            {
                setText(text);
                setAlignment(Pos.CENTER);
                setMaxWidth(Double.MAX_VALUE);
                setFont(MinisimFonts.bold(12));
            }
        };
    }

    public void updateInfo(final Body selectedBody) {
        position.first().setText(String.valueOf(selectedBody.position().x()));
        position.second().setText(String.valueOf(selectedBody.position().y()));

        speed.first().setText(String.valueOf(selectedBody.speed().x()));
        speed.second().setText(String.valueOf(selectedBody.speed().y()));

        acceleration.first().setText(String.valueOf(selectedBody.acceleration().x()));
        acceleration.second().setText(String.valueOf(selectedBody.acceleration().y()));

        force.first().setText(String.valueOf(selectedBody.force().x()));
        force.second().setText(String.valueOf(selectedBody.force().y()));

        mass.setText(String.valueOf(selectedBody.mass()));

        radius.setText(String.valueOf(selectedBody.radius()));
    }
}
