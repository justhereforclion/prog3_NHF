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

    //Executing a valid move on chessBoard
    public void executeMove(Move move){
        Piece pieceInHand = chessBoard.getPieceOnPos(move.src);
        chessBoard.setPieceOnPos(pieceInHand, move.dest);
        chessBoard.setPieceOnPos(null,move.src);
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
        
        return true;
    }
    private static boolean isPosOnBoard(Position pos){
        return pos.getRow() < 8 && pos.getRow() >= 0 && pos.getCol() < 8 && pos.getCol() >= 0;
    }

    //Recursively checks whether dest is reachable from src with given limit and direction(vector);
    private boolean isDestReachable(Position src, Position dest, Position vector,int limit){
        //Cant reach this square, didnt find dest on the way here
        if(limit < 0){
            return false;
        }
        //Walked off the table,  didnt find dest on the way here
        else if( ! Engine.isPosOnBoard(dest) ){
            return false;
        }
        //Standing on dest, dest is reachable from original src
        else if (src.equals(dest)){
            return true;
        }
        //Walks into a piece on the way, blocking the path 
        else if(chessBoard.isPosOccupied(src)){
            return false;
        }
        //None of the above, keep searching in the given direction
        else {
            return isDestReachable(src.add(vector), dest, vector, limit-1);
        }
    }
    private boolean isPawnMovable( Move move ){
        //Pawn TODO
        return true
    }

}
