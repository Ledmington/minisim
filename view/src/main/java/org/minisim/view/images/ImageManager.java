package org.minisim.view.images;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.image.Image;

import org.minisim.utils.MiniLogger;
import org.minisim.utils.Profiler;

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
