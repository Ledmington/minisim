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
package com.ledmington.minisim.view.images;

public enum ImageName {
    APPLICATION_ICON("/icons/atom.png"),
    PLAY("/icons/play.png"),
    PAUSE("/icons/pause.png"),
    PLAYBACK("/icons/back.png"),
    SAVE("/icons/save.png"),
    SETTINGS("/icons/settings.png");

    private final String path;

    ImageName(final String path) {
        this.path = path;
    }

    public String path() {
        return path;
    }
}
