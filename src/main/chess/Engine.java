package main.chess;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import main.chess.Piece.PieceType;

public class Engine {

    //Shows whether a player has chosen a piece already
    private  boolean hasPieceInHand;

    //Shows which player is next
    private Color nextToMove;

    //Shows current move to be made
    private Move move;

    //Shows current state of the board, and the previous state of the board
    private ChessBoard chessBoard;
    private ChessBoard pastBoard;

    //Chess Match, stores the moves which were played during this game, also the winner if there is one
    private ChessMatch chessMatch;

    //GUI which mirrors the state of chessBoard
    private GameGUI gui;

    //Constructor
    public Engine(ChessBoard cb){
        this.hasPieceInHand = false;
        this.nextToMove = Color.WHITE;
        this.move = new Move();
        this.chessBoard = cb;
        this.chessMatch = new ChessMatch();
        this.gui = null;
    }
    //Set chessBoard attribute
    //public void setChessBoard(ChessBoard cb){this.chessBoard = cb;}

    //Set gui attribute
    public void setGameGUI(GameGUI gui){this.gui = gui;}

    //Set up engine for a new game, chessBoard will be in staring position
    public void newGame(){
        this.hasPieceInHand = false;
        this.nextToMove = Color.WHITE;
        this.move = new Move();
        this.chessBoard = new ChessBoard();
        this.chessMatch = new ChessMatch();
        chessMatch.add(chessBoard);
        this.gui.updateGamePanel(chessBoard);
    }
    //Handling input from GameGUI, ActionListener calls this
    public void handleInput(Position pos){
        
        if(hasPieceInHand){
            //Player already has a piece in hand, pos is the desired destination
            move.setDest(pos);
            if(isValidMove(move)){
                updateState();
                gui.updateGamePanel(this.chessBoard);
            } else {
                hasPieceInHand = false;
            }
        }
        else{
            //Check whether the player has clicked on a piece
            move.setSrc(pos);
            hasPieceInHand = chessBoard.isPosOccupied(move.getSrc());
        }

    }
    //Saving current state of game, and every move of it to given txt file
    public void saveGame() throws IOException{
        String savePath = "src/main/saved-match/non-terminated/match.txt";
        
        chessMatch.write(new File(savePath));
    }

    //Loads saved match from non-terminated matches
    public void loadGame() throws IOException{
        String loadPath = "src/main/saved-match/non-terminated/match.txt";//Fix loadPath for engine to load from

        this.hasPieceInHand = false; //No piece is selected
        chessMatch.read(new File(loadPath));//Fills up the chess match attribute from file
        this.chessBoard = chessMatch.getScreenShots().getLast().clone(); // Select current chess board from chess match
        if(chessMatch.getScreenShots().size() % 2 == 1){ //Desiding which color is next to move based on how many move was played in the match
            nextToMove = Color.WHITE;
        } else {nextToMove = Color.BLACK;}

        gui.updateGamePanel(chessBoard);//Updating game panel to show current board
    }
    //Executing a valid move on chessBoard, changing hasMoved attribute of piece 
    public void executeMove(Move move){
        Piece pieceInHand = chessBoard.getPieceOnPos(move.getSrc());
        chessBoard.setPieceOnPos(pieceInHand, move.getDest());
        chessBoard.setPieceOnPos(null,move.getSrc());
        chessBoard.getPieceOnPos(move.getDest()).setHasMoved(true);
    }

    //Updates static,non static attributes of engine
    public void updateState(){
        pastBoard = chessBoard.clone();
        executeMove(move);
        chessMatch.add(chessBoard.clone());
        hasPieceInHand = false;
        nextToMove = nextToMove == Color.WHITE ? Color.BLACK : Color.WHITE;
    }

    //Methods for checking rules on moves
    public boolean isValidMove( Move move ){

        //Chosen squares are the same or player is not next to move
        if(move.getSrc() == move.getDest()){
            return false;}

        //If two pieces have been chosen, then they can not be the same color
        if(chessBoard.isPosOccupied(move.getSrc()) && chessBoard.isPosOccupied(move.getDest())){
            if(chessBoard.getPieceOnPos(move.getSrc()).getColor() == chessBoard.getPieceOnPos(move.getDest()).getColor()){
                return false;}}
        
        //Player is not next to move
        if(chessBoard.isPosOccupied(move.getSrc())){
            if( ! chessBoard.getPieceOnPos(move.getSrc()).getColor().equals(nextToMove)) return false;
        }

        //Checks rules by piece type
        
        switch(chessBoard.getPieceOnPos(move.getSrc()).getType()){
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
        Piece pawn = chessBoard.getPieceOnPos(move.getSrc()); 
        int direction = pawn.getColor().equals(Color.WHITE) ? 1 : -1;

        if(chessBoard.isPosOccupied(move.getDest())){
            if(isDestReachable(move.getSrc(), move.getSrc(), move.getDest(), new Position(direction,1), 1)){return true;}
            else return isDestReachable(move.getSrc(), move.getSrc(), move.getDest(), new Position(direction,-1), 1);
        }
        else{
            if(isDestReachable(move.getSrc(), move.getSrc(), move.getDest(), new Position(direction,0), 1)){return true;}
            else if( ! pawn.hasMoved()){
                return isDestReachable(move.getSrc(), move.getSrc(), move.getDest(), new Position(direction,0), 2);
            }
            return false;
        }
    }

    //Checks rules for rook movement
    private boolean isValidRookMove(Move move, int limit){
        
        if(isDestReachable(move.getSrc(), move.getSrc(), move.getDest(), new Position(1,0), limit)) return true;
        else if(isDestReachable(move.getSrc(), move.getSrc(), move.getDest(), new Position(0,1), limit)) return true;
        else if(isDestReachable(move.getSrc(), move.getSrc(), move.getDest(), new Position(-1,0), limit)) return true;
        else return isDestReachable(move.getSrc(), move.getSrc(), move.getDest(), new Position(0,-1), limit);

    }

    //Checks rule for knight move
    private boolean isValidKnightMove(Move move){
        if(isDestReachable(move.getSrc(), move.getSrc(), move.getDest(), new Position(2,1), 1)) return true;
        else if(isDestReachable(move.getSrc(), move.getSrc(), move.getDest(), new Position(2,-1), 1)) return true;
        else if(isDestReachable(move.getSrc(), move.getSrc(), move.getDest(), new Position(-2,1), 1)) return true;
        else if(isDestReachable(move.getSrc(), move.getSrc(), move.getDest(), new Position(-2,-1), 1))return true;
        else if(isDestReachable(move.getSrc(), move.getSrc(), move.getDest(), new Position(1,2), 1)) return true;
        else if(isDestReachable(move.getSrc(), move.getSrc(), move.getDest(), new Position(-1,2), 1)) return true;
        else if(isDestReachable(move.getSrc(), move.getSrc(), move.getDest(), new Position(1,-2), 1)) return true;
        else return (isDestReachable(move.getSrc(), move.getSrc(), move.getDest(), new Position(-1,-2), 1)) ;
    }

    //Checks rule for bishop
    private boolean isValidBishopMove(Move move, int limit){
        if(isDestReachable(move.getSrc(), move.getSrc(), move.getDest(), new Position(1,1), limit)) return true;
        else if(isDestReachable(move.getSrc(), move.getSrc(), move.getDest(), new Position(-1,1), limit)) return true;
        else if(isDestReachable(move.getSrc(), move.getSrc(), move.getDest(), new Position(1,-1), limit)) return true;
        else return isDestReachable(move.getSrc(), move.getSrc(), move.getDest(), new Position(-1,-1), limit);
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

