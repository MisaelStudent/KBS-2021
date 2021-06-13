package com.clisp;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Files;

import java.util.ArrayList;
import java.util.HashMap;

public class ResourceNode
{
    private String                        folder_path;
    private File                          folder;
    private HashMap<String, ResourceFile> files;
    private ArrayList<ResourceFile>       files_exec_order;
    private boolean                       folder_exists;

    public ResourceNode(String f) {
        folder_path = f;
        folder = new File(f);
        if (folder.exists() && folder.isDirectory()) {
            folder_exists = true;
        }
        files = new HashMap<String, ResourceFile>();
        files_exec_order = new ArrayList<ResourceFile>();
    }

    public void sortExecOrder() {
        files_exec_order.sort((a, b) -> {
                return a.getExecLevel() - b.getExecLevel();
            });
    }

    public String[] getResourceNamesByExecOrder() {
        String[] names = new String[files_exec_order.size()];
        int i = 0;
        for (ResourceFile rf : files_exec_order) {
            names[i++] = rf.getName();
        }
        return names;
    }

    public void getResources() {
        if (isValid() == false) {
            return;
        }

        for (File file : folder.listFiles()) {
            String name = file.getName();
            if (name.endsWith(".clp") && file.isFile()) {
                ResourceFile rf = new ResourceFile(file);
                files.put(name.split("\\.")[0], rf);
                files_exec_order.add(rf);
            }
        }
    }

    public void setNewExecLevelByPattern(String pattern, int level) {
        for (ResourceFile rf : files_exec_order) {
            if (rf.getName().contains(pattern)) {
                rf.setExecLevel(level);
            }
        }
        sortExecOrder();
    }

    public boolean isValid() {
        return folder_exists;
    }

    public boolean createTmp() {
        try {
            Path path = Files.createTempDirectory(folder_path);
            folder = path.toFile();
            folder_exists = folder.exists();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return folder_exists;
    }

    public boolean create() {
        folder_exists = folder.mkdir();
        return folder_exists;
    }

    public ArrayList<ResourceFile> getResourceFiles() {
        return files_exec_order;
    }

    public boolean createFile(String name, String content) {
        File file = new File(String.format("%s/%s.clp", folder.getAbsolutePath(), name));
        try {
            file.createNewFile();
            file.setWritable(true);
        } catch (Exception e) {
            return false;
        }
        ResourceFile rf = new ResourceFile(file);
        files.put(name, rf);
        files_exec_order.add(rf);
        return rf.write(content);
    }

    public boolean appendToFile(String name, String content) {
        ResourceFile rf = files.get(name);
        return rf.append(content);
    }
}
