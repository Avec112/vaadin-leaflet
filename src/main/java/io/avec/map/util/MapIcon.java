package io.avec.map.util;

import lombok.Getter;

@Getter
public enum MapIcon {

    HOTEL("images/marker-icon-orange.png"),
    RESTURANT("images/marker-icon-yellow.png"),
    MUSEUM("images/marker-icon-violet.png");

    private final String path;

    MapIcon(String path) {
        this.path = path;
    }
}
