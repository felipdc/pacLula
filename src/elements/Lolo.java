package elements;

import control.Stage;
import utils.Drawing;
import java.awt.Graphics;
import java.io.Serializable;

/**
 * Projeto de POO 2017
 * 
 * @author Luiz Eduardo
 * Baseado em material do Prof. Jose Fernando Junior
 */
public class Lolo extends Element  implements Serializable{

    private int movDirection = STOP;
    private int desireDirection = STOP;
    private Stage stg = new Stage(1);
    
    public Lolo(String imageName) {
        super(imageName);
    }
    
    @Override
    public void autoDraw(Graphics g){
        Drawing.draw(g, this.imageIcon, pos.getY(), pos.getX());
    }

    public void backToLastPosition(){
        this.pos.comeBack();
    }
    
    public void setMovDirection(int direction) {
        movDirection = direction;
    }
    
    public void setDesireDirection(int direction){
        desireDirection = direction;
    }
    
    public int getLastMovDirection(){
        return lastMovDirection;
    }
    
    public int lastDirection(){
        return lastMovDirection;
    }
    
    public double getXPosition(){
        return pos.getX();
    }
    
    public double getYPosition(){
        return pos.getY();  
    }
    
    public boolean isDirectionPossible(int desireDirection){
        if(pos.getX()%1!=0&&0!=pos.getY()%1){
            return false;
        }
        if(desireDirection==MOVE_LEFT&&lastMovDirection==MOVE_UP){
            return stg.wallCords[(int)(pos.getX()+0.9d)][(int)(pos.getY())-1]!=1;
        }
        if(desireDirection==MOVE_LEFT){
            return stg.wallCords[(int)pos.getX()][(int)(pos.getY())-1]!=1;
        }
        if(desireDirection==MOVE_RIGHT&&lastMovDirection==MOVE_UP){
            return stg.wallCords[(int)(pos.getX()+0.9d)][(int)(pos.getY())+1]!=1;
        }
        if(desireDirection==MOVE_RIGHT){
            return stg.wallCords[(int)pos.getX()][(int)(pos.getY())+1]!=1;
        }
        if(desireDirection==MOVE_UP&&lastMovDirection==MOVE_LEFT){
            return stg.wallCords[(int)(pos.getX())-1][(int)(pos.getY()+0.9d)]!=1;
        }
        if(desireDirection==MOVE_UP){
            return stg.wallCords[(int)(pos.getX())-1][(int)(pos.getY())]!=1;
        }
        if(desireDirection==MOVE_DOWN&&lastMovDirection==MOVE_LEFT){
            return stg.wallCords[(int)(pos.getX())+1][(int)(pos.getY()+0.9d)]!=1;
        }
        if(desireDirection==MOVE_DOWN){
            return stg.wallCords[(int)(pos.getX())+1][(int)(pos.getY())]!=1;
        }else{
            return false;
        }
    }
    
    public void move() {
        if(isDirectionPossible(desireDirection)){
            movDirection=desireDirection;
        }else{
            movDirection = lastMovDirection;
        }
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
