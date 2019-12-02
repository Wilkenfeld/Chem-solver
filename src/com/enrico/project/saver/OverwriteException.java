package com.enrico.project.saver;

import java.io.File;

public class OverwriteException extends Exception {
    public OverwriteException(File file) {
        super("Do you want to overwrite file " + file.getAbsolutePath() + "?");
    }
}
