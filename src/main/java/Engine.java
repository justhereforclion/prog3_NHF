import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Represents the main engine for the chess game, handling game logic,
 * state transitions, move validation, and communication with the GUI.
 */
public class Engine {

    /** Indicates if a player has already selected a piece to move. */
    private boolean hasPieceInHand;

    /** Indicates which player (color) is next to move. */
    private Color nextToMove;

    /** Represents the current move being made. */
    private Move move;

    /** The current state of the chessboard. */
    private ChessBoard chessBoard;

    /** The previous state of the chessboard, used for undoing moves. */
    private ChessBoard pastBoard;

    /** Stores information about the match, including moves and the winner. */
    private ChessMatch chessMatch;

    /** The GUI interface that mirrors the chessboard's state. */
    private GameGUI gui;

    /**
     * Constructs the engine with an initial chessboard state.
     *
     * @param cb the initial chessboard state
     */
    public Engine(ChessBoard cb) {
        this.hasPieceInHand = false;
        this.nextToMove = Color.WHITE;
        this.move = new Move();
        this.chessBoard = cb;
        this.chessMatch = new ChessMatch();
        this.gui = null;
    }

    /**
     * Sets the GUI for the engine.
     *
     * @param gui the GameGUI instance to associate with the engine
     */
    public void setGameGUI(GameGUI gui) {
        this.gui = gui;
    }

    /**
     * Starts a new game, resetting the chessboard and match state.
     */
    public void newGame() {
        this.hasPieceInHand = false;
        this.nextToMove = Color.WHITE;
        this.move = new Move();
        this.chessBoard = new ChessBoard();
        this.chessMatch = new ChessMatch();
        chessMatch.add(chessBoard);
        this.gui.updateGamePanel(chessBoard);
    }

    /**
     * Handles player input from the GUI, processing a chessboard position.
     *
     * @param pos the position selected by the player
     */
    public void handleInput(Position pos) {
        if (hasPieceInHand) {
            // Player already has a piece in hand; process destination
            move.setDest(pos);
            if (isValidMove(move)) {
                updateState();
                gui.updateGamePanel(this.chessBoard);
            } else {
                hasPieceInHand = false;
            }
        } else {
            // Check if the player clicked on a valid piece
            move.setSrc(pos);
            hasPieceInHand = chessBoard.isPosOccupied(move.getSrc());
        }
    }

    /**
     * Saves the current game state to a predefined file.
     *
     * @throws IOException if an error occurs during file writing
     */
    public void saveGame() throws IOException {
        String savePath = "src/main/resources/saved-match/non-terminated.txt";
        chessMatch.write(new File(savePath));
    }

    /**
     * Loads a saved game from a predefined file.
     *
     * @throws IOException if an error occurs during file reading
     */
    public void loadGame() throws IOException {
        String loadPath = "src/main/resources/saved-match/non-terminated.txt";

        this.hasPieceInHand = false; // Reset piece selection state
        chessMatch.read(new File(loadPath)); // Load match state from file
        this.chessBoard = chessMatch.getScreenShots().getLast().clone(); // Retrieve the last saved chessboard state
        this.chessBoard.setMovementStates(chessMatch.getMovementStates()); // Restore movement state

        // Determine the next player based on the number of moves played
        if (chessMatch.getScreenShots().size() % 2 == 1) {
            nextToMove = Color.WHITE;
        } else {
            nextToMove = Color.BLACK;
        }

        gui.updateGamePanel(chessBoard); // Update GUI to reflect the loaded state
    }

    /**
     * Executes a valid move on the chessboard, updating piece positions.
     *
     * @param move the move to execute
     */
    public void executeMove(Move move) {
        Piece pieceInHand = chessBoard.getPieceOnPos(move.getSrc());
        chessBoard.setPieceOnPos(pieceInHand, move.getDest());
        chessBoard.setPieceOnPos(null, move.getSrc());
        chessBoard.getPieceOnPos(move.getDest()).setHasMoved(true);
    }

    /**
     * Updates the game state after a move, including board snapshots and player turns.
     */
    public void updateState() {
        pastBoard = chessBoard.clone();
        executeMove(move);
        chessMatch.add(chessBoard.clone());
        hasPieceInHand = false;
        nextToMove = nextToMove == Color.WHITE ? Color.BLACK : Color.WHITE;
    }

