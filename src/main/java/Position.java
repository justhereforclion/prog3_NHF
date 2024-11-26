import java.io.UnsupportedEncodingException;

/**
 * The Position class represents a specific location on a chessboard using 
 * row and column coordinates. This class provides methods to manipulate and 
 * access the position, as well as methods to convert the position to a string 
 * or create a position from a string representation.
 */
class Position {
    private int row; // The row of the position (0-7)
    private int column; // The column of the position (0-7)

    /**
     * Constructs a Position with the specified row and column.
     * 
     * @param r The row of the position (0-7).
     * @param c The column of the position (0-7).
     */
    public Position(int r, int c){
        this.row = r;
        this.column = c;
    }

    /**
     * Gets the row of the position.
     * 
     * @return The row of the position (0-7).
     */
    public int getRow(){ 
        return this.row;
    }

    /**
     * Gets the column of the position.
     * 
     * @return The column of the position (0-7).
     */
    public int getCol(){ 
        return this.column;
    }

    /**
     * Adds the given vector (another Position object) to the current position 
     * and returns a new Position object with the updated row and column.
     * 
     * @param vector The vector (Position) to add to the current position.
     * @return A new Position object representing the result of the addition.
     */
    public Position add(Position vector){
        return new Position(this.row + vector.row, this.column + vector.column);
    }

    /**
     * Compares the current position to another Position object.
     * 
     * @param pos The position to compare against.
     * @return True if the two positions are equal (same row and column), 
     *         otherwise false.
     */
    public boolean equals(Position pos){
        return this.row == pos.getRow() && this.column == pos.getCol();
    }

    /**
     * Creates and returns a new Position that is a clone of the current position.
     * 
     * @return A new Position object that is a copy of the current position.
     */
    public Position clone(){
        return new Position(this.row, this.column);
    }

    /**
     * Returns a string representation of the position. The column is represented 
     * by a letter (A-H), and the row is represented by a number (1-8).
     * 
     * @return A string representation of the position, e.g., "A1", "D5".
     */
    public String toString(){
        // Getting the ASCII values of row and column which will be converted into String
        char[] chars = new char[2];
        chars[0] = (char)(this.column + 65); // Convert column to letter (A-H)
        chars[1] = (char)(this.row + 49); // Convert row to number (1-8)
        return new String(chars);
    }

    /**
     * Creates a Position object from a string representation. The string must 
     * represent a position using a letter (A-H) for the column and a number (1-8) 
     * for the row (e.g., "A1", "D5").
     * 
     * @param str The string representation of the position (e.g., "A1", "D5").
     * @return A Position object representing the position described by the string.
     */
    public static Position posFromString(String str){
        int column = str.charAt(0) - 65; // Convert column letter (A-H) to integer (0-7)
        int row = str.charAt(1) - 49; // Convert row number (1-8) to integer (0-7)
        
        return new Position(row, column);
    }
}
