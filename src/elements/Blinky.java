package elements;

import control.Stage;
/**
 *
 * @author Felipe
 */
public class Blinky extends Ghost{
    

    
    public Blinky(String imageName, int ghostType, Lolo llolo) {
        super(imageName, ghostType);
        seekLolo(llolo);
        
    }

    
    
    public void seekLolo(Lolo llolo){
        
        double xDist = pos.getX() - llolo.pos.getX();
        double yDist = pos.getY() - llolo.pos.getY();

        this.moveRight();
        
    }
    
    
}
