package com.enrico.drawing;

import java.util.ArrayList;

public class LineGroup {
    private ArrayList<Line> lines;

    public LineGroup() {
        lines = new ArrayList<>();
    }

    public ArrayList<Line> getLines() {
        return lines;
    }

    public void addLine(Line line) {
        lines.add(line);
    }
}
