package com.handson.five;

import java.awt.GridLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import com.gui.SimpleGui;
import com.jade.JadeAgentInterface;

public class FiveGui extends SimpleGui
{
    private JButton execute_btn;
    public FiveGui(JadeAgentInterface jai) {
        super(jai);
        panel.setLayout(new GridLayout(1, 1));
        execute_btn = new JButton("Execute");
        panel.add(execute_btn);

        execute_btn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    agent.execute();
                }
            });
    }
}
