package main.chess;

import java.awt.Color;

public class Piece {
   
    enum PieceType {
        PAWN, ROOK, KNIGHT, BISHOP, QUEEN, KING
    }
    
    private Color color;
    private PieceType type;
    private boolean hasMoved;

    public Piece(Color c, PieceType t, boolean v){
        this.color = c;
        this.type = t;
        this.hasMoved = v;
    }
    public Piece clone(){
        return new Piece(this.color, this.type, this.hasMoved);
    }

    public String toString(){
        String toString;
        
        switch(this.type){
            case PAWN : toString = new String("p"); break;
            case ROOK : toString = new String("r"); break;
            case KNIGHT : toString = new String("n"); break;
            case BISHOP : toString = new String("b"); break;
            case QUEEN : toString = new String("q"); break;
            case KING : toString = new String("k"); break;
            default : return new String("x");
        }
        if(this.color.equals(Color.WHITE)){toString = toString.toUpperCase();}
        return toString;
    }

    public static Piece fromString(String str){
        Piece piece = new Piece(null,null,true);

        if(str.equals(str.toLowerCase())){ 
            piece.color = Color.BLACK;
        } else { piece.color = Color.WHITE;}

        switch(str.toLowerCase()){
            case "p": piece.type = PieceType.PAWN; break;
            case "r": piece.type = PieceType.ROOK; break;
            case "n": piece.type = PieceType.KNIGHT; break;
            case "b": piece.type = PieceType.BISHOP; break;
            case "q": piece.type = PieceType.QUEEN; break;
            case "k": piece.type = PieceType.KING; break;
            default : piece = null;
        }
        return piece;
    } 
    
    public Color getColor(){return this.color;}

    public PieceType getType(){return this.type;}

    public boolean hasMoved(){return this.hasMoved;}

    public void setHasMoved(boolean b){this.hasMoved = b;}


}