    /**
     * Validates if a move complies with chess rules.
     *
     * @param move the move to validate
     * @return true if the move is valid, false otherwise
     */
    public boolean isValidMove(Move move) {
        // Validate basic conditions
        if (move.getSrc() == move.getDest()) {
            return false;
        }

        if (chessBoard.isPosOccupied(move.getSrc()) && chessBoard.isPosOccupied(move.getDest())) {
            if (chessBoard.getPieceOnPos(move.getSrc()).getColor() == chessBoard.getPieceOnPos(move.getDest()).getColor()) {
                return false;
            }
        }

        // Ensure the player is moving their own piece
        if (chessBoard.isPosOccupied(move.getSrc())) {
            if (!chessBoard.getPieceOnPos(move.getSrc()).getColor().equals(nextToMove)) {
                return false;
            }
        }

        // Validate move based on piece type
        switch (chessBoard.getPieceOnPos(move.getSrc()).getType()) {
            case PAWN:
                if (!isValidPawnMove(move)) return false;
                break;
            case ROOK:
                if (!isValidRookMove(move, 8)) return false;
                break;
            case KNIGHT:
                if (!isValidKnightMove(move)) return false;
                break;
            case BISHOP:
                if (!isValidBishopMove(move, 8)) return false;
                break;
            case QUEEN:
                if (!isValidQueenMove(move)) return false;
                break;
            case KING:
                if (!isValidKingMove(move)) return false;
                break;
        }

        // Ensure the move does not cause a check
        return !causesCheck(move);
    }

    /**
     * Checks if a move would result in the king being in check.
     * Temporarily executes the move, evaluates the board state, and reverts the move.
     *
     * @param move the move to evaluate
     * @return true if the move causes a check, false otherwise
     */
    private boolean causesCheck(Move move) {
        ChessBoard prevBoard = this.chessBoard.clone();
        this.executeMove(move);

        Position pos = findKing();

        if (inThreat(pos)) {
            chessBoard = prevBoard;
            return true;
        } else {
            chessBoard = prevBoard;
            return false;
        }
    }

