import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The MoveListener class is responsible for handling user input for making moves in the chess game.
 * It listens for actions (such as button presses) from the user interface and translates those actions into moves
 * by invoking the `handleInput` method of the `Engine` class.
 * This class implements the `ActionListener` interface, making it suitable for use with Swing components.
 */
public class MoveListener implements ActionListener {

    // The engine that handles the game logic
    private Engine engine;

    /**
     * Constructs a MoveListener for a given engine.
     * 
     * @param e The engine that this listener will interact with to process moves.
     */
    public MoveListener(Engine e){
        this.engine = e;
    }

    /**
     * This method is called when an action event (such as a button click) is triggered.
     * It takes the action command from the event, which represents the position of the move,
     * and passes it to the `handleInput` method of the engine.
     * 
     * @param event The action event that triggered this method call.
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        engine.handleInput(Position.posFromString(event.getActionCommand()));
    }
}
