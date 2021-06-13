package com.jade;

import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;


public class JadeAgent extends Agent implements JadeAgentInterface
{
    @Override
    public void init(String service_name) {
        DFAgentDescription dfd = new DFAgentDescription();
        ServiceDescription service = new ServiceDescription();
        dfd.setName(getAID());
        service.setType(service_name);
        service.setName("JADE-"+service_name);

        dfd.addServices(service);
        try {
            DFService.register(this, dfd);
        } catch (FIPAException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        try {
            DFService.deregister(this);
        } catch (FIPAException e) {
            e.printStackTrace();
        }
    }


    @Override
    public String onInput(Object object) {
        return null;
    }

    @Override
    public void execute() {

    }

    @Override
    public void onClose() {
        this.doDelete();
    }

    @Override
    public String getAgentLocalName() {
        return getLocalName();
    }
}
