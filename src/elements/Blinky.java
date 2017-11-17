package elements;

import control.GameScreen;
import control.Stage;
import static java.lang.Math.abs;
import java.util.Random;
import utils.Consts;

public class Blinky extends Ghost{
    
    private static final int LEFT = 0;
    private static final int RIGHT = 1;
    private static final int UP = 2;
    private static final int DOWN = 3;
    private static final int X = 5;
    private static final int Y = 6;
    
    private Stage stg = new Stage(1);
    private GameScreen gameScreen;
    
    public Blinky(String imageName, GameScreen gameScreen) {
        super(imageName, Consts.ID_GHOST1);
        this.gameScreen = gameScreen;
    }
   

    public void seekLolo(Lolo llolo){
        
        //Check if blinky got lolo
        checkIfLoloIsDead(llolo, gameScreen);
        
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
        
        //check if the movement will be random
        Random rn = new Random();
        int randomMovement= rn.nextInt(11);
        
        if(randomMovement>8){
            randomMove();
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
    
    public void randomMove(){
          
        Random rn = new Random();
        int randomDirection = rn.nextInt(4);

        
        switch(randomDirection){
            case LEFT:
                if(isLeftPossible()){
                    this.moveLeft();
                    break;
                }
                if(isRightPossible()){
                    this.moveRight();
                    break;
                }
                if(isUpPossible()){
                    this.moveUp();
                    break;
                }
                if(isDownPossible()){
                    this.moveDown();
                    break;
                }
                return;
            case RIGHT:
                if(isRightPossible()){
                    this.moveRight();
                    break;
                }
                if(isLeftPossible()){
                    this.moveLeft();
                    break;
                }
                if(isUpPossible()){
                    this.moveUp();
                    break;
                }
                if(isDownPossible()){
                    this.moveDown();
                    break;
                }
                return;
            case UP:
                if(isUpPossible()){
                    this.moveUp();
                    break;
                }
                if(isRightPossible()){
                    this.moveRight();
                    break;
                }
                if(isLeftPossible()){
                    this.moveLeft();
                    break;
                }
                if(isDownPossible()){
                    this.moveDown();
                    break;
                }
                return;
            case DOWN:
                if(isDownPossible()){
                    this.moveDown();
                    break;
                }
                if(isRightPossible()){
                    this.moveRight();
                    break;
                }
                if(isUpPossible()){
                    this.moveUp();
                    break;
                }
                if(isLeftPossible()){
                    this.moveLeft();
                    break;
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