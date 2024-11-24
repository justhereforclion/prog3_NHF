package main.chess;

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
    private JPanel rules;

    private String[] COLUMN_IDS = {"A","B","C","D","E","F","G","H"};
    private String[] ROW_IDS = {"1","2","3","4","5","6","7","8"};


    public GameGUI(ChessBoard cb, Engine engine){
        engine.setGameGUI(this);
        
        frame = new JFrame("CHESS");
        frame.setLayout( new CardLayout());
        //Creating the actions wich will be add to these components(could use actionListeners as well)
        NavigationListener nl = new NavigationListener(this.frame);

        //TODO Initalizing all the panels
        game = new GamePanel(cb, engine, nl);
        menu = new MenuPanel(nl);

        //Adding Components to the frame
        frame.add("Menu", menu);
        frame.add("New Game",game);
        

        //Adjusting the main frame
        frame.setResizable(true);
        frame.setMinimumSize(new Dimension(650,580));
        
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    
        
    public void updateGame(Engine.Move move, ChessBoard cb){
        this.game.setChessBoard(cb);
        this.game.paintBoard();
        //this.frame.validate();
        //this.frame.pack();
    }
}