    /**
     * Determines if a piece at the given position is under threat by any opposing pieces.
     *
     * @param pos the position of the piece to evaluate
     * @return true if the piece is in threat, false otherwise
     */
    private boolean inThreat(Position pos) {
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Position current = new Position(r, c);
                Move m = new Move(current, pos);
                if (chessBoard.isPosOccupied(current)) {
                    if (!chessBoard.getPieceOnPos(current).getColor().equals(nextToMove)) {
                        switch (chessBoard.getPieceOnPos(current).getType()) {
                            case PAWN:
                                if (isValidPawnMove(m)) return true;
                                break;
                            case ROOK:
                                if (isValidRookMove(m, 8)) return true;
                                break;
                            case KNIGHT:
                                if (isValidKnightMove(m)) return true;
                                break;
                            case BISHOP:
                                if (isValidBishopMove(m, 8)) return true;
                                break;
                            case QUEEN:
                                if (isValidQueenMove(m)) return true;
                                break;
                            case KING:
                                if (isValidKingMove(m)) return true;
                                break;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Finds the position of the king for the current player.
     *
     * @return the position of the king
     */
    private Position findKing() {
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                if (chessBoard.isPosOccupied(new Position(r, c))) {
                    if (chessBoard.getPieceOnPos(new Position(r, c)).getType() == Piece.PieceType.KING) {
                        if (chessBoard.getPieceOnPos(new Position(r, c)).getColor().equals(nextToMove)) {
                            return new Position(r, c);
                        }
                    }
                }
            }
        }
        return new Position(0, 0);
    }

    /**
     * Validates the movement rules for a pawn.
     *
     * @param move the move to evaluate
     * @return true if the move is valid, false otherwise
     */
    private boolean isValidPawnMove(Move move) {
        Piece pawn = chessBoard.getPieceOnPos(move.getSrc());
        int direction = pawn.getColor().equals(Color.WHITE) ? 1 : -1;

        if (chessBoard.isPosOccupied(move.getDest())) {
            if (isDestReachable(move.getSrc(), move.getSrc(), move.getDest(), new Position(direction, 1), 1)) return true;
            else return isDestReachable(move.getSrc(), move.getSrc(), move.getDest(), new Position(direction, -1), 1);
        } else {
            if (isDestReachable(move.getSrc(), move.getSrc(), move.getDest(), new Position(direction, 0), 1)) return true;
            else if (!pawn.hasMoved()) {
                return isDestReachable(move.getSrc(), move.getSrc(), move.getDest(), new Position(direction, 0), 2);
            }
            return false;
        }
    }

    /**
     * Validates the movement rules for a rook.
     *
     * @param move  the move to evaluate
     * @param limit the maximum number of steps the rook can take
     * @return true if the move is valid, false otherwise
     */
    private boolean isValidRookMove(Move move, int limit) {
        if (isDestReachable(move.getSrc(), move.getSrc(), move.getDest(), new Position(1, 0), limit)) return true;
        else if (isDestReachable(move.getSrc(), move.getSrc(), move.getDest(), new Position(0, 1), limit)) return true;
        else if (isDestReachable(move.getSrc(), move.getSrc(), move.getDest(), new Position(-1, 0), limit)) return true;
        else return isDestReachable(move.getSrc(), move.getSrc(), move.getDest(), new Position(0, -1), limit);
    }

    /**
     * Validates the movement rules for a knight.
     *
     * @param move the move to evaluate
     * @return true if the move is valid, false otherwise
     */
    private boolean isValidKnightMove(Move move) {
        if (isDestReachable(move.getSrc(), move.getSrc(), move.getDest(), new Position(2, 1), 1)) return true;
        else if (isDestReachable(move.getSrc(), move.getSrc(), move.getDest(), new Position(2, -1), 1)) return true;
        else if (isDestReachable(move.getSrc(), move.getSrc(), move.getDest(), new Position(-2, 1), 1)) return true;
        else if (isDestReachable(move.getSrc(), move.getSrc(), move.getDest(), new Position(-2, -1), 1)) return true;
        else if (isDestReachable(move.getSrc(), move.getSrc(), move.getDest(), new Position(1, 2), 1)) return true;
        else if (isDestReachable(move.getSrc(), move.getSrc(), move.getDest(), new Position(-1, 2), 1)) return true;
        else if (isDestReachable(move.getSrc(), move.getSrc(), move.getDest(), new Position(1, -2), 1)) return true;
        else return isDestReachable(move.getSrc(), move.getSrc(), move.getDest(), new Position(-1, -2), 1);
    }
        /**
     * Validates the movement rules for a bishop.
     * Bishops move diagonally without jumping over pieces, within the specified limit.
     *
     * @param move  the move to evaluate
     * @param limit the maximum number of steps the bishop can take
     * @return true if the move is valid, false otherwise
     */

    private boolean isValidBishopMove(Move move, int limit) {
        if (isDestReachable(move.getSrc(), move.getSrc(), move.getDest(), new Position(1, 1), limit)) return true;
        else if (isDestReachable(move.getSrc(), move.getSrc(), move.getDest(), new Position(-1, 1), limit)) return true;
        else if (isDestReachable(move.getSrc(), move.getSrc(), move.getDest(), new Position(1, -1), limit)) return true;
        else return isDestReachable(move.getSrc(), move.getSrc(), move.getDest(), new Position(-1, -1), limit);
    }

    /**
     * Validates the movement rules for a king.
     * Kings can move one square in any direction, as long as the move doesn't result in a check.
     *
     * @param move the move to evaluate
     * @return true if the move is valid, false otherwise
     */
    private boolean isValidKingMove(Move move) {
        if (isValidRookMove(move, 1)) return true;
        else return isValidBishopMove(move, 1);
    }

    /**
     * Validates the movement rules for a queen.
     * Queens can move any number of squares along a rank, file, or diagonal, without jumping over pieces.
     *
     * @param move the move to evaluate
     * @return true if the move is valid, false otherwise
     */
    private boolean isValidQueenMove(Move move) {
        if (isValidRookMove(move, 8)) return true;
        else return isValidBishopMove(move, 8);
    }

    /**
     * Checks whether the given position is within the valid bounds of the chessboard.
     * The chessboard is 8x8, so valid row and column values range from 0 to 7.
     *
     * @param pos The position to check. This is a {@link Position} object that contains the row and column values.
     * @return {@code true} if the position is within the valid chessboard area (0 ≤ row < 8, 0 ≤ column < 8),
     *         otherwise {@code false}.
     */
    private static boolean isPosOnBoard(Position pos){
        return pos.getRow() < 8 && pos.getRow() >= 0 && pos.getCol() < 8 && pos.getCol() >= 0;
    }

    /**
     * Checks if a destination is reachable from a given position within constraints.
     *
     * @param origin   the starting position
     * @param current  the current position in recursion
     * @param dest     the destination position
     * @param vector   the direction of movement
     * @param limit    the remaining allowed steps
     * @return true if the destination is reachable, false otherwise
     */
    private boolean isDestReachable(Position origin, Position current, Position dest, Position vector, int limit) {
        if (limit < 0) return false; // Limit exceeded
        else if (!Engine.isPosOnBoard(current)) return false; // Out of bounds
        else if (current.equals(dest)) return true; // Reached destination
        else if (!origin.equals(current) && chessBoard.isPosOccupied(current)) return false; // Path blocked
        else return isDestReachable(origin, current.add(vector), dest, vector, limit - 1);
    }
}