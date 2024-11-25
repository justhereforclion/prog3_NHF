package main.java;

import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.JFrame;
import java.awt.GridLayout;
import java.awt.CardLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.Color;

public class GameGUI {
    //Parent frame and all of its panels
    private JFrame frame;
    private MenuPanel menu;
    private GamePanel game;
    private RulesPanel rules;


    public GameGUI(ChessBoard cb, Engine engine){
        
        engine.setGameGUI(this);//Setting itself for engine, to communicate
        
        frame = new JFrame("CHESS"); //Main window/frame user will interract with this
        frame.setLayout( new CardLayout()); //Frame has card layout, navigation listener chooses which panel to show

        NavigationListener nl = new NavigationListener(this.frame);//Creating the action listener wich will be added to these components
        
        //TODO Initalizing all the panels
        game = new GamePanel(cb, engine, nl);
        menu = new MenuPanel(nl, game);
        rules = new RulesPanel(nl);

        //TODO Adding Components to the frame 
        frame.add("MenuPanel", menu);
        frame.add("GamePanel", game);
        frame.add("RulesPanel", rules);

        //Adjusting the main frame
        frame.setResizable(true);//Main frame is resizable
        frame.setMinimumSize(new Dimension(650,580));//Minimum size guarantees that piece images will be displayed correctly
        
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);//Close application if window closes
        frame.setVisible(true);//Set frame visible
    }

    
        
    public void updateGamePanel(ChessBoard cb){
        this.game.setChessBoard(cb);
        this.game.paintBoard();
        
    }
}