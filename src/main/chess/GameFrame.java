package main.chess;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Color;

public class GameFrame {
    //Parent frame and all of its panels
    private JFrame frame;
    private JPanel menu;
    private JPanel game;
    private JPanel rules;

    //Visulizes the state of this board
    private ChessBoard chessBoard;

    private String[] COLUMN_IDS = {"A","B","C","D","E","F","G","H"};
    private String[] ROW_IDS = {"1","2","3","4","5","6","7","8"};


    public GameFrame(ChessBoard b){

        frame = new JFrame("CHESS");
        chessBoard = b;

        initGame();
        //frame.add(this.initMenu());
        //frame.add(this.initRules());
        //TODO

        //Adding JComponents to the frame
        frame.add(game,BorderLayout.CENTER);
        

        frame.pack();
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private void initGame(){

        this.game = new JPanel(new BorderLayout(5,0));
        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(8,8));
        JPanel sideBar = new JPanel();

        JButton menuButton = new JButton("Menu");
        JButton saveButton = new JButton("Save");

        sideBar.add(menuButton);
        sideBar.add(saveButton);
        game.add(sideBar, BorderLayout.EAST);
       


        for(int r=7; r >= 0; r--){
            for(int c=0; c <= 7; c++){
                //A button will represent a square on table
                JButton square;

                //Figuring out which image to load on given square
                if(chessBoard.isPosOccupied(new Position(r,c)) == true){
                    String colorOfImg = chessBoard.getPieceOnPos(new Position(r,c)).getColor() == Color.WHITE ? "white" : "black";
                    String typeOfImg;
                    switch(chessBoard.getPieceOnPos(new Position(r,c)).getType()){
                        case Piece.PieceType.PAWN : typeOfImg = "pawn";
                        break;
                        case Piece.PieceType.ROOK : typeOfImg = "rook";
                        break;
                        case Piece.PieceType.KNIGHT : typeOfImg = "knight";
                        break;
                        case Piece.PieceType.BISHOP : typeOfImg = "bishop";
                        break;
                        case Piece.PieceType.QUEEN : typeOfImg = "queen";
                        break;
                        case Piece.PieceType.KING : typeOfImg = "king";
                        break;
                        default: typeOfImg = "pawn";
                    }
                    String nameOfImg = colorOfImg + "-" + typeOfImg + ".png";
                    String imgPath = "src/main/piece-images/" + nameOfImg;

                     square = new JButton( new ImageIcon(imgPath));
                } else{
                     square = new JButton();
                }
                
                square.setBackground(chessBoard.getSquareOnPos(new Position(r,c)).getColor());
                
                boardPanel.add(square);

            }
            game.add(boardPanel, BorderLayout.CENTER);
        }
        

        
    }
}