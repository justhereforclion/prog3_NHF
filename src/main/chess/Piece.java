package main.chess;

import java.awt.Color;

public class Piece {
   
    enum PieceType {
        PAWN, ROOK, KNIGHT, BISHOP, QUEEN, KING
    }
    
    private final Color color;
    private final PieceType type;
    private boolean hasMoved;

    public Piece(Color c, PieceType t, boolean v){
        this.color = c;
        this.type = t;
        this.hasMoved = v;
    }
    public Piece clone(){
        return new Piece(this.color, this.type, this.hasMoved);
    }
    public Color getColor(){return this.color;}

    public PieceType getType(){return this.type;}

    public boolean hasMoved(){return this.hasMoved;}

    public void setHasMoved(boolean b){this.hasMoved = b;}


}
