package com.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.BorderLayout;

import com.jade.JadeAgentInterface;

public class SimpleGui extends JFrame
{
    protected JadeAgentInterface agent;
    protected JPanel             panel;

    public SimpleGui(JadeAgentInterface jai) {
        super(jai.getAgentLocalName());
        agent = jai;
        panel = new JPanel();

        addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    agent.onClose();
                }
            });
        getContentPane().add(panel, BorderLayout.CENTER);
        setResizable(false);
    }

    public void showGui() {
        pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int cy = (int)screenSize.getWidth() / 2;
        int cx = (int)screenSize.getHeight() / 2;

        setLocation (cx - getWidth() / 2, cy - getHeight() / 2);
        super.setVisible(true);
    }
}
