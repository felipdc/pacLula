package elements;

import control.Stage;
import java.awt.Graphics;
import utils.Drawing;

public class Ghost extends Element{
    
    public static final int STOP = 0;
    public static final int MOVE_LEFT = 1;
    public static final int MOVE_RIGHT = 2;
    public static final int MOVE_UP = 3;
    public static final int MOVE_DOWN = 4;
    
    private int movDirection = STOP;
    private int lastMovDirection = STOP;
    private int desireDirection = STOP;
    private Stage stg = new Stage(1);
    
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
    
    protected boolean isRightPossible(){
        if(desireDirection==MOVE_RIGHT&&lastMovDirection==MOVE_UP){
            return stg.wallCords[(int)(pos.getX()+0.9d)][(int)(pos.getY())+1]!=1;
        }
        return stg.wallCords[(int)pos.getX()][(int)(pos.getY())+1]!=1;
    }
    
    protected boolean isLeftPossible(){
        if(desireDirection==MOVE_LEFT&&lastMovDirection==MOVE_UP){
            return stg.wallCords[(int)(pos.getX()+0.9d)][(int)(pos.getY())-1]!=1;
        }
        return stg.wallCords[(int)pos.getX()][(int)(pos.getY())-1]!=1;
    }
    
    protected boolean isUpPossible(){
        if(lastMovDirection==MOVE_LEFT){
            return stg.wallCords[(int)(pos.getX())-1][(int)(pos.getY()+0.9d)]!=1;
        }
        return stg.wallCords[(int)(pos.getX())-1][(int)(pos.getY())]!=1;
    }
    
    protected boolean isDownPossible(){
        if(lastMovDirection==MOVE_LEFT){
            return stg.wallCords[(int)(pos.getX())+1][(int)(pos.getY()+0.9d)]!=1;
        }
        return stg.wallCords[(int)(pos.getX())+1][(int)(pos.getY())]!=1;
    }
    
    public String getGhostPosition(){
        String sPosX = Double.toString(this.pos.getX());
        String sPosY = Double.toString(this.pos.getY());
        
        return sPosX +" "+ sPosY;
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
