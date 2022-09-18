package org.minisim.view.iamges;

public enum ImageName {
    PLAY("/icons/play.png"),
    PAUSE("/icons/pause.png"),
    PLAYBACK("/icons/back.png");

    private final String path;

    private ImageName(final String path) {
        this.path = path;
    }

    public String path() {
        return path;
    }
}
