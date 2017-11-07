package elements;

import control.Stage;
import java.awt.Graphics;
import utils.Drawing;

public class Ghost extends Element{
    
    
    private int movDirection = STOP;
    private Stage stg = new Stage(1);
    private int sensXPosition;
    private int sensYPosition;
    
    public Ghost(String imageName, int ghostType) {
        super(imageName);
        this.isMortal = true;
        this.isTransposable = false;
        elementId = ghostType;
    }

    @Override
    public void autoDraw(Graphics g) {
       Drawing.draw(g, this.imageIcon, pos.getY(), pos.getX());
    }
    
    public void backToLastPosition(){
        this.pos.comeBack();
    }
    
    public void setMovDirection(int direction){
        movDirection = direction;
    }
    
    public void updateSensPosition(){
        sensXPosition = (int)(this.pos.getX()*10);
        sensYPosition = (int)(this.pos.getY()*10);
    }
    
    protected boolean isRightPossible(){
        
        if((sensXPosition%10)!=0)
            return false;
        return stg.wallCords[sensXPosition/10][(sensYPosition+10)/10]!=1;
    }
    
    protected boolean isLeftPossible(){
        
        if((sensXPosition%10)!=0)
            return false;

        return stg.wallCords[sensXPosition/10][(sensYPosition-10)/10]!=1;
 
    }
    
    protected boolean isUpPossible(){
        
        if((sensYPosition%10)!=0)
            return false;
        if(lastMovDirection==MOVE_LEFT){
           System.out.println("go");
        }
        return stg.wallCords[(sensXPosition-10)/10][sensYPosition/10]!=1;
    }
    
    protected boolean isDownPossible(){
        
        if((sensYPosition%10)!=0)
            return false;
        if(lastMovDirection==MOVE_LEFT){
           System.out.println("go");
        }
        return stg.wallCords[(sensXPosition+10)/10][sensYPosition/10]!=1;
    }
    
    public String getGhostPosition(){
        String sPosX = Double.toString(this.pos.getX());
        String sPosY = Double.toString(this.pos.getY());
        
        return sPosX +" "+ sPosY;
    }
    
    public boolean keepCurrentPath(){
        
        switch(lastMovDirection){
            case MOVE_LEFT:
                if(!isLeftPossible())
                    return false;
                if(isUpPossible())
                    return false;
                if(isDownPossible())
                    return false;               
                break;
            case MOVE_RIGHT:
                if(!isRightPossible())
                    return false;
                if(isUpPossible())
                    return false;
                if(isDownPossible())
                    return false; 
                break;
            case MOVE_UP:
                if(!isUpPossible())
                    return false;
                if(isRightPossible())
                    return false;
                if(isLeftPossible())
                    return false;
                break;
            case MOVE_DOWN:
                if(!isDownPossible())
                    return false;  
                if(isRightPossible())
                    return false;
                if(isLeftPossible())
                    return false;
                break;
            case STOP:
                return false;
        }
             
        return true;
    }
    
    public int getLastMovDirection(){
        return lastMovDirection;
    }
    
    public void move() {
        switch (movDirection) {
            case MOVE_LEFT:
                this.moveLeft();
                lastMovDirection = MOVE_LEFT;
                break;
            case MOVE_RIGHT:
                this.moveRight();
                lastMovDirection = MOVE_RIGHT;
                break;
            case MOVE_UP:
                this.moveUp();
                lastMovDirection = MOVE_UP;
                break;
            case MOVE_DOWN:
                this.moveDown();
                lastMovDirection = MOVE_DOWN;
                break;
            default:
                break;
        }
    }
    
}
