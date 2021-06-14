package com.jade;

public interface JadeAgentInterface
{
    public void init(String name);

    public void close();

    public String onInput(Object object);

    public void execute();

    public void onClose();

    public String getAgentLocalName();

    public Object getInputObject();
}
