import javax.swing.JFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.CardLayout;

/**
 * The NavigationListener class listens for user actions such as button clicks 
 * in the menu and navigates between different panels in the application 
 * by updating the CardLayout of the JFrame.
 */
public class NavigationListener implements ActionListener {
    
    private JFrame frame;
    
    /**
     * Constructs a NavigationListener with the specified JFrame.
     * This listener will handle navigation actions between panels in the JFrame.
     * 
     * @param f The JFrame where the panels will be displayed.
     */
    public NavigationListener(JFrame f){
        this.frame = f;
    }

    /**
     * Handles action events triggered by user interactions. Based on the action command,
     * this method updates the CardLayout to display the appropriate panel.
     * 
     * @param e The action event that triggered the navigation.
     */
    public void actionPerformed(ActionEvent e){
        CardLayout l = (CardLayout)this.frame.getContentPane().getLayout(); // Get CardLayout from JFrame

        String ac = e.getActionCommand(); // Get the action command (button label)
        String p = "MenuPanel"; // Default panel is MenuPanel

        // Determine which panel to show based on the action command
        if(ac.equals("New Game")){
            p = "GamePanel";
        }
        if(ac.equals("Load Game")){
            p = "GamePanel";
        }
        if(ac.equals("Continue")){
            p = "GamePanel";
        }
        if(ac.equals("Menu")){
            p = "MenuPanel";
        }
        if(ac.equals("Rules")){
            p = "RulesPanel";
        }

        l.show(this.frame.getContentPane(), p); // Show the selected panel
    }
}
