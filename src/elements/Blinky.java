package elements;

import control.BackgroundElement;
import control.GameScreen;
import control.Stage;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import static java.lang.Math.abs;
import java.util.Random;
import javax.swing.ImageIcon;
import utils.Consts;

public class Blinky extends Ghost{
    
    private static final int LEFT = 0;
    private static final int RIGHT = 1;
    private static final int UP = 2;
    private static final int DOWN = 3;
    private static final int X = 5;
    private static final int Y = 6;
    
    private boolean blinkyJump = false;
    private boolean scared = false;
    private boolean alreadyCaught = false;
    
    private Stage stg = new Stage(1);
    private BackgroundElement bg;
    
    public Blinky(String imageName, BackgroundElement bg) {
        super(imageName, Consts.ID_GHOST1);
        this.bg = bg;
    }
    
  
   

    public void seekLolo(Lolo llolo){
        
        //Check if lolo is powered
        
        if(llolo.getPelletPowered() && alreadyCaught==false){
            scared=true;
            if(!"scaredGhost.png".equals(getGhostImage()))
                paintGhost("scaredGhost.png");
            setJump(true);
        }else if(llolo.getPelletPowered() && alreadyCaught==true){
            scared=false;
            if(!"blinky.png".equals(getGhostImage()))
                paintGhost("blinky.png");
            setJump(false);
        }else{
            scared=false;
            if(!"blinky.png".equals(getGhostImage()))
                paintGhost("blinky.png");
            setJump(false);
            alreadyCaught = false;
            resetGhostsDead();
        }
        
        //reduce movement by half when scared
        
        if(jumpMove()){
            if(blinkyJump){
                blinkyJump=!blinkyJump;
                return;
            }
            blinkyJump=!blinkyJump;
        }
             
        //Check if blinky got lolo
        if(checkIfLoloIsDead(llolo, bg, scared)==GHOST_DEAD){
            this.setPosition(10, 8);
            scared = false;
            alreadyCaught = true;
        }
        
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
            if(!isScared()){
                return RIGHT;
            }else{
                return LEFT;
            }
                
        }else{
            if(!isScared()){
                return LEFT;
            }else{
                return RIGHT;
            }
        }
    }
    
    public int yPriority(double yDist){
        if(yDist>0){
            if(!isScared()){
                return UP;
            }else{
                return DOWN;
            }    
              
        }else{
            if(!isScared()){
                return DOWN;
            }else{
                return UP;
            }
        }
    }
    
    public int axisPriority(double yDist,double xDist){
        if(abs(xDist)>abs(yDist)){
            return X;
        }else{
            return Y;
        }
    }
    
    public boolean isScared(){
        return scared;
    }
    
    public void setScaredOn(){
        scared=true;
    }
    
    public void setScaredOff(){
        scared=false;
    }

}