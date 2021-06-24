package com.clisp;

import java.io.File;
import java.util.HashMap;

// TODO: We really don't want all files open all the time, a File manager will
// be better for this. To controll all files and only have cached the ones we are
// using.
public class ResourceManager
{
    private String                        resource_root;
    private File                          dir_root;
    private HashMap<String, ResourceNode> nodes;
    private boolean                       root_exist;

    public ResourceManager(String path) {
        this.resource_root = path;
        this.nodes = new HashMap<String, ResourceNode>();
        this.dir_root = new File(path);

        if (dir_root.exists()) {
            root_exist = true;
        }
     }

    public boolean addFolder(String key, String folder)
    {
        if (!root_exist) {
            return false;
        }
        ResourceNode node = new ResourceNode(String.format("%s/%s", resource_root, folder));
        if (!node.isValid()) {
            return false;
        }
        node.getResources();
        return nodes.put(key, node) != null;
    }

    public void setNewExecLevelByPattern(String key, String pattern, int level) {
        ResourceNode node = nodes.get(key);
        if (node != null) {
            node.setNewExecLevelByPattern(pattern, level);
        }
    }

    public String[] getNodeResourceNames(String key) {
        ResourceNode node = nodes.get(key);
        if (node != null) {
            return node.getResourceNamesByExecOrder();
        }
        return null;
    }

    public boolean isValid() {
        return root_exist;
    }

    public ResourceNode getNode(String key) {
        return nodes.get(key);
    }

    public boolean createResource(String key, String path, boolean is_tmp) {
        if (!is_tmp) {
            path = String.format("%s/%s", resource_root, path);
        }
        ResourceNode node = new ResourceNode(path);
        if (node.isValid()) {
            return false;
        }
        nodes.put(key, node);
        if (is_tmp) {
            return node.createTmp();
        }
        return node.create();
    }

    public boolean appendToFile(String key, String name, String content) {
        ResourceNode node = nodes.get(key);
        return node.appendToFile(name, content);
    }

    public boolean createFile(String key, String name, String content) {
        ResourceNode node = nodes.get(key);
        return node.createFile(name, content);
    }
}
