import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Represents a chess match, storing the game's state, including screenshots of the board
 * and movement states. This class can save and load match data from a file.
 */
public class ChessMatch implements Serializable {
    
    /**
     * The winner of the match. Can be null if the game is ongoing or has no winner.
     */
    private String winner;

    /**
     * Stores the movement states of the game, typically in a textual format.
     */
    private String movementStates;

    /**
     * A list of screenshots representing the state of the chessboard at different moments.
     */
    private LinkedList<ChessBoard> screenShots;

    /**
     * Constructs a new ChessMatch instance with no winner and an empty list of screenshots.
     */
    public ChessMatch() {
        winner = null;
        screenShots = new LinkedList<>();
    }

    /**
     * Adds a new screenshot to the match history.
     *
     * @param cb the ChessBoard representing the current state to add.
     */
    public void add(ChessBoard cb) {
        this.screenShots.add(cb);
    }

    /**
     * Retrieves the list of screenshots stored in the match.
     *
     * @return a list of ChessBoard objects representing the screenshots.
     */
    public List<ChessBoard> getScreenShots() {
        return this.screenShots;
    }
    
    /**
     * Retrieves the movement states of the chess match.
     *
     * @return a string representing the movement states.
     */
    public String getMovementStates() {
        return this.movementStates;
    }

    /**
     * Writes the match data, including movement states and screenshots, to a file.
     *
     * @param file the file to which the match data will be written.
     * @throws IOException if an I/O error occurs during writing.
     */
    public void write(File file) throws IOException {
        FileWriter writer = new FileWriter(file);
        writer.write("NONE\n"); // Placeholder for winner information
        writer.write(screenShots.getLast().getMovementStates() + "\n");
        for (ChessBoard b : this.screenShots) {
            writer.write(b.toString() + "\n");
        }
        writer.close();
    }

    /**
     * Reads match data from a file, reconstructing the movement states and screenshots.
     *
     * @param file the file from which the match data will be read.
     * @throws IOException if an I/O error occurs during reading.
     */
    public void read(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        this.winner = reader.readLine(); // Read winner information
        this.movementStates = reader.readLine(); // Read movement states
        this.screenShots = new LinkedList<>();
        
        String line = reader.readLine();
        while (line != null) {
            this.add(ChessBoard.fromString(line)); // Reconstruct ChessBoard from string
            line = reader.readLine();
        }
        reader.close();
    }
}
