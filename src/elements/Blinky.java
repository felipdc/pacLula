package elements;

import control.Stage;
import static java.lang.Math.abs;
import utils.Consts;

public class Blinky extends Ghost{
    
    private static final int LEFT = 1;
    private static final int RIGHT = 2;
    private static final int UP = 3;
    private static final int DOWN = 4;
    private static final int X = 5;
    private static final int Y = 6;
    
    private Stage stg = new Stage(1);
    
    public Blinky(String imageName) {
        super(imageName, Consts.ID_GHOST1);
    }
    
    public boolean checkIfLoloIsDead(Lolo llolo){
        return llolo.overlap(this);
    }

    public void seekLolo(Lolo llolo){
        
        //Check if blinky got lolo
        if(checkIfLoloIsDead(llolo))
            return;
        
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
        
        double yDist = pos.getX() - llolo.pos.getX();
        double xDist = pos.getY() - llolo.pos.getY();
        int axisPrior = axisPriority(yDist,xDist);
        int xPrior = xPriority(xDist);
        int yPrior = yPriority(yDist);
        int tryNum = 0;
        
        if(axisPrior==X){
            seekX(xPrior,yPrior,tryNum);
        }else{
            seekY(xPrior,yPrior,tryNum);
        }
    }
    
    
    public void seekX(int xPrior, int yPrior, int tryNum){
        //right is x priority and first try to move
        if(tryNum==0&&xPrior==RIGHT){
            if(this.isRightPossible()){
                this.moveRight();
            }else{
                seekY(xPrior, yPrior, 1);
            }
        }
        //left is x priority and first try to move
        if(tryNum==0&&xPrior==LEFT){
            if(this.isLeftPossible()){
                this.moveLeft();
            }else{
                seekY(xPrior, yPrior, 1);
            }
        }
        
        if(tryNum==1&&xPrior==RIGHT){
            if(this.isRightPossible()){
                this.moveRight();
            }else{
                if(this.isLeftPossible()){
                    this.moveLeft();
                }else{
                    seekY(xPrior, yPrior, 2);
                }
            }
        }
        
        if(tryNum==1&&xPrior==LEFT){
            if(this.isLeftPossible()){
                this.moveLeft();
            }else{
                if(this.isRightPossible()){
                    this.moveRight();
                }else{
                    seekY(xPrior, yPrior, 2);
                }
            }
        }
        
        if(tryNum==2){
            if(this.isRightPossible()){
                this.moveRight();
            }else{
                if(this.isLeftPossible()){
                    this.moveLeft();
                }
            }
        }       
    }
    
    public void seekY(int xPrior, int yPrior, int tryNum){
        
        if(tryNum==0&&yPrior==UP){
            if(this.isUpPossible()){
                this.moveUp();
            }else{
                seekX(xPrior, yPrior, 1);
            }
        }
        
        if(tryNum==0&&yPrior==DOWN){
            if(this.isDownPossible()){
                this.moveDown();
            }else{
                seekX(xPrior, yPrior, 1);
            }
        }
        
        if(tryNum==1&&yPrior==UP){
            if(this.isUpPossible()){
                this.moveUp();
            }else{
                if(this.isDownPossible()){
                    this.moveDown();
                }else{
                    seekX(xPrior, yPrior, 2);
                }
            }
        }
        
        if(tryNum==1&&yPrior==DOWN){
            if(this.isDownPossible()){
                this.moveDown();
            }else{
                if(this.isUpPossible()){
                    this.moveUp();
                }else{
                    seekX(xPrior, yPrior, 2);
                }
            }
        }
        
        if(tryNum==2){
            if(this.isDownPossible()){
                this.moveDown();
            }else{
                if(this.isUpPossible()){
                    this.moveUp();
                }
            }
        }   
    }
    
    public int xPriority(double xDist){
        if(xDist<0){
                return RIGHT;               
            }else{
                return LEFT;
            }
    }
    
    public int yPriority(double yDist){
        if(yDist>0){
                return UP;               
            }else{
                return DOWN;
            }
    }
    
    public int axisPriority(double yDist,double xDist){
        if(abs(xDist)>abs(yDist)){
            return X;
        }else{
            return Y;
        }
    }
}