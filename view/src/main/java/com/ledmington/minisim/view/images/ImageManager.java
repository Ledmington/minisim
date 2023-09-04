/*
* minisim - Minimalistic N-Body simulation
* Copyright (C) 2022-2023 Filippo Barbari <filippo.barbari@gmail.com>
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

import java.util.HashMap;
import java.util.Map;

import javafx.scene.image.Image;

import com.ledmington.minisim.utils.MiniLogger;
import com.ledmington.minisim.utils.Profiler;

public final class ImageManager {
    private ImageManager() {}

    private static final Map<String, Image> images = new HashMap<>();
    private static final MiniLogger logger = MiniLogger.getLogger("minisim");

    public static Image getImage(final ImageName imageName) {
        if (!images.containsKey(imageName.path())) {
            final long ns = Profiler.profile(() -> images.put(imageName.path(), new Image(imageName.path())));
            logger.info("Loaded image \"%s\" (%.3f ms)", imageName.path(), (double) ns / 1_000_000.0);
        }
        return images.get(imageName.path());
    }
}
