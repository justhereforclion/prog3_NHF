import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Color;
import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ChessTest {

    private Position pos;
    private Piece piece;
    private ChessBoard cb;
    private Engine e;
    
    @BeforeEach
    void setUp(){
        pos = new Position(1,1);
        cb = new ChessBoard();
        e = new Engine(cb);
        piece = new Piece(Color.WHITE, Piece.PieceType.PAWN, false);

        //Moving with pawns to make space
        e.executeMove(new Move(new Position(1,0), new Position(3,0)));
        e.executeMove(new Move(new Position(1,1), new Position(3,1)));
        e.executeMove(new Move(new Position(1,2), new Position(3,2)));
        e.executeMove(new Move(new Position(1,3), new Position(3,3)));
    }

    @Test
    void testPieceConstructor(){
        assertEquals(Color.WHITE, piece.getColor());
        assertFalse(piece.hasMoved());
    }

    @Test
    void tesChessBoardConstructor(){
        assertEquals(Piece.PieceType.PAWN, cb.getPieceOnPos(new Position(1,7)).getType());
        assertEquals(Piece.PieceType.KING, cb.getPieceOnPos(new Position(0,4)).getType());
        assertEquals(Piece.PieceType.PAWN, cb.getPieceOnPos(new Position(6,1)).getType());
        assertEquals(Piece.PieceType.KING, cb.getPieceOnPos(new Position(7,4)).getType());
    }

    @Test
    void testPositionConstructor(){
        assertEquals(1, pos.getCol());
        assertEquals(1,pos.getRow());
    }

    @Test
    void testValidMoves(){
        //pawn
        boolean isValid = e.isValidMove(new Move(new Position(3,0), new Position(4,0)));
        assertTrue(isValid);
        //rook
        isValid = e.isValidMove(new Move(new Position(0,0), new Position(2,0)));
        assertTrue(isValid);
        //bishop
        isValid = e.isValidMove(new Move(new Position(0,2), new Position(1,1)));
        assertTrue(isValid);
        //knight
        isValid = e.isValidMove(new Move(new Position(0,1), new Position(2,2)));
        assertTrue(isValid);  
        //queen
        isValid = e.isValidMove(new Move(new Position(0,3), new Position(2,1)));
        assertTrue(isValid);
    }

    @Test
    void testInvalidMoves(){
        //pawn
        boolean isValid = e.isValidMove(new Move(new Position(6,0), new Position(5,0)));
        assertFalse(isValid);
        //knight
        isValid = e.isValidMove(new Move(new Position(7,1), new Position(5,0)));
        assertFalse(isValid);
    }

    @Test 
    void testMovesOnBoard(){
        //pawn and bishop moves
        e.executeMove(new Move(new Position(3,0), new Position(4,0)));
        e.executeMove(new Move(new Position(0,2), new Position(2,4)));

        assertEquals(new Piece(Color.WHITE, Piece.PieceType.PAWN, true), cb.getPieceOnPos(new Position(4,0)));
        assertEquals(new Piece(Color.WHITE, Piece.PieceType.BISHOP, true), cb.getPieceOnPos(new Position(2,4)));
    }

    @Test
    void testFindKing(){
        assertEquals(0, e.findKing().getRow());
        assertEquals(4, e.findKing().getCol());
    }

    @Test 
    void testHandlingInput(){
        e.handleInput(new Position(1,0));
        e.handleInput(new Position(3,0));
        assertEquals(new Piece(Color.WHITE, Piece.PieceType.PAWN, true), cb.getPieceOnPos(new Position(3,0)));
    }

    @Test
    void testLoadMatch(){
        try {
            e.loadGame("src/test/resources/match.txt");
            assertEquals(new Piece(Color.BLACK, Piece.PieceType.QUEEN, true), e.getBoard().getPieceOnPos(new Position(4,3)));
        } catch (IOException e) {
            e.printStackTrace();
        }
       
    }

}
