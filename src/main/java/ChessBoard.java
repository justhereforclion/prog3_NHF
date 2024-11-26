import java.awt.Color;
import java.io.InputStream;
import java.io.Serializable;

public class ChessBoard {
   
    /**
     * Represents a square on the chessboard. Each square has a color and may contain a chess piece.
     */
    class Square {
        private Color color;
        private Piece piece;

        /**
         * Default constructor for a square, initializes with no color and no piece.
         */
        public Square() {
            this.color = null;
            this.piece = null;
        }

        /**
         * Constructor that initializes the square with a specific color and piece.
         * 
         * @param c The color of the square.
         * @param p The piece on the square.
         */
        public Square(Color c, Piece p){
            this.color = c;
            this.piece = p;
        }

        /**
         * Creates a clone of this square. If the square contains a piece, it is also cloned.
         * 
         * @return A new Square object with the same color and piece (if occupied).
         */
        public Square clone(){
            if(this.isOccupied()){
                return new Square(this.color, this.piece.clone());
            }
            else return new Square(this.color, null);
        }

        /**
         * Returns a string representation of the square.
         * If the square is occupied, returns the piece's string representation; otherwise, returns "x".
         * 
         * @return String representation of the square.
         */
        public String toString(){
            if(isOccupied()){
                return this.piece.toString();
            }
            return new String("x");
        }

        /**
         * Checks if the square is occupied by a piece.
         * 
         * @return True if the square is occupied, otherwise false.
         */
        public boolean isOccupied(){
            return this.piece != null;
        }

        /**
         * Gets the piece occupying the square.
         * 
         * @return The piece on the square, or null if unoccupied.
         */
        public Piece getPiece(){
            return this.piece;
        }

        /**
         * Sets a piece on the square.
         * 
         * @param p The piece to place on the square.
         */
        public void setPiece(Piece p){
            this.piece = p;
        }

        /**
         * Gets the color of the square.
         * 
         * @return The color of the square.
         */
        public Color getColor(){
            return this.color;
        }

        /**
         * Sets the color of the square.
         * 
         * @param c The color to assign to the square.
         */
        public void setColor(Color c){
            this.color = c;
        }
    }

    /**
     * A 2D array representing the chessboard, consisting of 64 squares.
     */
    private Square[][] board;

    /**
     * Constructor that initializes the chessboard by setting up the squares, colors, and pieces.
     */
    public ChessBoard() {
        board = new Square[8][8];
        this.initBoard();
        this.paintBoard();
        this.setPieces(); 
    }

    /**
     * Creates a clone of the ChessBoard with the same square colors and piece positions.
     * 
     * @return A new ChessBoard object with the same setup.
     */
    public ChessBoard clone() {
        ChessBoard b = new ChessBoard();
        for(int r = 0; r < 8; ++r){
            for(int c = 0; c < 8; ++c){
                b.board[r][c] = this.board[r][c].clone();
            }
        }
        return b;
    }

    /**
     * Creates a string representation of the chessboard by concatenating each square's string representation.
     * 
     * @return A string representing the entire chessboard.
     */
    public String toString(){
        String toString = new String();
        for(int r = 0; r < 8; r++){
            for(int c = 0; c < 8; c++){
                toString = toString.concat(this.board[r][c].toString());
            }
        }
        return toString;
    }

    /**
     * Creates a ChessBoard object from a string representation.
     * 
     * @param str A string representing the board's state, where each character corresponds to a square.
     * @return A ChessBoard object initialized with the given state.
     */
    public static ChessBoard fromString(String str){
        ChessBoard cb = new ChessBoard();
        for(int r = 0; r < 8; r++){
            for(int c = 0; c < 8; c++){
                cb.setPieceOnPos(Piece.fromString(new String(str.substring(r*8 + c,r*8+c+1))), new Position(r,c));
            }
        }
        return cb;
    }

    /**
     * Returns the movement state of each piece on the board as a string of 64 characters (0 or 1).
     * 
     * @return A string representing the movement state of each piece (1 if moved, 0 if not).
     */
    public String getMovementStates(){
        String states = new String();
        for(int r = 0; r < 8; r++){
            for(int c = 0; c < 8; c++){
                if(this.getSquareOnPos(new Position(r,c)).isOccupied()){
                    if(this.getPieceOnPos(new Position(r,c)).hasMoved()){
                        states = states.concat("1");
                    }else {
                        states = states.concat("0");
                    }
                }else states = states.concat("0");
            }
        }
        return states;
    }

    /**
     * Sets the movement status of each piece on the board, based on the given string.
     * 
     * @param str A string representing the movement states (64 characters, '1' or '0').
     */
    public void setMovementStates(String str){
        for(int r = 0; r < 8; r++){
            for(int c = 0; c < 8; c++){
                if(this.getSquareOnPos(new Position(r,c)).isOccupied()){
                    this.getPieceOnPos(new Position(r,c)).setHasMoved(str.substring(r*8 + c, r*8 +c +1).equals("1"));
                }
            }
        }
    }

