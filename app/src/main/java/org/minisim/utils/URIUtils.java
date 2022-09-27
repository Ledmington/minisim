package org.minisim.utils;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * TODO: write doc
 */
public final class URIUtils {
    private URIUtils() {}

    /**
     * TODO: write doc
     * @param uri
     * @return
     */
    public static URI safeCreate(final String uri) {
        try {
            return new URI(uri);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
