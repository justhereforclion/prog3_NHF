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

    public String toString(){
        //Getting the ASCII values of row and column which will be converted into String
        char[] chars = new char[2];
        chars[0] = (char)(this.row + 65);
        chars[1] = (char)(this.column + 49);
        return new String(chars);
    }

    public static Position posFromString(String str){
        byte[] bytes = new byte[2];
        try {
            bytes = str.getBytes("US_ASCII");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        int row = (int)bytes[0];
        int column = (int)bytes[1];
        return new Position(row,column);
    }
}
