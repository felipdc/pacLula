package elements;

import control.Stage;
import static java.lang.Math.abs;
import utils.Consts;
/**
 *
 * @author Felipe
 */
public class Blinky extends Ghost{
    
    private Stage stg = new Stage(1);
    
    public Blinky(String imageName) {
        super(imageName, Consts.ID_GHOST1);
    }

    public void seekLolo(Lolo llolo){
        
        double yDist = pos.getX() - llolo.pos.getX();
        double xDist = pos.getY() - llolo.pos.getY();
        
        if(xDist>yDist){
            if("RIGHT".equals(xPriority(xDist))&&this.isRightPossible()){
                seekRight(xDist,yDist);
            }
            if("LEFT".equals(xPriority(xDist))&&this.isLeftPossible()){
                seekLeft(xDist,yDist);
            }
        }else{
            if("UP".equals(yPriority(yDist))){
                seekUp(xDist,yDist);
            }else{
                seekLeft(xDist,yDist);
            }
        }
        
    }
    
    public String xPriority(double xDist){
        if(xDist>0){
                return "RIGHT";               
            }else{
                return "LEFT";
            }
    }
    
    public String yPriority(double yDist){
        if(yDist>0){
                return "UP";               
            }else{
                return "DOWN";
            }
    }

    private void seekRight(double xDist, double yDist){

            this.moveRight();
        
    }
    
    private void seekLeft(double xDist, double yDist){
        
    }
    
    private void seekUp(double xDist, double yDist){
        
    }
    
    private void seekDown(double xDist, double yDist){
        
    }
    
   
    
 
    
}