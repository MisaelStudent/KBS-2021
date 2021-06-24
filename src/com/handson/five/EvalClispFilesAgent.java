package com.handson.five;

import com.jade.JadeAgent;
import com.clisp.ResourceManager;
import com.clisp.ClispEnv;

public class EvalClispFilesAgent extends JadeAgent
{
    private FiveGui gui;
    private ResourceManager rm;
    private ClispEnv clisp_env;

    protected void setup() {
        init("run-clisp-resources");

        clisp_env = new ClispEnv();
        String dir_path_string     = "resources/clisp/";
        rm = new ResourceManager(dir_path_string);

        if (rm.isValid()) {
            String dir_folders_clisp[] = {"persons", "prodcust", "market"};
            rm.addFolder("person", dir_folders_clisp[0]);
            rm.setNewExecLevelByPattern("person", "rules", 10);

            rm.addFolder("product", dir_folders_clisp[1]);
            rm.setNewExecLevelByPattern("product", "rules", 10);

            rm.addFolder("market", dir_folders_clisp[2]);
            rm.setNewExecLevelByPattern("market", "templates", 0);
            rm.setNewExecLevelByPattern("market", "rules", 2);
            rm.setNewExecLevelByPattern("market", "facts", 3);
            rm.setNewExecLevelByPattern("market", "persons", 10);

            clisp_env.setResourceManager(rm);
        }

        gui = new FiveGui(this);
        gui.showGui();
    }

    protected void takeDown() {
        close();
        gui.dispose();
    }

    @Override
    public void execute() {
        clisp_env.run("person");
        clisp_env.run("product");
        clisp_env.run("market");
    }
}
