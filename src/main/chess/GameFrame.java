package main.chess;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.GridLayout;

public class GameFrame {

    private JFrame frame;
    private JPanel menu;
    private JPanel game;
    private JPanel rules;

    private String[] COLUMN_IDS = {"A","B","C","D","E","F","G","H"};
    private String[] ROW_IDS = {"1","2","3","4","5","6","7","8"};


    public GameFrame(){

        frame = new JFrame("CHESS");
        game = new JPanel(new GridLayout(8,8));

        for(int r=7; r >= 0; r--){
            for(int c=0; c <= 7; c++){
                game.add(new JButton(ROW_IDS[r].concat(COLUMN_IDS[c])));
            }
        }

        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}