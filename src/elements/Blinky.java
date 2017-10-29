package elements;

import control.Stage;
import static java.lang.Math.abs;
import utils.Consts;
/**
 *
 * @author Felipe
 */
public class Blinky extends Ghost{
    
    private Stage stg = new Stage(1);
    
    public Blinky(String imageName) {
        super(imageName, Consts.ID_GHOST1);
    }

    public void seekLolo(Lolo llolo){
        
        double yDist = pos.getX() - llolo.pos.getX();
        double xDist = pos.getY() - llolo.pos.getY();
        String axisPrior = axisPriority(yDist,xDist);
        String xPrior = xPriority(xDist);
        String yPrior = yPriority(yDist);
        int tryNum = 0;
        
        if("X".equals(axisPrior)){
            seekX(xPrior,yPrior,tryNum);
        }else{
            seekY(xPrior,yPrior,tryNum);
        }
        
        
    }
    
    public void seekX(String xPrior, String yPrior, int tryNum){
        //right is x priority and first try to move
        if(tryNum==0&&"RIGHT".equals(xPrior)){
            if(this.isRightPossible()){
                this.moveRight();
            }else{
                seekY(xPrior, yPrior, 1);
            }
        }
        //left is x priority and first try to move
        if(tryNum==0&&"LEFT".equals(xPrior)){
            if(this.isLeftPossible()){
                this.moveLeft();
            }else{
                seekY(xPrior, yPrior, 1);
            }
        }
        
        if(tryNum==1&&"RIGHT".equals(xPrior)){
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
        
        if(tryNum==1&&"LEFT".equals(xPrior)){
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
                this.moveLeft();
            }
        }
        
    }
    
    public void seekY(String xPrior, String yPrior, int tryNum){
        
        if(tryNum==0&&"UP".equals(yPrior)){
            if(this.isUpPossible()){
                this.moveUp();
            }else{
                seekX(xPrior, yPrior, 1);
            }
        }
        
        if(tryNum==0&&"DOWN".equals(yPrior)){
            if(this.isDownPossible()){
                this.moveDown();
            }else{
                seekX(xPrior, yPrior, 1);
            }
        }
        
        if(tryNum==1&&"UP".equals(yPrior)){
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
        
        if(tryNum==1&&"DOWN".equals(yPrior)){
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
                this.moveUp();
            }
        }
        
    }
    
    public String xPriority(double xDist){
        if(xDist<0){
                return "RIGHT";               
            }else{
                return "LEFT";
            }
    }
    
    public String yPriority(double yDist){
        if(yDist>0){
                return "UP";               
            }else{
                return "DOWN";
            }
    }
    
    public String axisPriority(double yDist,double xDist){
        if(abs(xDist)>abs(yDist)){
            return "X";
        }else{
            return "Y";
        }
    }
    
}