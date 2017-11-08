/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elements;

/**
 *
 * @author metalgas
 */
public class Pinky extends Ghost{
    
    private static final int LEFT = 1;
    private static final int RIGHT = 2;
    private static final int UP = 3;
    private static final int DOWN = 4;
    private static final int X = 5;
    private static final int Y = 6;
    
    public Pinky(String imageName, int ghostType) {
        super(imageName, ghostType);
    }
    
    public void seekLolo(Lolo llolo){
        
        //Update large ghost position
        updateSensPosition();
        
        //Check if blinky will stay in current path
        if(keepCurrentPath()){
            switch(getLastMovDirection()){
                case MOVE_LEFT:
                    this.moveLeft();
                    break;
                case MOVE_RIGHT:
                    this.moveRight();
                    break;
                case MOVE_UP:
                    this.moveUp();
                    break;
                case MOVE_DOWN:
                    this.moveDown();
                    break;
            }
            return;
        }
        
        if(llolo.getLastMovDirection()==MOVE_RIGHT||llolo.getLastMovDirection()==MOVE_LEFT){
            parallelToX();
        }else{
            parallelToY();
        }
        
        
    }
    
    public void parallelToX(){
        
    }
    
    public void parallelToY(){
        
    }
    
}
