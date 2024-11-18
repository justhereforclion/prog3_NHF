package main.chess;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements ActionListener {

    private ChessBoard chessBoard;
    private Engine engine;
    private JPanel board;
    private Box sideBar;
    
    public GamePanel(ChessBoard cb, Engine engine){
        chessBoard = cb;
        this.engine = engine;

        setLayout( new BorderLayout(10,0));

        //Board panel contains the buttons(squares)
        board = new JPanel();

        //Sidebar is next to board, contains two buttons
        sideBar = Box.createVerticalBox();

        //Adding buttons to sidebar, changing their sizes for better visibility TODO
        JButton menuButton = new JButton("Menu");
        JButton saveButton = new JButton("Save");
        

        sideBar.add(menuButton);
        sideBar.add(saveButton);
       

        add(sideBar, BorderLayout.EAST);
       
        board = constructBoard();

        add(board, BorderLayout.CENTER);
    }
        
    //Refreshing what is displayed on board
    private JPanel constructBoard(){

        //New panel which will contain all the buttons
        board = new JPanel( new GridLayout(8,8));

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
                square.setActionCommand(new Position(r,c).toString());
                square.addActionListener(this);
                board.add(square);
            }
        }
        return board;
    }

    public void updateBoard(Engine.Move move){
        Component[] squares = this.board.getComponents();
        //The rows were placed backwards into board component the columns are in ascending order
        int sRow = 7 - move.src.getRow();
        int sCol = move.src.getCol();
        int dRow = 7 - move.dest.getRow();
        int dCol = move.dest.getCol();
        JButton src = (JButton)squares[sRow * 8 + sCol];
        JButton dest = (JButton)squares[dRow * 8 + dCol];
        Icon piece = src.getIcon();
        src.setIcon(null);
        dest.setIcon(piece);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        engine.handleInput(Position.posFromString(e.getActionCommand()));
    }
}
