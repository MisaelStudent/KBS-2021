package com.handson.four;

import com.jade.JadeAgent;
import com.clisp.ResourceManager;
import com.clisp.ClispEnv;

import com.jade.AskBehaviour;
import com.jade.TellBehaviour;

public class EvalDynamicClispInput extends JadeAgent
{
    private FourGui gui;
    private ResourceManager rm;
    private ClispEnv clisp_env;

    protected void setup() {
        init("eval-dynamic-clisp");
        addBehaviour(new TellBehaviour(this, obj -> {
                    EvalDynamicClispInput agent = (EvalDynamicClispInput)obj;
                    agent.startEnv();
                    agent.setupClispEnv();
                    agent.show();
                    return true;
        }));
    }

    public void startEnv() {
        clisp_env = new ClispEnv();
        String dir_path_string     = "resources/clisp/";
        rm = new ResourceManager(dir_path_string);
        gui = new FourGui(this);
    }

    public void setupClispEnv() {
        if (rm.isValid()) {
            rm.createResource("four", "hands-on", true);
            rm.createFile("four", "facts", "");
            rm.createFile("four", "rules", "");
            clisp_env.setResourceManager(rm);
        }
    }

    public void show() {
        gui.showGui();
    }

    protected void takeDown() {
        close();
        gui.dispose();
    }

    public void evaluate() {
        clisp_env.run("four");
    }

    @Override
    public void execute() {
        addBehaviour(new AskBehaviour(this, obj -> {
                    EvalDynamicClispInput agent = (EvalDynamicClispInput)obj;
                    agent.evaluate();
                    return true;
        }));
    }

    @Override
    public Object getInputObject() {
        return rm;
    }
}
