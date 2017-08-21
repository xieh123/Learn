package com.example.myapplication.widget.graffiti;

import android.graphics.Path;

public class DrawPathEntry {
    private Path path;
    private int paintColor;
    private boolean isEraser;

    public Path getPath() {
        return path;
    }

    public DrawPathEntry(Path path, int paintColor, boolean isEraser) {
        this.path = path;
        this.paintColor = paintColor;
        this.isEraser = isEraser;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public int getPaintColor() {
        return paintColor;
    }

    public void setPaintColor(int paintColor) {
        this.paintColor = paintColor;
    }

    public boolean isEraser() {
        return isEraser;
    }

    public void setEraser(boolean eraser) {
        isEraser = eraser;
    }
}
