package chess;

public class ChessBoard {
   
    class Pozition {
        private int row;
        private int coloumn;

        public Pozition (int r, int c){
            this.row = r;
            this.coloumn = c;
        }

        public int getRow(){ return this.row;}

        public int getColoumn(){ return this.coloumn;}                
    }

    class Square {
        private Color color;
        private Piece piece;
        private Pozition poz;

        public Square() {
            this.color = null;
            this.piece = null;
        }
        
        public Square(Color c, Piece p){
            this.color = c;
            this.piece = p;
        }

        public void setPiece(Piece p){
            this.piece = p;
        }

        public void setColor(Color c){
            this.color = c;
        }
    }

    private Square[][] board;

    public ChessBoard() {
        board = new Square[8][8];
    }

    public void paintBoard(){

        for(int r = 0; r < 8; ++r){
            for(int c = 0; c < 8; ++c){
                if( r % 2 == 1){
                    if( c % 2 == 1){
                        board[r][c].setColor(Color.BLACK);
                    } else {
                        board[r][c].setColor(Color.WHITE);
                    }
                }
            }
        }
    }
    private Square[][] setPawns(){
        for(int r = 0; r < 8; ++r){
            for(int c = 0; c < 8; ++c){
                if( r == 0 || r == 1){
                    this.board[r][c].setPiece(new Piece(Color.WHITE, Piece.PieceType.PAWN));
                }else if( r == 6 || r == 7){
                    this.board[r][c].setPiece(new Piece(Color.BLACK, Piece.PieceType.PAWN));
                }
            }
        }
        return this.board;
    }

    private Square[][] setRooks(){

        this.board[0][0].setPiece(new Piece(Color.WHITE, Piece.PieceType.ROOK));
        this.board[0][7].setPiece(new Piece(Color.WHITE, Piece.PieceType.ROOK));
        this.board[7][0].setPiece(new Piece(Color.BLACK, Piece.PieceType.ROOK));
        this.board[7][7].setPiece(new Piece(Color.BLACK, Piece.PieceType.ROOK));

        return this.board;
    }

    private Square[][] setKnights(){

        this.board[0][1].setPiece(new Piece(Color.WHITE, Piece.PieceType.KNIGHT));
        this.board[0][6].setPiece(new Piece(Color.WHITE, Piece.PieceType.KNIGHT));
        this.board[7][1].setPiece(new Piece(Color.BLACK, Piece.PieceType.KNIGHT));
        this.board[7][6].setPiece(new Piece(Color.BLACK, Piece.PieceType.KNIGHT));

        return this.board;
    }

    private Square[][] setBishops(){
        
        this.board[0][2].setPiece(new Piece(Color.WHITE, Piece.PieceType.BISHOP));
        this.board[0][5].setPiece(new Piece(Color.WHITE, Piece.PieceType.BISHOP));
        this.board[7][2].setPiece(new Piece(Color.WHITE, Piece.PieceType.BISHOP));
        this.board[7][5].setPiece(new Piece(Color.WHITE, Piece.PieceType.BISHOP));

        return this.board;
    }

    private Square[][] setQueens(){

        this.board[0][3].setPiece(new Piece(Color.WHITE, Piece.PieceType.QUEEN));
        this.board[7][3].setPiece(new Piece(Color.BLACK, Piece.PieceType.QUEEN));
        
        return this.board;

    }

    private Square[][] setKings(){

        this.board[0][4].setPiece(new Piece(Color.WHITE, Piece.PieceType.KING));
        this.board[7][4].setPiece(new Piece(Color.BLACK, Piece.PieceType.KING));
        return this.board;
    }

    public void setPieces() {
        board = setPawns();
        board = setRooks();
        board = setKnights();
        board = setBishops();
        board = setQueens();
        board = setKings();
    }

    public void initBoard() {

        //Filling board with new squares, and pieces
        for(int r = 0; r < 8; ++r){
            for(int c = 0; c < 8; ++c){
    }
}
