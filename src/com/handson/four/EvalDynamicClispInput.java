package com.handson.four;

import jade.core.behaviours.OneShotBehaviour;

import com.jade.JadeAgent;
import com.clisp.ResourceManager;
import com.clisp.ClispEnv;

public class EvalDynamicClispInput extends JadeAgent
{
    private FourGui gui;
    private ResourceManager rm;
    private ClispEnv clisp_env;

    protected void setup() {
        init("eval-dynamic-clisp");

        clisp_env = new ClispEnv();
        String dir_path_string     = "resources/clisp/";
        rm = new ResourceManager(dir_path_string);

        if (rm.isValid()) {
            rm.createResource("four", "hands-on", true);
            rm.createFile("four", "facts", "");
            rm.createFile("four", "rules", "");
            clisp_env.setResourceManager(rm);
        }

        gui = new FourGui(this);
        gui.showGui();
    }

    protected void takeDown() {
        close();
        gui.dispose();
    }

    @Override
    public void execute() {
        clisp_env.run("four");
    }

    @Override
    public Object getInputObject() {
        return rm;
    }
}
