package chess;

public class ChessBoard {

    class Position {
        private int row;
        private int column;

        public Position (int r, int c){
            this.row = r;
            this.column = c;
        }
        public int getRow(){ return this.row;}

        public int getCol(){ return this.column;}

    }
   
    class Square {
        private Color color;
        private Piece piece;

        public Square() {
            this.color = null;
            this.piece = null;
        }
        
        public Square(Color c, Piece p){
            this.color = c;
            this.piece = p;
        }

        public Piece getPiece(){return this.piece;}

        public void setPiece(Piece p){
            this.piece = p;
        }

        public void setColor(Color c){
            this.color = c;
        }
    }

    //This serves as a container, stores the squares
    private Square[][] board;

    public ChessBoard() {
        board = new Square[8][8];
    }

    private void paintBoard(){

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
    private void setPawns(){
        for(int r = 0; r < 8; ++r){
            for(int c = 0; c < 8; ++c){
                if( r == 0 || r == 1){
                    this.board[r][c].setPiece(new Piece(Color.WHITE, Piece.PieceType.PAWN));
                }else if( r == 6 || r == 7){
                    this.board[r][c].setPiece(new Piece(Color.BLACK, Piece.PieceType.PAWN));
                }
            }
        }
    }

    private void setRooks(){

        this.board[0][0].setPiece(new Piece(Color.WHITE, Piece.PieceType.ROOK));
        this.board[0][7].setPiece(new Piece(Color.WHITE, Piece.PieceType.ROOK));
        this.board[7][0].setPiece(new Piece(Color.BLACK, Piece.PieceType.ROOK));
        this.board[7][7].setPiece(new Piece(Color.BLACK, Piece.PieceType.ROOK));

    }

    private void setKnights(){

        this.board[0][1].setPiece(new Piece(Color.WHITE, Piece.PieceType.KNIGHT));
        this.board[0][6].setPiece(new Piece(Color.WHITE, Piece.PieceType.KNIGHT));
        this.board[7][1].setPiece(new Piece(Color.BLACK, Piece.PieceType.KNIGHT));
        this.board[7][6].setPiece(new Piece(Color.BLACK, Piece.PieceType.KNIGHT));

    }

    private void setBishops(){
        
        this.board[0][2].setPiece(new Piece(Color.WHITE, Piece.PieceType.BISHOP));
        this.board[0][5].setPiece(new Piece(Color.WHITE, Piece.PieceType.BISHOP));
        this.board[7][2].setPiece(new Piece(Color.WHITE, Piece.PieceType.BISHOP));
        this.board[7][5].setPiece(new Piece(Color.WHITE, Piece.PieceType.BISHOP));

    }

    private void setQueens(){

        this.board[0][3].setPiece(new Piece(Color.WHITE, Piece.PieceType.QUEEN));
        this.board[7][3].setPiece(new Piece(Color.BLACK, Piece.PieceType.QUEEN));
        

    }

    private void setKings(){

        this.board[0][4].setPiece(new Piece(Color.WHITE, Piece.PieceType.KING));
        this.board[7][4].setPiece(new Piece(Color.BLACK, Piece.PieceType.KING));
    }

    private void setPieces() {
        this.setPawns();
        this.setRooks();
        this.setKnights();
        this.setBishops();
        this.setQueens();
        this.setKings();
    }

    //Filling board with new squares, and pieces
    public void initBoard() {
        this.paintBoard();
        this.setPieces();   
    }

    public Piece getPieceOnPos( Position pos ){
        return this.board[pos.getRow()][pos.getCol()].getPiece();
    }

    public void setPieceOnPos(Piece piece, Position pos){
        this.board[pos.getRow()][pos.getCol()].setPiece(piece);
    }
}
