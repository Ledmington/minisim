package org.minisim.view.images;

import java.util.Map;

public class ImageManager {
    private ImageManager() {}

    private static Map<String, Image> images = new HashMap<>();

    public Image getImage(final ImageName imageName) {
        if (!images.containsKey(imageName.path())) {
            images.put(imageName.path(), new Image(imageName.path()));
        }
        return images.get(imageName.path());
    }
}
