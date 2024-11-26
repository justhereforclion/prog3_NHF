import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JComponent;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A panel that represents the main menu of the chess game. This panel allows the user to
 * start a new game, continue a previously saved game, view the game rules, or load an existing game.
 * It includes buttons for each of these actions, and uses ActionListeners to handle user input.
 * 
 * <p> This panel also manages the navigation between different screens (e.g., starting a new game, viewing the rules). </p>
 */
public class MenuPanel extends JPanel {

    // Buttons for different menu actions
    JButton newGameButton;
    JButton continueButton;
    JButton rulesButton;
    JButton loadButton;

    /**
     * Constructs a new MenuPanel with buttons for starting a new game, continuing a game, viewing the rules,
     * and loading a saved game. Sets up the layout and actions associated with each button.
     * 
     * @param nl The NavigationListener that handles the navigation between different panels.
     * @param gamePanel The ActionListener for controlling the game panel, used for resetting or continuing the game.
     */
    public MenuPanel(NavigationListener nl, ActionListener gamePanel) {

        // Create and configure a JPanel to hold the menu buttons
        JPanel menuList = new JPanel();
        menuList.setLayout(new GridLayout(5, 1, 0, 50)); // Grid layout with 5 rows, 1 column
        menuList.setPreferredSize(new Dimension(400, 500)); // Set the preferred size of the menu panel

        // Set the layout for the MenuPanel (FlowLayout for central alignment)
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        // Title label for the menu
        JLabel title = new JLabel("Chess");
        title.setFont(new Font("bold", Font.BOLD, 40)); // Font settings for title
        title.setHorizontalAlignment(JTextField.CENTER); // Center the title horizontally

        // New Game button: Starts a new game when clicked
        newGameButton = new JButton("New Game");
        newGameButton.addActionListener(nl); // Navigation between panels
        newGameButton.addActionListener(gamePanel); // Reset game panel when a new game is started
        newGameButton.setActionCommand("New Game"); // Command for identifying the button action
        newGameButton.setFont(new Font("bold", Font.BOLD, 25)); // Set font for button

        // Rules button: Displays the game rules when clicked
        rulesButton = new JButton("Rules");
        rulesButton.addActionListener(nl); // Navigation between panels
        rulesButton.setActionCommand("Rules"); // Command for identifying the button action
        rulesButton.setFont(new Font("bold", Font.BOLD, 25)); // Set font for button

        // Load Game button: Loads a previously saved game when clicked
        loadButton = new JButton("Load Game");
        loadButton.addActionListener(nl); // Navigation between panels
        loadButton.addActionListener(gamePanel); // Set game panel for continuing the loaded game
        loadButton.setActionCommand("Load Game"); // Command for identifying the button action
        loadButton.setFont(new Font("bold", Font.BOLD, 25)); // Set font for button

        // Continue button: Continues the current game if one is ongoing
        continueButton = new JButton("Continue");
        continueButton.addActionListener(nl); // Navigation between panels
        continueButton.setActionCommand("Continue"); // Command for identifying the button action
        continueButton.setFont(new Font("bold", Font.BOLD, 25)); // Set font for button

        // Add the components to the menu panel
        menuList.add(title); // Add the title label
        menuList.add(newGameButton); // Add the New Game button
        menuList.add(continueButton); // Add the Continue button
        menuList.add(rulesButton); // Add the Rules button
        menuList.add(loadButton); // Add the Load Game button

        // Set the final layout and add the menu panel to the main MenuPanel
        this.setLayout(new FlowLayout(FlowLayout.CENTER));
        this.add(menuList);
    }
}
