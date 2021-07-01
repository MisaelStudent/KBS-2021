package com.jade;

import jade.core.behaviours.Behaviour;

import com.jade.JadeAgent;

public class AskBehaviour extends Behaviour
{
    boolean is_done;
    JadeAgent agent;
    BehaviourLambda lambda;

    public AskBehaviour(JadeAgent a, BehaviourLambda lambda) {
        is_done = false;
        agent   = a;
        this.lambda = lambda;
    }

    public void action() {
        is_done = lambda.function(agent);
    }

    public boolean done() {
        return is_done;
    }
}
