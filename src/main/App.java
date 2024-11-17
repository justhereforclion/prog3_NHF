package main;

import main.chess.*;

public class App {
    public static void main(String[] args) throws Exception {
        ChessBoard chessBoard = new ChessBoard();
        GameGUI window = new GameGUI(chessBoard);

    }
}
