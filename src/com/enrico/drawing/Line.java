package com.enrico.drawing;

public class Line {
    private final int startX;
    private final int endX;

    private final int startY;
    private final int endY;

    public Line(int startX, int endX, int startY, int endY) {
        this.startX = startX;
        this.endX = endX;

        this.startY = startY;
        this.endY = endY;
    }

    public int getStartX() {
        return startX;
    }

    public int getEndX() {
        return endX;
    }

    public int getStartY() {
        return startY;
    }

    public int getEndY() {
        return endY;
    }
}
