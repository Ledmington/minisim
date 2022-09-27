package org.minisim.view;

import java.util.List;
import org.minisim.utils.SmartGridPane;

public final class BodyInfoPane extends SmartGridPane {
    public BodyInfoPane() {
        super();
        final List<String> parameters2D = List.of("Position", "Speed", "Acceleration", "Force");
        final List<String> parameters = List.of("Mass", "Radius");
        final LabelFactory factory = LabelFactory.getInstance();

        for (String param : parameters2D) {
            add(factory.newLabel(param, MinisimFonts.bold(12)), 2);
            addRow(factory.newLabel("X:"), factory.newLabel("1.0"));
            addRow(factory.newLabel("Y:"), factory.newLabel("-2.0"));
        }

        for (String param : parameters) {
            addRow(factory.newLabel(param + ":", MinisimFonts.bold(12)), factory.newLabel("1.5"));
        }
    }
}
