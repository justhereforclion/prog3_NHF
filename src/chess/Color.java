package chess;

public enum Color {
        BLACK(0,0,0), WHITE(255,255,255), GREEN(0,255,0);

        private int r;
        private int g;
        private int b; 

        private Color(int r, int g, int b) {
            this.r = r;
            this.g = g;
            this.b = b;
        }
}
