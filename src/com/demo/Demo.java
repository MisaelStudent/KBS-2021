package com.demo;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.ArrayList;

import com.clisp.*;

public class Demo
{
    public static void main(String[] args) {
        ClispEnv clisp_env = new ClispEnv();

        String dir_path_string     = "resources/clisp/";
        String dir_folders_clisp[] = {"persons", "prodcust", "market"};
        ResourceManager rm = new ResourceManager(dir_path_string);

        if (rm.isValid()) {
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

            // NOTE: Files given in class contain errors...
            clisp_env.run("person");
            clisp_env.run("product");
            clisp_env.run("market");

            // NOTE: Create new temporary resources to execute clisp code... Will be used in hands on...
            System.out.println("Create hands-on");
            System.out.println(rm.createResource("tmp", "hands-on", true));
            rm.createFile("tmp", "sum", "(+ 1 1)");
            clisp_env.run("tmp");
        }

    }
}
