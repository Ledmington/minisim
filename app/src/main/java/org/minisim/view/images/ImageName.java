package org.minisim.view.images;

public enum ImageName {
    APPLICATION_ICON("/icons/atom.ico"),
    PLAY("/icons/play.png"),
    PAUSE("/icons/pause.png"),
    PLAYBACK("/icons/back.png"),
    SAVE("/icons/save.png"),
    SETTINGS("/icons/settings.png");

    private final String path;

    private ImageName(final String path) {
        this.path = path;
    }

    public String path() {
        return path;
    }
}
