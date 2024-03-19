/*
* minisim - Minimalistic N-Body simulation
* Copyright (C) 2022-2024 Filippo Barbari <filippo.barbari@gmail.com>
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package com.ledmington.minisim.view;

import java.util.Optional;

import javafx.geometry.Pos;
import javafx.scene.control.Label;

import com.ledmington.minisim.simulation.SimulationState;
import com.ledmington.minisim.utils.Pair;
import com.ledmington.minisim.view.utils.SmartGridPane;

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

    public void updateInfo(final SimulationState state, final Optional<Integer> selectedBodyIndex) {
        if (selectedBodyIndex.isEmpty()) {
            return;
        }

        final int i = selectedBodyIndex.orElseThrow();

        position.first().setText(String.format("%.3f", state.posx()[i]));
        position.second().setText(String.format("%.3f", state.posy()[i]));

        speed.first().setText(String.format("%.3f", state.speedx()[i]));
        speed.second().setText(String.format("%.3f", state.speedy()[i]));

        acceleration.first().setText(String.format("%.3f", state.accx()[i]));
        acceleration.second().setText(String.format("%.3f", state.accy()[i]));

        force.first().setText(String.format("%.3f", state.forcex()[i]));
        force.second().setText(String.format("%.3f", state.forcey()[i]));

        mass.setText(String.format("%.3f", state.masses()[i]));

        radius.setText(String.format("%.3f", state.radii()[i]));
    }
}
