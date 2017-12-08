/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elements;

import control.BackgroundElement;
import control.GameScreen;
import java.util.Random;
import utils.Consts;

/**
 *
 * @author metalgas
 */
public class Pinky extends Ghost{
    
    private static final int LEFT = 0;
    private static final int RIGHT = 1;
    private static final int UP = 2;
    private static final int DOWN = 3;
    private static final int X = 5;
    private static final int Y = 6;
    private transient GameScreen gameScreen;
    private boolean pinkyJump = false;
    private boolean scared = false;
    private boolean alreadyCaught = false;
    private BackgroundElement bg;
    
    
    public Pinky(String imageName, BackgroundElement bg) {
        super(imageName, Consts.ID_GHOST2);
        this.bg = bg;
    }
    
    public void seekLolo(Lolo llolo){
        
        if(llolo.getPelletPowered() && alreadyCaught==false){
            if(!"scaredGhost.png".equals(getGhostImage()))
                paintGhost("scaredGhost.png");
            setJump(true);
        }else if(llolo.getPelletPowered() && alreadyCaught==true){
            scared=false;
            if(!"pinky.png".equals(getGhostImage()))
                paintGhost("pinky.png");
            setJump(false);
        }else{
            scared=false;
            if(!"pinky.png".equals(getGhostImage()))
                paintGhost("pinky.png");
            setJump(false);
            alreadyCaught = false;
        }
        
        if(jumpMove()){
            if(pinkyJump){
                pinkyJump=!pinkyJump;
                return;
            }
            pinkyJump=!pinkyJump;
        }
        
        
        //check if lolo is dead
        if(checkIfLoloIsDead(llolo, bg, scared)==GHOST_DEAD){
            scared=false;
            alreadyCaught=true;
            this.setPosition(10, 8);
            
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
        
        if(llolo.getLastMovDirection()==MOVE_RIGHT||llolo.getLastMovDirection()==MOVE_LEFT){
            parallelToX(llolo.getLastMovDirection());
        }else{
            parallelToY(llolo.getLastMovDirection());
        }
        
        
    }
    
    public void parallelToX(int loloLastDirection){
        
        if(loloLastDirection==MOVE_RIGHT){
            if(isRightPossible()){
                this.moveRight();
            }else{
                randomMove(MOVE_RIGHT);
            }
        }else{
            if(isLeftPossible()){
                this.moveLeft();
            }else{
                randomMove(MOVE_LEFT);
            }
        }

    }
    
    public void parallelToY(int loloLastDirection){
        
        if(loloLastDirection==MOVE_UP){
            if(isUpPossible()){
                this.moveUp();
            }else{
                randomMove(MOVE_UP);
            }
        }else{
            if(isDownPossible()){
                this.moveDown();
            }else{
                randomMove(MOVE_DOWN);
            }
        }
        
    }
    
    public void randomMove(int forbDirection){
        
        Random rn = new Random();
        int randomDirection = rn.nextInt(4);
        
        while(randomDirection==forbDirection){
            randomDirection = rn.nextInt(4);
        }
        
        switch(randomDirection){
            case LEFT:
                if(isLeftPossible()){
                    this.moveLeft();
                }
                break;
            case RIGHT:
                if(isRightPossible()){
                    this.moveRight();
                }
                break;
            case UP:
                if(isUpPossible()){
                    this.moveUp();
                }
                break;
            case DOWN:
                if(isDownPossible()){
                    this.moveDown();
                }
                break;
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
    
}
    

