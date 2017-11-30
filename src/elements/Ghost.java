package elements;

import control.BackgroundElement;
import control.GameScreen;
import control.Stage;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.ImageIcon;
import utils.Consts;
import utils.Drawing;

public class Ghost extends Element{
    
    public static final int LOLO_DEAD = 1;
    public static final int GHOST_DEAD = 2;
    private int movDirection = STOP;
    private Stage stg = new Stage(1);
    private int sensXPosition;
    private int sensYPosition;
    private String ghostImage;
    private boolean scared = false;
    private boolean jump = false;
    
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
    
    public int checkIfLoloIsDead(Lolo llolo, BackgroundElement bg){
        
        if(llolo.overlap(this)){
            if(!scared){
                if(llolo.getLifes()==0){
                    bg.gameOver();
                }
                if(llolo.getLifes()!=0){
                    bg.loloCaught();
                }
                return LOLO_DEAD;
            }else{
                return GHOST_DEAD;
            }
        }
        return 0;
        
    }
    
    public boolean jumpMove(){
        return jump;
    }
    
    public void setJump(boolean opt){
        jump = opt;
    }
    
    public boolean isLoloPowered(Lolo llolo){
        return llolo.getPelletPowered();
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
        
        //System.out.println("xs = "+sensXPosition+" x = "+sensXPosition/10);
        //System.out.println("ys = "+sensYPosition+" y = "+sensYPosition/10);
    }
    
    public void teleportGhost(){
        if(pos.getX()==10&&pos.getY()==0){
            this.setPosition(10, 19);
        }else{
            this.setPosition(10,0);
        }
    }
    
    protected boolean isRightPossible(){
        
        if((sensXPosition%10)!=0)
            return false;
        
        //System.out.println(sensXPosition +" "+ sensYPosition);
        try{
            if(stg.wallCords[sensXPosition/10][(sensYPosition+10)/10]!=1){
            //System.out.println("Right is now possible in position: ");
            //System.out.println(sensXPosition +" "+ sensYPosition);
                return true;
            }else{
                return false;
            }
        }catch(ArrayIndexOutOfBoundsException e){
            teleportGhost();
            return true;
        }
        //return stg.wallCords[sensXPosition/10][(sensYPosition+10)/10]!=1;
    }
    
    protected boolean isLeftPossible(){
        
        if((sensXPosition%10)!=0)
            return false;
                    
        //System.out.println(sensXPosition +" "+ sensYPosition);
        try{
            if(stg.wallCords[sensXPosition/10][(sensYPosition-1)/10]!=1){
            //System.out.println("Left is now possible in position: ");
            //System.out.println(sensXPosition +" "+ sensYPosition);
                return true;
            }else{
                return false;
            }
            
        }catch(ArrayIndexOutOfBoundsException e){
            teleportGhost();
            return true;
        }

        //return stg.wallCords[sensXPosition/10][(sensYPosition-10)/10]!=1;
 
    }
    
    protected boolean isUpPossible(){
        
        if((sensYPosition%10)!=0)
            return false;
        
        //System.out.println(sensXPosition +" "+ sensYPosition);
        if(stg.wallCords[(sensXPosition-1)/10][sensYPosition/10]!=1){
            //System.out.println("Up is now possible in position: ");
            //System.out.println(sensXPosition +" "+ sensYPosition);
            return true;
        }else{
            
            return false;
        }
        
        //return stg.wallCords[(sensXPosition-10)/10][sensYPosition/10]!=1;
    }
    
    protected boolean isDownPossible(){
        
        if((sensYPosition%10)!=0)
            return false;
        
       // System.out.println(sensXPosition +" "+ sensYPosition);

       if(stg.wallCords[(sensXPosition+10)/10][sensYPosition/10]!=1){
            //System.out.println("Down is now possible in position: ");
            //System.out.println(sensXPosition +" "+ sensYPosition);
            return true;
        }else{
            
            return false;
        }
        
       
       // return stg.wallCords[(sensXPosition+10)/10][sensYPosition/10]!=1;
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
    
    public void paintGhost(String imageName){
         try {
            ghostImage=imageName;
            imageIcon = new ImageIcon(new java.io.File(".").getCanonicalPath() + Consts.PATH + imageName);
            Image img = imageIcon.getImage();
            BufferedImage bi = new BufferedImage(Consts.CELL_SIZE, Consts.CELL_SIZE, BufferedImage.TYPE_INT_ARGB);
            Graphics g = bi.createGraphics();
            g.drawImage(img, 0, 0, Consts.CELL_SIZE, Consts.CELL_SIZE, null);
            imageIcon = new ImageIcon(bi);
            
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public String getGhostImage(){
        return ghostImage;
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
