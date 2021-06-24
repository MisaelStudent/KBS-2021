package com.clisp;

import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;

public class ResourceFile
{
    private String path;
    private String name;
    private File   file;
    private int    exec_level;

    public ResourceFile(File file) {
        this.path = file.getPath();
        this.name = file.getName();
        this.file = file;
    }

    public int getExecLevel() {
        return exec_level;
    }

    public void setExecLevel(int level) {
        exec_level = level;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public String getAbsolutePath() {
        return file.getAbsolutePath();
    }

    public boolean write(String content) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(content);
            writer.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean append(String content) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.append(content);
            writer.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
