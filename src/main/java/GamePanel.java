
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements ActionListener{

    private ChessBoard chessBoard;  //Reference for chess board
    private Engine engine;          //Reference for game engine
    private MoveListener ml;        //Move listener, will be added to every square(button)


    private JPanel board;           //The actual chess board which contains the squares
    private JPanel sideBar;         //Sidebar enableing user to navigate and save the current game
    
    public GamePanel(ChessBoard cb, Engine engine, NavigationListener nl){
        //Reference to chess board and to the game engine and action listener(listens to buttons on board)
        chessBoard = cb;
        this.engine = engine;
        this.ml = new MoveListener(engine);

        //Initalizing panels and setting their layouts
        this.setLayout( new BorderLayout(10,0));
        board = new JPanel();
        sideBar = new JPanel( new FlowLayout(FlowLayout.LEFT));

        //Adding buttons to sidebar, changing their sizes for better visibility, adding Action Listener 
        JButton menuButton = new JButton("Menu");
        menuButton.addActionListener(nl);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(this);

        sideBar.add(menuButton);
        sideBar.add(saveButton);
        //Adding sidebar to Game panel
        add(sideBar, BorderLayout.NORTH);
       
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
                square.addActionListener(ml);
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
                    String imgPath = "src/main/resources/piece-images/" + nameOfImg;
                    
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

    public void actionPerformed(ActionEvent event){
        if(event.getActionCommand().equals("New Game")){
            this.engine.newGame();
        }
        if(event.getActionCommand().equals("Load Game")){
            try {
                this.engine.loadGame();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(event.getActionCommand().equals("Save")){
            try {
                this.engine.saveGame();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
}
