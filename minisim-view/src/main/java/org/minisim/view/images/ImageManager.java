package org.minisim.view.images;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import javafx.scene.image.Image;
import org.minisim.utils.Profiler;

public final class ImageManager {
    private ImageManager() {}

    private static final Map<String, Image> images = new HashMap<>();
    private static final Logger logger = Logger.getLogger("minisim");

    public static Image getImage(final ImageName imageName) {
        if (!images.containsKey(imageName.path())) {
            final long ms = Profiler.profile(() -> images.put(imageName.path(), new Image(imageName.path())));
            logger.info(String.format("Loaded image \"%s\" (%d ms)", imageName.path(), ms));
        }
        return images.get(imageName.path());
    }
}
