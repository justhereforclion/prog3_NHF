package main.chess;

public class Engine {

    static class Move {
        public ChessBoard.Position src;
        public ChessBoard.Position dest;
    }

    //Rules will be checked on this board.
    private ChessBoard chessBoard;

    //Methods for checking rules on moves
    private boolean isPawnMovable( Move move ){
        switch(chessBoard.getPieceOnPos(move.src).getColor()){
            //TODO
        }
    }
    //Chosen spuares not necessarrily hava pieces on them, need to handle null
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
