package main.chess;

//Helper class for GameEngine, groups two positions together
public class Move {
    private Position src;
    private Position dest;

    public Move(){
        this.src = null;
        this.dest = null;
    }

    public Move(Position src, Position dest){
        this.src = src;
        this.dest = dest;
    }

    public Position getSrc(){
        return this.src;
    }
    public Position getDest(){
        return this.dest;
    }

    public void setSrc(Position src){
        this.src = src;
    }
    
    public void setDest(Position dest){
        this.dest = dest;
    }}

