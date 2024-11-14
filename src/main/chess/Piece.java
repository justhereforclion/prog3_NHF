package main.chess;

public class Piece {
   
    enum PieceType {
        PAWN, ROOK, KNIGHT, BISHOP, QUEEN, KING
    }
    
    private final Color color;
    private final PieceType type;

    public Piece(Color c, PieceType t){
        this.color = c;
        this.type = t;
    }

    public Color getColor(){return this.color;}

    public PieceType getType(){return this.type;}


}
