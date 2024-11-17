package main.chess;

import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.Color;

public class GameGUI {
    //Parent frame and all of its panels
    private JFrame frame;
    private JPanel menu;
    private JPanel game;
    private JPanel rules;

    //The frame visulizes the state of this board
    private ChessBoard chessBoard;

    private String[] COLUMN_IDS = {"A","B","C","D","E","F","G","H"};
    private String[] ROW_IDS = {"1","2","3","4","5","6","7","8"};


    public GameGUI(ChessBoard b){

        frame = new JFrame("CHESS");
        chessBoard = b;

        //TODO Initalizing all the panels
        constructGamePanel();
        

        //Adding JComponents to the frame
        frame.add(game,BorderLayout.CENTER);
        

        //Adjusting the main frame
        frame.setResizable(true);
        frame.setMinimumSize( new Dimension(800,700));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private void constructGamePanel(){
        //The main panel which contains the table and the sidebar
        this.game = new JPanel(new BorderLayout(10,0));

        //Board panel contains the buttons
        JPanel board;
        //Sidebar is next to board, contains two buttons
        Box sideBar = Box.createVerticalBox();

        //Adding buttons to sidebar, changing their sizes for better visibility TODO
        JButton menuButton = new JButton("Menu");
        JButton saveButton = new JButton("Save");
        

        sideBar.add(menuButton);
        sideBar.add(saveButton);
       

        game.add(sideBar, BorderLayout.EAST);
       
        board = this.refreshBoard();

        game.add(board, BorderLayout.CENTER);
    }
        
    //Refreshing what is displayed on board
    private JPanel refreshBoard(){

        //New panel which will contain all the buttons
        JPanel board = new JPanel( new GridLayout(8,8));

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
                    //Initalize a button with image
                    square = new JButton( new ImageIcon(imgPath));
                } else{
                    //Initalize a button without an image
                    square = new JButton();
                }
                //Changes background color on button to match the chessboard colors
                square.setBackground(chessBoard.getSquareOnPos(new Position(r,c)).getColor());
                
                board.add(square);
            }
        }
        return board;
    }
}