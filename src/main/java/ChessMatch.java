
import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ChessMatch implements Serializable{
    
    private String winner;
    private String movementStates;
    private LinkedList<ChessBoard> screenShots;
    
    public ChessMatch(){
        winner = null;
        screenShots = new LinkedList<>();
    }

    public void add(ChessBoard cb){
        this.screenShots.add(cb);
    }

    public List<ChessBoard> getScreenShots(){
        return this.screenShots;
    }
    
    public String getMovementStates(){
        return this.movementStates;
    }
    public void write(File file) throws IOException{
        FileWriter writer = new FileWriter(file);
        writer.write("NONE\n");
        writer.write(screenShots.getLast().getMovementStates() + "\n");
        for( ChessBoard b : this.screenShots){
            writer.write(b.toString() + "\n");
        }
        writer.close();
    }

    public void read(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        this.winner = reader.readLine();
        this.movementStates = reader.readLine();
        this.screenShots = new LinkedList<>();
        
        String line = reader.readLine();
        while(line != null){
            this.add(ChessBoard.fromString(line));
            line = reader.readLine();
        }
        reader.close();
    }
}
