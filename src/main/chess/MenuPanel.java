package main.chess;

import javax.swing.JPanel;

import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JComponent;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPanel extends JPanel {
    
    JButton newGameButton;
    JButton rulesButton;
    JButton loadButton;

    public MenuPanel( NavigationListener nl ){
        
        JPanel menuList = new JPanel();
        menuList.setLayout(new GridLayout(3,1,0,50));
        menuList.setPreferredSize(new Dimension(400,300));

        this.setLayout( new BoxLayout(this, BoxLayout.PAGE_AXIS));

        newGameButton = new JButton("New Game");
        newGameButton.addActionListener(nl);
        newGameButton.setActionCommand("New Game");

        rulesButton = new JButton("Rules of Chess");
        rulesButton.addActionListener(nl);
        rulesButton.setActionCommand("Rules of Chess");
        
        loadButton = new JButton("Load Game");
        loadButton.addActionListener(nl);
        loadButton.setActionCommand("Load Game");
        
        menuList.add(newGameButton);
        menuList.add(rulesButton);
        menuList.add(loadButton);

        this.setLayout(new FlowLayout(FlowLayout.CENTER));
        this.add(menuList);
    }

    
}
