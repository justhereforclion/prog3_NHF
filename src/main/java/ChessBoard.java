package main.java;

import java.awt.Color;
import java.io.InputStream;
import java.io.Serializable;

public class ChessBoard {
   
    class Square {
        private Color color;
        private Piece piece;

        public Square() {
            this.color = null;
            this.piece = null;
        }
        
        public Square(Color c, Piece p){
            this.color = c;
            this.piece = p;
        }
        public Square clone(){
            if(this.isOccupied()){
                return new Square(this.color,this.piece.clone());
            }
            else return new Square(this.color, null);
        }

        public String toString(){
            if(isOccupied()){
                return this.piece.toString();
            }
            return new String("x");
        }

        public boolean isOccupied(){
            return this.piece != null;
        }
        public Piece getPiece(){return this.piece;}

        public void setPiece(Piece p){
            this.piece = p;
        }

        public Color getColor(){
            return this.color;
        }

        public void setColor(Color c){
            this.color = c;
        }
    
    }

    //This serves as a container, stores the squares
    private Square[][] board;

    public ChessBoard() {
        board = new Square[8][8];
        this.initBoard();
        this.paintBoard();
        this.setPieces(); 
    }

    public ChessBoard clone() {
        ChessBoard b = new ChessBoard();
        for(int r = 0; r < 8; ++r){
            for(int c = 0; c < 8; ++c){
                b.board[r][c] = this.board[r][c].clone();
            }
        }
        return b;
    }
    //Creates a string which represents each squares on the board
    public String toString(){
        String toString = new String();
        for(int r = 0; r < 8; r++){
            for(int c = 0; c < 8; c++){
                toString = toString.concat(this.board[r][c].toString());
            }
        }
        return toString;
    }
    //Creates ChessBoard from String
    public static ChessBoard fromString(String str){
        ChessBoard cb = new ChessBoard();
        for(int r = 0; r < 8; r++){
            for(int c = 0; c < 8; c++){
                cb.setPieceOnPos(Piece.fromString(new String(str.substring(r*8 + c,r*8+c+1))), new Position(r,c));
            }
        }
        return cb;
    }

    //Return the movement state on every position on board. String which has 64 characters in it.
    public String getMovementStates(){
        String states = new String();
        for(int r = 0; r < 8; r++){
            for(int c = 0; c < 8; c++){
                if(this.getSquareOnPos(new Position(r,c)).isOccupied()){
                    if(this.getPieceOnPos(new Position(r,c)).hasMoved()){
                        states = states.concat("1");
                    }else {states = states.concat("0");}
                }else states = states.concat("0");
            }
        }
        return states;
    }
    //Setting movement status on every piece on chess board, defined by given string
    public void setMovementStates(String str){
        for(int r = 0; r < 8; r++){
            for(int c = 0; c < 8; c++){
                if(this.getSquareOnPos(new Position(r,c)).isOccupied()){
                    this.getPieceOnPos(new Position(r,c)).setHasMoved(str.substring(r*8 + c, r*8 +c +1).equals("1"));
                }
               
            }
        }
    }
    private void paintBoard(){

        for(int r = 0; r < 8; ++r){
            for(int c = 0; c < 8; ++c){
                if( r % 2 == 1){
                    if( c % 2 == 1){
                        board[r][c].setColor(new Color(0.628f, 0.398f,0.183f));
                    } else {
                        board[r][c].setColor(new Color(0.925f, 0.789f,0.628f));
                    }
                }else{
                    if( c % 2 == 1){
                        board[r][c].setColor(new Color(0.925f, 0.789f,0.628f));
                    } else {
                        board[r][c].setColor( new Color(0.628f, 0.398f,0.183f));
                    }
                }
            }
        }
    }
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

    private void setRooks(){

        this.board[0][0].setPiece(new Piece(Color.WHITE, Piece.PieceType.ROOK, false));
        this.board[0][7].setPiece(new Piece(Color.WHITE, Piece.PieceType.ROOK, false));
        this.board[7][0].setPiece(new Piece(Color.BLACK, Piece.PieceType.ROOK, false));
        this.board[7][7].setPiece(new Piece(Color.BLACK, Piece.PieceType.ROOK, false));

    }

    private void setKnights(){

        this.board[0][1].setPiece(new Piece(Color.WHITE, Piece.PieceType.KNIGHT, false));
        this.board[0][6].setPiece(new Piece(Color.WHITE, Piece.PieceType.KNIGHT, false));
        this.board[7][1].setPiece(new Piece(Color.BLACK, Piece.PieceType.KNIGHT, false));
        this.board[7][6].setPiece(new Piece(Color.BLACK, Piece.PieceType.KNIGHT, false));

    }

    private void setBishops(){
        
        this.board[0][2].setPiece(new Piece(Color.WHITE, Piece.PieceType.BISHOP, false));
        this.board[0][5].setPiece(new Piece(Color.WHITE, Piece.PieceType.BISHOP, false));
        this.board[7][2].setPiece(new Piece(Color.BLACK, Piece.PieceType.BISHOP, false));
        this.board[7][5].setPiece(new Piece(Color.BLACK, Piece.PieceType.BISHOP, false));

    }

    private void setQueens(){

        this.board[0][3].setPiece(new Piece(Color.WHITE, Piece.PieceType.QUEEN, false));
        this.board[7][3].setPiece(new Piece(Color.BLACK, Piece.PieceType.QUEEN, false));
        

    }

    private void setKings(){

        this.board[0][4].setPiece(new Piece(Color.WHITE, Piece.PieceType.KING, false));
        this.board[7][4].setPiece(new Piece(Color.BLACK, Piece.PieceType.KING, false));
    }

    private void setPieces() {
        this.setPawns();
        this.setRooks();
        this.setKnights();
        this.setBishops();
        this.setQueens();
        this.setKings();
    }

    //Filling board with new squares
    private void initBoard() {
        for(int r = 0; r < 8; ++r){
            for(int c = 0; c < 8; ++c){
                this.board[r][c] = new Square();
            }
        }  
    }

    public Square getSquareOnPos(Position pos){
        return this.board[pos.getRow()][pos.getCol()];
    }
    public Piece getPieceOnPos( Position pos ){
        return this.board[pos.getRow()][pos.getCol()].getPiece();
    }

    public boolean isPosOccupied(Position pos){
        return this.board[pos.getRow()][pos.getCol()].isOccupied();
    }

    public void setPieceOnPos(Piece piece, Position pos){
        this.board[pos.getRow()][pos.getCol()].setPiece(piece);
    }

}
