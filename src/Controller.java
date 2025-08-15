import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Controller implements KeyListener {
    
    private Maze maze;

    public Controller(Maze maze){
        this.maze = maze;   
    }

   

    @Override
    public void keyPressed(KeyEvent e) {
        int dx = 0;
        int dy = 0;
        
        switch (e.getKeyCode()) {
            case KeyEvent.VK_RIGHT:
                dx = 1;
                break;
            case KeyEvent.VK_LEFT:
                dx = -1;
                break;
            case KeyEvent.VK_UP:
                dy = -1;
                break;
            case KeyEvent.VK_DOWN:
                dy = 1;
                break;
            default:
                return; 
        }

        if (maze.isValid(dx, dy)) {
            maze.updatePlayerLoc(dx, dy);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }
}
