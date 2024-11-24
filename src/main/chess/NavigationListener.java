package main.chess;

import javax.swing.JFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.CardLayout;

public class NavigationListener implements ActionListener {
    
    private JFrame frame;
    
    public NavigationListener(JFrame f){
        this.frame = f;
    }
    public void actionPerformed(ActionEvent e){
        CardLayout l = (CardLayout)this.frame.getContentPane().getLayout();
        l.show(this.frame.getContentPane(), e.getActionCommand());
    }
}