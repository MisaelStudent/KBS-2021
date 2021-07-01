package com.clisp;

import java.nio.file.Files;
import java.nio.file.Path;

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

    public String getAbsolutePath() {
        return file.getAbsolutePath();
    }

    public String getContent() {
        try {
            return Files.readString(Path.of(file.getPath()));
        } catch (Exception e) {
            return null;
        }
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
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
            writer.append(content);
            writer.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
