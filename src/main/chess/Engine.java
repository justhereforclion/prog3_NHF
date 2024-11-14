package main.chess;

public class Engine {

    static class Move {
        public Position src;
        public Position dest;
    }

    //Rules will be checked on this board.
    private ChessBoard chessBoard;

    //Methods for checking rules on moves
    private boolean isPawnMovable( Move move ){
        //Which color is the pawn, sets the direction it can move
        Position vector;
        vector = chessBoard.getPieceOnPos(move.src).getColor() == Color.WHITE ? new Position(1,0) : new Position(-1,0);
        
        //Only stepping one square ahead
        if(move.dest == move.src.add(vector)){return true;}
        
        //Taking a piece while moving
        else if(chessBoard.getPieceOnPos(move.dest) != null) {
            if(move.dest == move.src.add(vector.add(new Position(0,1)))){return true;}
            if(move.dest == move.src.add(vector.add(new Position(0,-1)))){return true;}
        }

        //Destination does not fit the scenarios above
        return false;
    }
    //Chosen squares not necessarrily hava pieces on them, need to handle null
    public boolean isValidMove( Move move ){

        //Chosen squares are the same
        if(move.src == move.dest){
            return false;
        }

        //Source square has a piece on it
        if(chessBoard.getPieceOnPos(move.src) != null){
            return false;
        }

        //Two chosen square hava same colored pieces
        if(chessBoard.getPieceOnPos(move.src).getColor() == chessBoard.getPieceOnPos(move.dest).getColor()){
            return false;
        }
        
        return true;
    }

}
