package main.chess;

import java.awt.Color;

public class Engine {

    //Nested class for better readability
    static class Move {
        public Position src;
        public Position dest;
    }

    //Shows whether a player has chosen a piece already
    private static boolean hasPieceInHand;

    //Shows which player is next
    private static Color nextToMove;

    //Shows current move to be made
    private static Move move;

    //Shows current state of the game.
    private ChessBoard chessBoard;

    //GUI which mirrors the state of chessBoard
    private GameGUI gui;

    //Constructor
    public Engine(ChessBoard cb){
        this.hasPieceInHand = false;
        this.nextToMove = Color.WHITE;
        this.move = new Move();
        this.chessBoard = cb;
        this.gui = null;
    }

    public void setChessBoard(ChessBoard cb){this.chessBoard = cb;}

    public void setGameGUI(GameGUI gui){this.gui = gui;}

    //Handling input from GameGUI, ActionListener calls this
    public void handleInput(Position pos){
        
        if(hasPieceInHand){
            //Player already has a piece in hand, pos is the desired destination
            move.dest = pos;
            if(isValidMove(move)){
                executeMove(move);
                updateState();
            } else {
                hasPieceInHand = false;
            }
        }
        else{
            move.src = pos;
            hasPieceInHand = chessBoard.getSquareOnPos(move.src).isSquareOccupied();
        }

    }

    //Executing a valid move on chessBoard, changing hasMoved attribute of piece 
    public void executeMove(Move move){
        Piece pieceInHand = chessBoard.getPieceOnPos(move.src);
        chessBoard.setPieceOnPos(pieceInHand, move.dest);
        chessBoard.setPieceOnPos(null,move.src);
        chessBoard.getPieceOnPos(move.dest).setHasMoved(true);
    }

    //Updates static attributes and GUI after each valid move
    public void updateState(){
        gui.updateGame(move);
        hasPieceInHand = false;
        nextToMove = nextToMove == Color.WHITE ? Color.BLACK : Color.WHITE;
    }

    //Methods for checking rules on moves
    public boolean isValidMove( Move move ){

        //Chosen squares are the same
        if(move.src == move.dest){
            return false;
        }

        //If two pieces have been chosen, then they can not be the same color
        if(chessBoard.getSquareOnPos(move.src).isSquareOccupied() && chessBoard.getSquareOnPos(move.dest).isSquareOccupied()){
            if(chessBoard.getPieceOnPos(move.src).getColor() == chessBoard.getPieceOnPos(move.dest).getColor()){
                return false;
            }
        }

        //Checks rules by piece type TODO
        boolean isValidMove = false;
        switch(chessBoard.getPieceOnPos(move.src).getType()){
            case PAWN: isValidMove = isValidPawnMove(move);
            //case ROOK: isValidMove = isValidRookMove(move);
            //case KNIGHT: isValidMove = isValidKnightMove(move);
           // case BISHOP: isValidMove = isValidBishopMove(move);
           // case QUEEN: isValidMove = isValidQueenMove(move);
            //case KING: isValidMove = isValidKingMove(move);
        }
        return isValidMove;
        
    }
    private boolean isValidPawnMove(Move move){
        Piece pawn = chessBoard.getPieceOnPos(move.src); 
        int direction = pawn.getColor().equals(Color.WHITE) ? 1 : -1;

        if(move.dest.equals(move.src.add(new Position(2 * direction, 0)))){
            //Must be its first move if it moves two rows at a time, and cannot take a piece
            return ! pawn.hasMoved() &&  ! chessBoard.getSquareOnPos(move.dest).isSquareOccupied();
        }
        else if(move.dest.equals(move.src.add(new Position(1 * direction, 0)))){
            //Destination must be empty if it moves one row ahead
            return ! chessBoard.getSquareOnPos(move.dest).isSquareOccupied();
        }
        else if(move.dest.equals(move.src.add(new Position(1 * direction, -1)))){
            //Must take a piece if it moves diagonally
            return chessBoard.getSquareOnPos(move.dest).isSquareOccupied();
        }
        else if(move.dest.equals(move.src.add(new Position(1 * direction, 1)))){
            return chessBoard.getSquareOnPos(move.dest).isSquareOccupied();
        }
        //Destination does not match the listed scenarios above, move is illegal
        return false;
    }
    private static boolean isPosOnBoard(Position pos){
        return pos.getRow() < 8 && pos.getRow() >= 0 && pos.getCol() < 8 && pos.getCol() >= 0;
    }

    //Recursively checks whether dest is reachable from src with given limit and direction(vector);
    private boolean isDestReachable(Position origin, Position current, Position dest, Position vector,int limit){
        //Didnt find dest on the way here
        if(limit < 0){
            return false;
        }
        //Walked off the table,  didnt find dest on the way here
        else if( ! Engine.isPosOnBoard(current) ){
            return false;
        }
        //Standing on dest, dest is reachable from original src
        else if (current.equals(dest)){
            return true;
        }
        //Walks into a piece on the way, blocking the path 
        else if(! origin.equals(current)){
            return chessBoard.isPosOccupied(current);
        }
        //None of the above, keep searching in the given direction
        else {
            return isDestReachable(origin, current.add(vector), dest, vector, limit-1);
        }
    }
    private boolean isPawnMovable( Move move ){
        //Pawn TODO
        return false;

}
}
