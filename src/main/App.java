package main;

import main.chess.*;

public class App {
    public static void main(String[] args) throws Exception {
       
        ChessBoard chessBoard = new ChessBoard();
        Engine engine = new Engine(chessBoard);
        GameGUI gui = new GameGUI(chessBoard, engine);
        
    }
}
