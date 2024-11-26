import java.awt.Color;

/**
 * The Piece class represents a chess piece on the board. It includes attributes such as
 * the piece's color, type (e.g., pawn, rook, knight, etc.), and whether it has been moved.
 * The class provides methods to manipulate and access these attributes, as well as methods
 * for converting the piece to a string representation and creating a piece from a string.
 */
public class Piece {

    /**
     * Enum representing the different types of chess pieces.
     */
    enum PieceType {
        PAWN, ROOK, KNIGHT, BISHOP, QUEEN, KING
    }
    
    private Color color; // Color of the piece (e.g., white or black)
    private PieceType type; // Type of the piece (e.g., pawn, rook, etc.)
    private boolean hasMoved; // Flag indicating whether the piece has moved

    /**
     * Constructs a Piece with the specified color, type, and moved status.
     * 
     * @param c The color of the piece (either Color.WHITE or Color.BLACK).
     * @param t The type of the piece (e.g., PieceType.PAWN, PieceType.ROOK).
     * @param v The flag indicating if the piece has moved (true if moved, false if not).
     */
    public Piece(Color c, PieceType t, boolean v){
        this.color = c;
        this.type = t;
        this.hasMoved = v;
    }

    /**
     * Creates and returns a new Piece that is a clone of the current piece.
     * 
     * @return A new Piece object that is a copy of the current piece.
     */
    public Piece clone(){
        return new Piece(this.color, this.type, this.hasMoved);
    }

    /**
     * Returns a string representation of the piece. The string is the lowercase 
     * letter corresponding to the piece type, and uppercase if the piece is white.
     * 
     * @return A string representation of the piece, e.g., "p" for a black pawn, 
     *         "P" for a white pawn.
     */
    public String toString(){
        String toString;
        
        switch(this.type){
            case PAWN: toString = "p"; break;
            case ROOK: toString = "r"; break;
            case KNIGHT: toString = "n"; break;
            case BISHOP: toString = "b"; break;
            case QUEEN: toString = "q"; break;
            case KING: toString = "k"; break;
            default: return "x"; // Invalid piece type
        }
        
        // Convert to uppercase if the piece is white
        if(this.color.equals(Color.WHITE)){
            toString = toString.toUpperCase();
        }
        return toString;
    }

    /**
     * Creates a Piece from the given string representation. The string is case-sensitive 
     * and indicates both the type and color of the piece (e.g., "p" for black pawn, 
     * "P" for white pawn).
     * 
     * @param str A string representation of the piece, where lowercase letters represent 
     *            black pieces and uppercase letters represent white pieces.
     * @return A Piece object corresponding to the given string, or null if the string is invalid.
     */
    public static Piece fromString(String str){
        Piece piece = new Piece(null, null, true);

        // Determine the color of the piece based on the case of the string
        if(str.equals(str.toLowerCase())){ 
            piece.color = Color.BLACK;
        } else { 
            piece.color = Color.WHITE;
        }

        // Determine the piece type based on the string
        switch(str.toLowerCase()){
            case "p": piece.type = PieceType.PAWN; break;
            case "r": piece.type = PieceType.ROOK; break;
            case "n": piece.type = PieceType.KNIGHT; break;
            case "b": piece.type = PieceType.BISHOP; break;
            case "q": piece.type = PieceType.QUEEN; break;
            case "k": piece.type = PieceType.KING; break;
            default: piece = null; // Invalid piece type
        }
        return piece;
    } 

    /**
     * Gets the color of the piece.
     * 
     * @return The color of the piece (either Color.WHITE or Color.BLACK).
     */
    public Color getColor(){
        return this.color;
    }

    /**
     * Gets the type of the piece.
     * 
     * @return The type of the piece (e.g., PAWN, ROOK, KNIGHT).
     */
    public PieceType getType(){
        return this.type;
    }

    /**
     * Checks if the piece has moved.
     * 
     * @return True if the piece has moved, otherwise false.
     */
    public boolean hasMoved(){
        return this.hasMoved;
    }

    /**
     * Sets the moved status of the piece.
     * 
     * @param b A boolean indicating whether the piece has moved (true if moved, false otherwise).
     */
    public void setHasMoved(boolean b){
        this.hasMoved = b;
    }
}
