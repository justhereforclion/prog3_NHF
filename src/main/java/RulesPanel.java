
import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class RulesPanel extends JPanel {
    
    public RulesPanel(ActionListener nl){
        setLayout(new BorderLayout());

        
        JTextArea textArea = new JTextArea(
            "The Pawn\r\n" +
            "When a game begins, each side starts with eight pawns. \nWhite's pawns are located on the second rank, \nwhile Black's pawns are located on the seventh rank.\n" +
            "The pawn is the least powerful piece and is worth one point.\n If it is a pawn's first move, it can move forward one or two squares.\n If a pawn has already moved, then it can move forward just one square at a time. \nIt attacks (or captures) each square diagonally to the left or right.\n" +
            "The Bishop\r\n" +
            "Each side starts with two bishops, one on a light square and one on a dark square.\n  When a game begins, White's bishops are located on c1 and f1,\n while Black's bishops are located on c8 and f8.\n" +
            "The Knight\r\n" +
            "The knight is considered a minor piece (like a bishop) and is worth three points.\n The knight is the only piece in chess that can jump over another\n piece! It moves one square left or right horizontally\n and then two squares up or down vertically, OR it moves two squares left or right horizontally\n and then one square up or down vertically—in other words, the knight moves in\n an \"L-shape.\" The knight can capture only what\n it lands on, not what it jumps over!\n" +          
            "The Rook\r\n" +
            "The rook is considered a major piece (like the queen) and is worth five points.\n It can move as many squares as it likes left or right horizontally, or as many squares\n as it likes up or down vertically (as long as it isn't blocked by other pieces). \nAn easy way to remember how a rook can move is that it moves like a \"+\" sign.\n" +
            "The Queen\r\n" +
            "The queen is the most powerful chess piece! When a game begins,\n each side starts with one queen. The queen is considered a \n major piece (like a rook) and is worth nine points. It can move as many squares\n as it likes left or right horizontally, or as many squares as\n it likes up or down vertically (like a rook). The queen can also move as many squares\n as it likes diagonally (like a bishop). An easy way to remember how a\n queen can move is that it moves like a rook and a bishop combined!\n" +
            "The King\r\n" + 
            "The king is the most important chess piece. \nRemember, the goal of a game of chess is to checkmate the king!\n When a game starts, each side has one king. The king is not a very powerful piece,\n as it can only move (or capture) one square in any direction. \nPlease note that the king cannot be captured! \nWhen a king is attacked, it is called \"check.\"\n"
            );

        

        JButton menuButton = new JButton("Back To Menu");
        menuButton.addActionListener(nl);

        this.add(menuButton, BorderLayout.NORTH);
        this.add(textArea, BorderLayout.CENTER);
    }

}
