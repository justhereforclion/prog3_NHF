/**
 * The main class that starts the chess game.
 * It initializes the chess board, the game engine, and the graphical user interface (GUI).
 * This is the entry point of the program.
 */
public class App {

    /**
     * The entry point of the program.
     * This method initializes the chess board, the game engine, and the GUI.
     * 
     * @param args The command line arguments (not used in this case).
     * @throws Exception If any error occurs during the game initialization.
     */
    public static void main(String[] args) throws Exception {
        
        // Initialize the chess board.
        ChessBoard chessBoard = new ChessBoard();

        // Initialize the game engine, which handles the chess logic.
        Engine engine = new Engine(chessBoard);

        // Create the graphical user interface (GUI).
        GameGUI gui = new GameGUI(chessBoard, engine);
        
    }
}