    /**
     * Paints the chessboard by assigning colors to each square. Alternates colors between dark and light.
     */
    private void paintBoard(){
        for(int r = 0; r < 8; ++r){
            for(int c = 0; c < 8; ++c){
                if( r % 2 == 1){
                    if( c % 2 == 1){
                        board[r][c].setColor(new Color(0.628f, 0.398f,0.183f)); // Dark brown
                    } else {
                        board[r][c].setColor(new Color(0.925f, 0.789f,0.628f)); // Light brown
                    }
                }else{
                    if( c % 2 == 1){
                        board[r][c].setColor(new Color(0.925f, 0.789f,0.628f)); // Light brown
                    } else {
                        board[r][c].setColor( new Color(0.628f, 0.398f,0.183f)); // Dark brown
                    }
                }
            }
        }
    }

    /**
     * Initializes the chessboard by setting up the pawns on their respective rows.
     */
    private void setPawns(){
        for(int r = 0; r < 8; ++r){
            for(int c = 0; c < 8; ++c){
                if( r == 0 || r == 1){
                    this.board[r][c].setPiece(new Piece(Color.WHITE, Piece.PieceType.PAWN, false));
                }else if( r == 6 || r == 7){
                    this.board[r][c].setPiece(new Piece(Color.BLACK, Piece.PieceType.PAWN, false));
                }
            }
        }
    }

    /**
     * Initializes the chessboard by placing the rooks in their starting positions.
     */
    private void setRooks(){
        this.board[0][0].setPiece(new Piece(Color.WHITE, Piece.PieceType.ROOK, false));
        this.board[0][7].setPiece(new Piece(Color.WHITE, Piece.PieceType.ROOK, false));
        this.board[7][0].setPiece(new Piece(Color.BLACK, Piece.PieceType.ROOK, false));
        this.board[7][7].setPiece(new Piece(Color.BLACK, Piece.PieceType.ROOK, false));
    }

    /**
     * Initializes the chessboard by placing the knights in their starting positions.
     */
    private void setKnights(){
        this.board[0][1].setPiece(new Piece(Color.WHITE, Piece.PieceType.KNIGHT, false));
        this.board[0][6].setPiece(new Piece(Color.WHITE, Piece.PieceType.KNIGHT, false));
        this.board[7][1].setPiece(new Piece(Color.BLACK, Piece.PieceType.KNIGHT, false));
        this.board[7][6].setPiece(new Piece(Color.BLACK, Piece.PieceType.KNIGHT, false));
    }

    /**
     * Initializes the chessboard by placing the bishops in their starting positions.
     */
    private void setBishops(){
        this.board[0][2].setPiece(new Piece(Color.WHITE, Piece.PieceType.BISHOP, false));
        this.board[0][5].setPiece(new Piece(Color.WHITE, Piece.PieceType.BISHOP, false));
        this.board[7][2].setPiece(new Piece(Color.BLACK, Piece.PieceType.BISHOP, false));
        this.board[7][5].setPiece(new Piece(Color.BLACK, Piece.PieceType.BISHOP, false));
    }

    /**
     * Initializes the chessboard by placing the queens in their starting positions.
     */
    private void setQueens(){
        this.board[0][3].setPiece(new Piece(Color.WHITE, Piece.PieceType.QUEEN, false));
        this.board[7][3].setPiece(new Piece(Color.BLACK, Piece.PieceType.QUEEN, false));
    }

    /**
     * Initializes the chessboard by placing the kings in their starting positions.
     */
    private void setKings(){
        this.board[0][4].setPiece(new Piece(Color.WHITE, Piece.PieceType.KING, false));
        this.board[7][4].setPiece(new Piece(Color.BLACK, Piece.PieceType.KING, false));
    }

    /**
     * Sets up all the chess pieces on the board (pawns, rooks, knights, bishops, queens, and kings).
     */
    private void setPieces() {
        this.setPawns();
        this.setRooks();
        this.setKnights();
        this.setBishops();
        this.setQueens();
        this.setKings();
    }

    /**
     * Initializes the chessboard by creating empty squares for each position.
     */
    private void initBoard() {
        for(int r = 0; r < 8; ++r){
            for(int c = 0; c < 8; ++c){
                this.board[r][c] = new Square();
            }
        }  
    }

    /**
     * Retrieves the square at a given position on the board.
     * 
     * @param pos The position of the square to retrieve.
     * @return The square at the specified position.
     */
    public Square getSquareOnPos(Position pos){
        return this.board[pos.getRow()][pos.getCol()];
    }

    /**
     * Retrieves the piece at a given position on the board.
     * 
     * @param pos The position of the piece to retrieve.
     * @return The piece at the specified position.
     */
    public Piece getPieceOnPos(Position pos){
        return this.board[pos.getRow()][pos.getCol()].getPiece();
    }

    /**
     * Checks if the position on the board is occupied by a piece.
     * 
     * @param pos The position to check.
     * @return True if the position is occupied, otherwise false.
     */
    public boolean isPosOccupied(Position pos){
        return this.board[pos.getRow()][pos.getCol()].isOccupied();
    }

    /**
     * Sets a piece at a specific position on the board.
     * 
     * @param piece The piece to set at the specified position.
     * @param pos The position to set the piece at.
     */
    public void setPieceOnPos(Piece piece, Position pos){
        this.board[pos.getRow()][pos.getCol()].setPiece(piece);
    }
}
