package main.chess;

import java.awt.Color;

import main.chess.Piece.PieceType;

public class Engine {

    //Nested class for better readability
    static class Move {
        public Position src;
        public Position dest;

        public Move(){
            this.src = null;
            this.dest = null;
        }

        public Move(Position src, Position dest){
            this.src = src;
            this.dest = dest;
        }
    }

    //Shows whether a player has chosen a piece already
    private  boolean hasPieceInHand;

    //Shows which player is next
    private Color nextToMove;

    //Shows current move to be made
    private Move move;

    //Shows current state of the board, and the previous state of the board
    private ChessBoard chessBoard;
    private ChessBoard pastBoard;

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
                updateState();
                executeMove(move);
                gui.updateGame(move,this.chessBoard);
            } else {
                hasPieceInHand = false;
            }
        }
        else{
            move.src = pos;
            hasPieceInHand = chessBoard.isPosOccupied(move.src);
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
        pastBoard = chessBoard.clone();
        hasPieceInHand = false;
        nextToMove = nextToMove == Color.WHITE ? Color.BLACK : Color.WHITE;
    }

    //Methods for checking rules on moves
    public boolean isValidMove( Move move ){

        //Chosen squares are the same or player is not next to move
        if(move.src == move.dest){
            return false;}

        //If two pieces have been chosen, then they can not be the same color
        if(chessBoard.isPosOccupied(move.src) && chessBoard.isPosOccupied(move.dest)){
            if(chessBoard.getPieceOnPos(move.src).getColor() == chessBoard.getPieceOnPos(move.dest).getColor()){
                return false;}}
        
        //Player is not next to move
        if(chessBoard.isPosOccupied(move.src)){
            if( ! chessBoard.getPieceOnPos(move.src).getColor().equals(nextToMove)) return false;
        }

        //Checks rules by piece type
        
        switch(chessBoard.getPieceOnPos(move.src).getType()){
            case PAWN: if(! isValidPawnMove(move)) return false; break;
            case ROOK: if(! isValidRookMove(move,8)) return false; break;
            case KNIGHT: if(! isValidKnightMove(move)) return false; break;
            case BISHOP: if(! isValidBishopMove(move,8)) return false; break;
            case QUEEN: if(! isValidQueenMove(move)) return false; break;
            case KING: if(! isValidKingMove(move)) return false;break;
        }
        

        //Checks if move would cause check
        return ! causesCheck(move);
        
    }

    //Checks if move would threaten the king, takes the move then decides if its valid
    private boolean causesCheck(Move move){
        //Copy state before move and then execute it
        ChessBoard prevBoard = this.chessBoard.clone();
        this.executeMove(move);

        Position pos = findKing();

        if(inThreat(pos)){
            chessBoard = prevBoard;
            return true;}
        else{
            chessBoard = prevBoard;
            return false;}
    }

    //Checks whether a piece on given position is in threat
    private boolean inThreat( Position pos){
        
        for(int r = 0; r < 8; r++){
            for(int c = 0; c < 8; c++){
                Position current = new Position(r,c);
                Move m = new Move(current, pos);
                if(chessBoard.isPosOccupied(current)){
                    if( ! chessBoard.getPieceOnPos(current).getColor().equals(nextToMove)){
                        switch(chessBoard.getPieceOnPos(current).getType()){
                            case PAWN: if( isValidPawnMove(m)) return true; break;
                            case ROOK: if( isValidRookMove(m,8)) return true; break;
                            case KNIGHT: if( isValidKnightMove(m)) return true; break;
                            case BISHOP: if( isValidBishopMove(m,8)) return true; break;
                            case QUEEN: if(isValidQueenMove(m)) return true; break;
                            case KING: if( isValidKingMove(m)) return true;break;
                        }
                    }
                }
            }
        }
        return false;
        
    }
    //Finds the position of the king of current color
    private Position findKing(){
        for(int r = 0; r < 8; r++){
            for(int c = 0; c < 8; c++){
                if(chessBoard.isPosOccupied(new Position(r, c))){
                    if(chessBoard.getPieceOnPos(new Position(r,c)).getType() == PieceType.KING){
                        if(chessBoard.getPieceOnPos(new Position(r,c)).getColor().equals(nextToMove)){
                            return new Position(r,c);
                        }
                    }
                }
            }
        }
        return new Position(0,0);
    } 
    //Checks rules for pawn move
    private boolean isValidPawnMove(Move move){
        Piece pawn = chessBoard.getPieceOnPos(move.src); 
        int direction = pawn.getColor().equals(Color.WHITE) ? 1 : -1;

        if(chessBoard.isPosOccupied(move.dest)){
            if(isDestReachable(move.src, move.src, move.dest, new Position(direction,1), 1)){return true;}
            else return isDestReachable(move.src, move.src, move.dest, new Position(direction,-1), 1);
        }
        else{
            if(isDestReachable(move.src, move.src, move.dest, new Position(direction,0), 1)){return true;}
            else if( ! pawn.hasMoved()){
                return isDestReachable(move.src, move.src, move.dest, new Position(direction,0), 2);
            }
            return false;
        }
    }

    //Checks rules for rook movement
    private boolean isValidRookMove(Move move, int limit){
        
        if(isDestReachable(move.src, move.src, move.dest, new Position(1,0), limit)) return true;
        else if(isDestReachable(move.src, move.src, move.dest, new Position(0,1), limit)) return true;
        else if(isDestReachable(move.src, move.src, move.dest, new Position(-1,0), limit)) return true;
        else return isDestReachable(move.src, move.src, move.dest, new Position(0,-1), limit);

    }

    //Checks rule for knight move
    private boolean isValidKnightMove(Move move){
        if(isDestReachable(move.src, move.src, move.dest, new Position(2,1), 1)) return true;
        else if(isDestReachable(move.src, move.src, move.dest, new Position(2,-1), 1)) return true;
        else if(isDestReachable(move.src, move.src, move.dest, new Position(-2,1), 1)) return true;
        else if(isDestReachable(move.src, move.src, move.dest, new Position(-2,-1), 1))return true;
        else if(isDestReachable(move.src, move.src, move.dest, new Position(1,2), 1)) return true;
        else if(isDestReachable(move.src, move.src, move.dest, new Position(-1,2), 1)) return true;
        else if(isDestReachable(move.src, move.src, move.dest, new Position(1,-2), 1)) return true;
        else return (isDestReachable(move.src, move.src, move.dest, new Position(-1,-2), 1)) ;
    }

    //Checks rule for bishop
    private boolean isValidBishopMove(Move move, int limit){
        if(isDestReachable(move.src, move.src, move.dest, new Position(1,1), limit)) return true;
        else if(isDestReachable(move.src, move.src, move.dest, new Position(-1,1), limit)) return true;
        else if(isDestReachable(move.src, move.src, move.dest, new Position(1,-1), limit)) return true;
        else return isDestReachable(move.src, move.src, move.dest, new Position(-1,-1), limit);
    }

    //Checks rule for queen move
    private boolean isValidQueenMove(Move move){
        if(isValidRookMove(move, 8)) {return true;}
        else {return isValidBishopMove(move, 8);}
    }

    private boolean isValidKingMove(Move move){
        if(isValidRookMove(move, 1)){return true;}
        else return isValidBishopMove(move, 1);
    }
    private static boolean isPosOnBoard(Position pos){
        return pos.getRow() < 8 && pos.getRow() >= 0 && pos.getCol() < 8 && pos.getCol() >= 0;
    }

    //Recursively checks whether dest is reachable from src with given limit and direction(vector)
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
        else if(! origin.equals(current) && chessBoard.isPosOccupied(current)){
            return false;
        }
        //None of the above, keep searching in the given direction
        else {
            return isDestReachable(origin, current.add(vector), dest, vector, limit-1);
        }
    }
    
}

