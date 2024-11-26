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

/**
 * Manages the graphical user interface (GUI) for the chess game.
 * It includes the main frame and its associated panels: menu, game, and rules.
 */
public class GameGUI {
    /**
     * The main JFrame that acts as the parent container for the GUI.
     */
    private JFrame frame;

    /**
     * The menu panel displayed in the GUI.
     */
    private MenuPanel menu;

    /**
     * The game panel where the chessboard is displayed and interacted with.
     */
    private GamePanel game;

    /**
     * The rules panel that shows the rules of the chess game.
     */
    private RulesPanel rules;

    /**
     * Constructs the game GUI, initializing its components and layout.
     *
     * @param cb the ChessBoard representing the initial state of the game.
     * @param engine the game engine managing game logic.
     */
    public GameGUI(ChessBoard cb, Engine engine) {
        engine.setGameGUI(this); // Allow the engine to communicate with the GUI
        
        frame = new JFrame("CHESS"); // Main window for user interaction
        frame.setLayout(new CardLayout()); // Card layout allows panel navigation

        // Create the navigation listener and associate it with this frame
        NavigationListener nl = new NavigationListener(this.frame);

        // Initialize the panels
        game = new GamePanel(cb, engine, nl);
        menu = new MenuPanel(nl, game);
        rules = new RulesPanel(nl);

        // Add panels to the frame with unique identifiers
        frame.add("MenuPanel", menu);
        frame.add("GamePanel", game);
        frame.add("RulesPanel", rules);

        // Configure the main frame
        frame.setResizable(true); // Allow resizing of the main window
        frame.setMinimumSize(new Dimension(650, 580)); // Minimum size to ensure proper layout
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // Exit application on close
        frame.setVisible(true); // Make the frame visible
    }

    /**
     * Updates the game panel with a new chessboard state.
     *
     * @param cb the ChessBoard representing the new state to display.
     */
    public void updateGamePanel(ChessBoard cb) {
        this.game.setChessBoard(cb); // Update the board in the game panel
        this.game.paintBoard(); // Repaint the game board to reflect changes
    }
}
