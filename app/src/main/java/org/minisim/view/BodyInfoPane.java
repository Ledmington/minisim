package org.minisim.view;

import javafx.scene.control.Label;
import org.minisim.simulation.body.Body;
import org.minisim.utils.Pair;
import org.minisim.utils.SmartGridPane;

public final class BodyInfoPane extends SmartGridPane {

    private final Pair<Label, Label> position = new Pair<>(
            new LabelBuilder().text("N/A").build(),
            new LabelBuilder().text("N/A").build());
    private final Pair<Label, Label> speed = new Pair<>(
            new LabelBuilder().text("N/A").build(),
            new LabelBuilder().text("N/A").build());
    private final Pair<Label, Label> acceleration = new Pair<>(
            new LabelBuilder().text("N/A").build(),
            new LabelBuilder().text("N/A").build());
    private final Pair<Label, Label> force = new Pair<>(
            new LabelBuilder().text("N/A").build(),
            new LabelBuilder().text("N/A").build());
    private final Label mass = new LabelBuilder().text("N/A").build();
    private final Label radius = new LabelBuilder().text("N/A").build();

    public BodyInfoPane() {
        super();

        add(new LabelBuilder().text("Position").font(MinisimFonts.bold(12)).build(), 2);
        addRow(new LabelBuilder().text("X:").build(), position.first());
        addRow(new LabelBuilder().text("Y:").build(), position.second());

        add(new LabelBuilder().text("Speed").font(MinisimFonts.bold(12)).build(), 2);
        addRow(new LabelBuilder().text("X:").build(), speed.first());
        addRow(new LabelBuilder().text("Y:").build(), speed.second());

        add(new LabelBuilder().text("Acceleration").font(MinisimFonts.bold(12)).build(), 2);
        addRow(new LabelBuilder().text("X:").build(), acceleration.first());
        addRow(new LabelBuilder().text("Y:").build(), acceleration.second());

        add(new LabelBuilder().text("Force").font(MinisimFonts.bold(12)).build(), 2);
        addRow(new LabelBuilder().text("X:").build(), force.first());
        addRow(new LabelBuilder().text("Y:").build(), force.second());

        addRow(new LabelBuilder().text("Mass:").font(MinisimFonts.bold(12)).build(), mass);

        addRow(new LabelBuilder().text("Radius:").font(MinisimFonts.bold(12)).build(), radius);
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
