package org.minisim.view.images;

import static org.minisim.App.logger;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.image.Image;
import org.minisim.utils.Profiler;

public class ImageManager {
    private ImageManager() {}

    private static final Map<String, Image> images = new HashMap<>();

    public static Image getImage(final ImageName imageName) {
        if (!images.containsKey(imageName.path())) {
            final long ms = Profiler.profile(() -> images.put(imageName.path(), new Image(imageName.path())));
            logger.info(String.format("Loaded image \"%s\" (%d ms)", imageName.path(), ms));
        }
        return images.get(imageName.path());
    }
}
