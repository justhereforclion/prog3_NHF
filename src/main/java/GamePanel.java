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

/**
 * Represents the game panel in the chess GUI.
 * This panel displays the chessboard and provides options to navigate and save the game.
 */
public class GamePanel extends JPanel implements ActionListener {

    /**
     * Reference to the chessboard, representing the state of the game.
     */
    private ChessBoard chessBoard;

    /**
     * Reference to the game engine, which handles the game logic.
     */
    private Engine engine;

    /**
     * Listener for move events, applied to each square (button) on the chessboard.
     */
    private MoveListener ml;

    /**
     * Panel containing the chessboard squares.
     */
    private JPanel board;

    /**
     * Sidebar panel for navigation and saving the game.
     */
    private JPanel sideBar;

    /**
     * Constructs a GamePanel instance, initializing the chessboard, engine, and GUI components.
     *
     * @param cb the ChessBoard representing the initial game state.
     * @param engine the Engine managing the game logic.
     * @param nl the NavigationListener for navigation buttons.
     */
    public GamePanel(ChessBoard cb, Engine engine, NavigationListener nl) {
        // Initialize references to the chessboard, engine, and move listener
        chessBoard = cb;
        this.engine = engine;
        this.ml = new MoveListener(engine);

        // Set layout and initialize panels
        this.setLayout(new BorderLayout(10, 0));
        board = new JPanel();
        sideBar = new JPanel(new FlowLayout(FlowLayout.LEFT));

        // Create and configure navigation buttons
        JButton menuButton = new JButton("Menu");
        menuButton.addActionListener(nl);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(this);

        // Add buttons to the sidebar
        sideBar.add(menuButton);
        sideBar.add(saveButton);

        // Add the sidebar to the GamePanel
        add(sideBar, BorderLayout.NORTH);

        // Initialize and paint the board
        this.board = createBoard();
        paintBoard();

        // Add the board to the GamePanel
        add(board, BorderLayout.CENTER);
    }

    /**
     * Updates the chessboard state with a new ChessBoard object.
     *
     * @param cb the new ChessBoard representing the updated state.
     */
    public void setChessBoard(ChessBoard cb) {
        this.chessBoard = cb;
    }

    /**
     * Creates the JPanel representing the chessboard, consisting of an 8x8 grid of buttons.
     *
     * @return a JPanel containing the chessboard squares.
     */
    private JPanel createBoard() {
        JPanel b = new JPanel(new GridLayout(8, 8));

        for (int r = 7; r >= 0; --r) {
            for (int c = 0; c < 8; ++c) {
                JButton square = new JButton();
                square.addActionListener(ml); // Attach move listener
                square.setActionCommand(new Position(r, c).toString()); // Set square's position
                b.add(square);
            }
        }
        return b;
    }

    /**
     * Paints the chessboard, updating each square's state based on the current chessboard.
     * This method sets the background color and piece image for each square.
     */
    public void paintBoard() {
        for (int r = 7; r >= 0; r--) {
            for (int c = 0; c <= 7; c++) {
                JButton square = (JButton) this.board.getComponent(r * 8 + c);

                // Determine if the square has a piece and load its image
                if (chessBoard.isPosOccupied(new Position(7 - r, c))) {
                    String colorOfImg = chessBoard.getPieceOnPos(new Position(7 - r, c)).getColor() == Color.WHITE ? "white" : "black";
                    String typeOfImg;

                    switch (chessBoard.getPieceOnPos(new Position(7 - r, c)).getType()) {
                        case Piece.PieceType.PAWN:
                            typeOfImg = "pawn";
                            break;
                        case Piece.PieceType.ROOK:
                            typeOfImg = "rook";
                            break;
                        case Piece.PieceType.KNIGHT:
                            typeOfImg = "knight";
                            break;
                        case Piece.PieceType.BISHOP:
                            typeOfImg = "bishop";
                            break;
                        case Piece.PieceType.QUEEN:
                            typeOfImg = "queen";
                            break;
                        case Piece.PieceType.KING:
                            typeOfImg = "king";
                            break;
                        default:
                            typeOfImg = "pawn";
                    }
                    String nameOfImg = colorOfImg + "-" + typeOfImg + ".png";
                    String imgPath = "src/main/resources/piece-images/" + nameOfImg;

                    // Set the image icon for the square
                    square.setIcon(new ImageIcon(imgPath));
                } else {
                    // Square has no piece
                    square.setIcon(null);
                }

                // Set the background color of the square to match the chessboard
                square.setBackground(chessBoard.getSquareOnPos(new Position(r, c)).getColor());
            }
        }
    }

    /**
     * Handles button actions for saving the game and navigating between panels.
     *
     * @param event the ActionEvent triggered by a button click.
     */
    public void actionPerformed(ActionEvent event) {
        if (event.getActionCommand().equals("New Game")) {
            this.engine.newGame();
        }
        if (event.getActionCommand().equals("Load Game")) {
            try {
                this.engine.loadGame();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (event.getActionCommand().equals("Save")) {
            try {
                this.engine.saveGame();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
