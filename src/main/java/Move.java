/**
 * A helper class for the GameEngine that represents a move on the chessboard.
 * It groups two positions together: a source position (src) and a destination position (dest).
 * This class is used to track and manage a move from one square to another in the chess game.
 */
public class Move {
    
    // The source position of the move.
    private Position src;

    // The destination position of the move.
    private Position dest;

    /**
     * Default constructor that creates an empty move with null positions for both source and destination.
     */
    public Move(){
        this.src = null;
        this.dest = null;
    }

    /**
     * Constructor that creates a move from the specified source and destination positions.
     * 
     * @param src The source position of the move.
     * @param dest The destination position of the move.
     */
    public Move(Position src, Position dest){
        this.src = src;
        this.dest = dest;
    }

    /**
     * Gets the source position of the move.
     * 
     * @return The source position of the move.
     */
    public Position getSrc(){
        return this.src;
    }

    /**
     * Gets the destination position of the move.
     * 
     * @return The destination position of the move.
     */
    public Position getDest(){
        return this.dest;
    }

    /**
     * Sets the source position of the move.
     * 
     * @param src The new source position to be set.
     */
    public void setSrc(Position src){
        this.src = src;
    }
    
    /**
     * Sets the destination position of the move.
     * 
     * @param dest The new destination position to be set.
     */
    public void setDest(Position dest){
        this.dest = dest;
    }
}
