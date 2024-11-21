package main.chess;

import java.io.UnsupportedEncodingException;

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
    
    public boolean equals(Position pos){
        if(this.row == pos.getRow()){
            return this.column == pos.getCol();
        }
        return false;
    }

    public String toString(){
        //Getting the ASCII values of row and column which will be converted into String
        char[] chars = new char[2];
        chars[0] = (char)(this.column + 65);
        chars[1] = (char)(this.row + 49);
        return new String(chars);
    }

    public static Position posFromString(String str){
        int column = str.charAt(0) - 65;
        int row = str.charAt(1) - 49;
        
        return new Position(row,column);
    }
}
