/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elements;

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
    private GameScreen gameScreen;
    
    
    public Pinky(String imageName, GameScreen gameScreen) {
        super(imageName, Consts.ID_GHOST2);
        this.gameScreen = gameScreen;
    }
    
    public void seekLolo(Lolo llolo){
        
        //check if lolo is dead
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
    

