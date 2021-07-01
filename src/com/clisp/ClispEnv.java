package com.clisp;

import net.sf.clipsrules.jni.*;

public class ClispEnv {
    Environment env; // = new Environment();
    ResourceManager rm;

    public ClispEnv() {
        env = new Environment();
        rm  = new ResourceManager("resources/"); // Default path
    }

    public void setResourceManager(ResourceManager rm) {
        this.rm = rm;
    }

    public void run(String key) {
        env.eval("(clear)");
        ResourceNode node = rm.getNode(key);
        for (ResourceFile rf : node.getResourceFiles()) {
            // env.load(rf.getAbsolutePath());
            String content = rf.getContent();
            if (content != null) {
                env.eval(content);
            }
        }
        env.run();
    }
}
