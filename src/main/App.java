package main;
import main.chess.*;

public class App {
    public static void main(String[] args) throws Exception {
        ChessBoard chessBoard = new ChessBoard();
        GameFrame window = new GameFrame(chessBoard);

    }
}
