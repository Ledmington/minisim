package org.minisim.view;

import java.util.List;
import org.minisim.utils.SmartGridPane;

public final class BodyInfoPane extends SmartGridPane {
    public BodyInfoPane() {
        super();
        final List<String> parameters2D = List.of("Position", "Speed", "Acceleration", "Force");
        final List<String> parameters = List.of("Mass", "Radius");

        for (String param : parameters2D) {
            add(new LabelBuilder().text(param).font(MinisimFonts.bold(12)).build(), 2);
            addRow(
                    new LabelBuilder().text("X:").build(),
                    new LabelBuilder().text("1.0").build());
            addRow(
                    new LabelBuilder().text("Y:").build(),
                    new LabelBuilder().text("-2.0").build());
        }

        for (String param : parameters) {
            addRow(
                    new LabelBuilder()
                            .text(param + ":")
                            .font(MinisimFonts.bold(12))
                            .build(),
                    new LabelBuilder().text("1.5").build());
        }
    }
}
