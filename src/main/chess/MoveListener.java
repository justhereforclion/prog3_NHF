package main.chess;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MoveListener implements ActionListener {
    private Engine engine;

    public MoveListener(Engine e){
        this.engine = e;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        engine.handleInput(Position.posFromString(event.getActionCommand()));
    }
}
