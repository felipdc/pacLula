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
        return stg.wallCords[(int)pos.getX()][(int)pos.getY()+1]!=1;
    }
    
    protected boolean isLeftPossible(){
        return stg.wallCords[(int)pos.getX()][(int)pos.getY()-1]!=1;
    }
    
    protected boolean isUpPossible(){
        return stg.wallCords[(int)pos.getX()-1][(int)pos.getY()]!=1;
    }
    
    protected boolean isDownPossible(){
        return stg.wallCords[(int)pos.getX()+1][(int)pos.getY()]!=1;
    }
    
    public void move() {
        switch (movDirection) {
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
            default:
                break;
        }
    }
    
}
