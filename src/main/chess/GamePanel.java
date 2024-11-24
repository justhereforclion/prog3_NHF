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
        //Reference to chess board and to the game engine
        chessBoard = cb;
        this.engine = engine;

        //GamePanel has borderlayout 
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
        //Adding sidebar to Game panel
        add(sideBar, BorderLayout.EAST);
       
        //Initalize board panel
        this.board = createBoard();
        paintBoard();

        //Add board panel to Game panel
        add(board, BorderLayout.CENTER);
    }

    public void setChessBoard(ChessBoard cb){
        this.chessBoard = cb;
    }
    private JPanel createBoard(){
        JPanel b = new JPanel(new GridLayout(8,8));

        for(int r=7; r >= 0; --r){
            for(int c=0; c < 8; ++c){
                JButton square = new JButton();
                square.addActionListener(this);
                square.setActionCommand(new Position(r,c).toString());
                b.add(square);
            }
        }
        return b;
    }
    //Painting board panel, displayes the state of chess board
    public void paintBoard(){
        for(int r=7; r >= 0; r--){
            for(int c=0; c <= 7; c++){

                JButton square = (JButton)this.board.getComponent( r*8 + c);

                //Figuring out which image to load on given square
                if( chessBoard.isPosOccupied(new Position(7-r,c)) ){
                    String colorOfImg = chessBoard.getPieceOnPos(new Position(7-r,c)).getColor() == Color.WHITE ? "white" : "black";
                    String typeOfImg;

                    switch(chessBoard.getPieceOnPos(new Position(7-r,c)).getType()){
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
                    
                    //Set the image icon of the square
                    square.setIcon( new ImageIcon(imgPath));
                } else{
                    //Square has no piece on it
                    square.setIcon(null);
                }
                //Changes background color on button to match the chessboard colors
                square.setBackground(chessBoard.getSquareOnPos(new Position(r,c)).getColor());
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        engine.handleInput(Position.posFromString(e.getActionCommand()));
    }
}
