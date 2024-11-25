package main.java;

import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JComponent;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPanel extends JPanel {
    
    JButton newGameButton;
    JButton continueButton;
    JButton rulesButton;
    JButton loadButton;

    public MenuPanel( NavigationListener nl, ActionListener gamePanel ){
        
        JPanel menuList = new JPanel();
        menuList.setLayout(new GridLayout(5,1,0,50));
        menuList.setPreferredSize(new Dimension(400,500));

        this.setLayout( new BoxLayout(this, BoxLayout.PAGE_AXIS));

        JLabel title = new JLabel("Chess");
        title.setFont(new Font("bold", Font.BOLD, 40));
        title.setHorizontalAlignment(JTextField.CENTER);

        newGameButton = new JButton("New Game");
        newGameButton.addActionListener(nl); // Responsible for navigation between the panels
        newGameButton.addActionListener(gamePanel);// Responsible to reset game panel if user starts a new game
        newGameButton.setActionCommand("New Game"); //Set action command, for determining which panel to show
        newGameButton.setFont(new Font("bold", Font.BOLD, 25));

        rulesButton = new JButton("Rules");
        rulesButton.addActionListener(nl);// Responsible for navigation between the panels
        rulesButton.setActionCommand("Rules");//Set action command, for determining which panel to show
        rulesButton.setFont(new Font("bold", Font.BOLD, 25));
        
        loadButton = new JButton("Load Game");
        loadButton.addActionListener(nl);// Responsible for navigation between the panels
        loadButton.addActionListener(gamePanel);// Responsible to set game panel for continuing a loaded game
        loadButton.setActionCommand("Load Game");//Set action command, for determining which panel to show
        loadButton.setFont(new Font("bold", Font.BOLD, 25));

        continueButton = new JButton("Continue");
        continueButton.addActionListener(nl);// Responsible for navigation between the panels
        continueButton.setActionCommand("Continue");//Set action command, for determining which panel to show
        continueButton.setFont(new Font("bold", Font.BOLD, 25));
        
        menuList.add(title);
        menuList.add(newGameButton);
        menuList.add(continueButton);
        menuList.add(rulesButton);
        menuList.add(loadButton);
        

        this.setLayout(new FlowLayout(FlowLayout.CENTER));
        this.add(menuList);
    }

    
}
