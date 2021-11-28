package com.company;

import java.io.File;
import java.io.Serial;
import java.io.Serializable;

public class ConfigData implements Serializable {

    @Serial
    private static final long serialVersionUID = 0xA;

    private String savePath;
    private String exportPath;

    /**
     * Calls constructor with default parameters.
     */
    public ConfigData() {
        this("./saves/", "./exports/");
    }

    /**
     * Creates the config data and creates any necessary file directories that don't exist.
     * @param savePath - path of the directory where hats should be saved.
     * @param exportPath - path of the directory where hats should be exported.
     */
    public ConfigData(String savePath, String exportPath) {
        this.savePath = savePath;
        this.exportPath = exportPath;

        File saveDir = new File(savePath);
        if(!saveDir.exists()){
            if(!saveDir.mkdirs()) {
                System.err.println("Could not create save directory.");
            }
        }

        File exportDir = new File(exportPath);
        if(!exportDir.exists()){
            if(!exportDir.mkdirs()) {
                System.err.println("Could not create export directory.");
            }
        }
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    public void setExportPath(String exportPath) {
        this.exportPath = exportPath;
    }

    public String getSavePath() {
        return savePath;
    }

    public String getExportPath() {
        return exportPath;
    }
}
