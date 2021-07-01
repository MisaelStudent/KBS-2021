package com.handson.four;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import com.gui.SimpleGui;
import com.jade.JadeAgentInterface;
import com.clisp.ResourceManager;


public class FourGui extends SimpleGui
{
    private JButton execute_btn;
    private JButton add_rules;
    private JButton add_facts;
    private JTextField rules_txt;
    private JTextField facts_txt;

    public FourGui(JadeAgentInterface jai) {
        super(jai);
        panel.setLayout(new GridLayout(3, 2));
        execute_btn = new JButton("Execute");
        add_facts = new JButton("Add Facts");
        add_rules = new JButton("Add Rules");
        rules_txt = new JTextField();
        facts_txt = new JTextField();
        panel.add(facts_txt);
        panel.add(add_facts);
        panel.add(rules_txt);
        panel.add(add_rules);
        panel.add(execute_btn);

        add_facts.addActionListener(new ActionListener() {
               public void actionPerformed(ActionEvent event)  {
                   ResourceManager rm = (ResourceManager)agent.getInputObject();
                   String text = facts_txt.getText().trim();
                   if (text.isEmpty()) {
                       System.out.println("empty fact, skipping....");
                       return;
                   }
                   System.out.println(String.format("%s added to buffer facts", text));
                   rm.appendToFile("four", "facts", text+"\n");
                   facts_txt.setText("");
               }
            });

        add_rules.addActionListener(new ActionListener() {
               public void actionPerformed(ActionEvent event)  {
                   ResourceManager rm = (ResourceManager)agent.getInputObject();
                   String text = rules_txt.getText().trim();
                   if (text.isEmpty()) {
                       System.out.println("empty rule, skipping....");
                       return;
                   }
                    System.out.println(String.format("%s added to buffer rules", text));
                   rm.appendToFile("four", "rules", text+"\n");
                   rules_txt.setText("");
               }
            });

        execute_btn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent event) {
                    agent.execute();
                }
            });
    }

}
