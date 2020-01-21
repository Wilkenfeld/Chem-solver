package com.enrico.interfaces;

import java.net.URL;

public interface GraphicalAtom {
    default URL getImageUrl(String path) {
        return getClass().getClassLoader().getResource(path);
    }
}
