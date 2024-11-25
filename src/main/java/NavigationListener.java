package main.java;

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
        
        String ac = e.getActionCommand();
        String p = "MenuPanel";
        if(ac.equals("New Game")){
            p = "GamePanel";
        }
        if(ac.equals("Load Game")){
            p = "GamePanel";
        }
        if(ac.equals("Continue")){
            p = "GamePanel";
        }
        if(ac.equals("Menu")){
            p = "MenuPanel";
        }if(ac.equals("Rules")){
            p = "RulesPanel";
        }

        l.show(this.frame.getContentPane(), p);
    }
}