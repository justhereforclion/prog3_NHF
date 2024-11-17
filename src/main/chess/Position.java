package main.chess;


class Position {
    private int row;
    private int column;

    public Position (int r, int c){
        this.row = r;
        this.column = c;
    }
    public int getRow(){ return this.row;}

    public int getCol(){ return this.column;}

    public Position add(Position vector){
        return new Position(this.row + vector.row, this.column + vector.column);
    }

}
