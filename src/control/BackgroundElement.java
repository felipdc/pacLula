/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;
import elements.Blinky;
import elements.Coin;
import elements.Element;
import elements.Fruit;
import elements.Glyde;
import elements.Inky;
import elements.Lolo;
import elements.Pinky;
import elements.PowerPellet;
import elements.Wall;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import utils.Consts;

/**
 *
 * @author Felipe
 */
public class BackgroundElement implements Serializable {
    
    //declare elements
    protected final Lolo lolo;
    protected final Blinky blinky;
    protected final Pinky pinky;
    protected final Inky inky;
    protected final Glyde glyde;
    protected final Stage stage = new Stage(1);
    protected final Wall[] walls = new Wall[stage.getWallNumber()+1];
    protected final Coin[] coins = new Coin[utils.Consts.NUM_CELLS*utils.Consts.NUM_CELLS+1];
    protected final Fruit[] fruits = new Fruit[2];
    protected final PowerPellet[] pellets = new PowerPellet[4];
    private boolean finishedScreen = false;
    
    private final transient GameScreen gameScreen;
    public ArrayList<Element> elemArray = new ArrayList<>();
    
    
    public BackgroundElement(GameScreen gameS){
        
        this.gameScreen = gameS;
        
        lolo = new Lolo("lula.png");
        lolo.setPosition(1, 1);
        this.addElement(lolo);
        
        //adding ghost blinky to stage
        blinky = new Blinky("blinky.png", this);
        blinky.setPosition(9,7);
        this.addElement(blinky);
        
        //adding ghost pinky to stage
        pinky = new Pinky("pinky.png", this);
        pinky.setPosition(10, 8);
        this.addElement(pinky);
        
        //adding ghost inky to stage      
        inky = new Inky("inky.png", this);
        inky.setPosition(10,7);
        this.addElement(inky);
        
        //adding ghost glyde to stage
        glyde = new Glyde("clyde.png", this);
        glyde.setPosition(9,8);
        this.addElement(glyde);
        
        addStageElements();
        
    }
    
    
    public final void addElement(Element elem) {
        elemArray.add(elem);
    }
    
    public void removeElement(Element elem) {
        elemArray.remove(elem);
    }
    
    private void addStageElements(){
        //adding walls,coins and power-pellets to stage
        
        int k=0;
        int z=0;
        int p=0;     
        
        for(int i=0;i<Consts.NUM_CELLS;i++){
            for(int j=0;j<Consts.NUM_CELLS;j++){
                    
                if(stage.wallCords[i][j]==1){                
                    walls[k]=new Wall("wall1.png");
                    walls[k].setPosition(i,j);
                    this.addElement(walls[k]);
                    k++;  
                }
                if(stage.wallCords[i][j]==0){
                    coins[z]=new Coin("money1.png");
                    coins[z].setPosition(i,j);
                    this.addElement(coins[z]);
                    z++;
                }               
                if(stage.wallCords[i][j]==3){
                    pellets[p]=new PowerPellet("lula_2018.jpg");
                    pellets[p].setPosition(i,j);
                    this.addElement(pellets[p]);
                    p++;
                }
                
            } 
        }
    }
    
    public void fruitSpawn(){
        
        //create random int object
        Random rn = new Random();
        int randomPosition[] = new int[2];
        
        //create fruit object of type 1
        Fruit f1 = new Fruit("fruit_cut.png",Consts.ID_FRUIT1);
        //create fruit object of type 2
        Fruit f2 = new Fruit("fruit_bolsa_familia.png",Consts.ID_FRUIT2);
       
        TimerTask spawnCut = new TimerTask(){
            public void run(){
                LOGGER.log(Level.INFO,"Adding f1");
                
                do{
                    //set a random position for the fruit
                    randomPosition[0] = rn.nextInt(Consts.NUM_CELLS-1);
                    randomPosition[1] = rn.nextInt(Consts.NUM_CELLS-1);
                }
                //check if the position generated is valid for the fruit
                while(stage.wallCords[randomPosition[0]][randomPosition[1]]!=0);
                
                f1.setPosition(randomPosition[0],randomPosition[1]);
                addElement(f1);                 
            }
        };
        
        TimerTask removeCut = new TimerTask(){
            public void run(){
                if(elemArray.contains(f1)){
                    LOGGER.log(Level.INFO,"Removing f1");
                    removeElement(f1);                            
                }
            }
        };
        
        TimerTask spawnBolsa = new TimerTask(){
            public void run(){
                LOGGER.log(Level.INFO,"Adding f2");
                 do{
                    //set a random position for the fruit
                    randomPosition[0] = rn.nextInt(Consts.NUM_CELLS-1);
                    randomPosition[1] = rn.nextInt(Consts.NUM_CELLS-1);
                }
                //check if the position generated is valid for the fruit
                while(stage.wallCords[randomPosition[0]][randomPosition[1]]!=0);
                
                f2.setPosition(randomPosition[0],randomPosition[1]);
                addElement(f2);
            }
        };
        
        TimerTask removeBolsa = new TimerTask(){
            public void run(){
                if(elemArray.contains(f2)){
                    LOGGER.log(Level.INFO,"Removing f2");
                    removeElement(f2);                            
                }
            }
        };
                
        Timer timeToRemoveCut = new Timer();
        timeToRemoveCut.schedule(removeCut,Consts.FRUIT1_SPAWN_TIME-100, Consts.FRUIT_DESTROY_TIME);
        
        Timer timeToRemoveBolsa = new Timer();
        timeToRemoveBolsa.schedule(removeBolsa, Consts.FRUIT2_SPAWN_TIME-100,Consts.FRUIT_DESTROY_TIME);
        
        Timer timeToSpawnCut = new Timer();
        timeToSpawnCut.schedule(spawnCut,Consts.FRUIT1_SPAWN_TIME, Consts.FRUIT1_SPAWN_TIME);
        
        Timer timeToSpawnBolsa = new Timer();
        timeToSpawnBolsa.schedule(spawnBolsa,Consts.FRUIT2_SPAWN_TIME,Consts.FRUIT2_SPAWN_TIME);
        
    }
    
    
    public void gameOver(){
        if(!finishedScreen){
            finishedScreen = true;
            gameScreen.setVisible(false);          
            GameOverScreen overScreen = new GameOverScreen();
            overScreen.setVisible(true);
            gameScreen.dispose();
        }
    }
    
    public void loloCaught(){
        
        lolo.decreaseLifes();
        
        lolo.setPosition(1, 1);
        blinky.setPosition(10, 7);
        pinky.setPosition(10,8);
        inky.setPosition(9,7);
        glyde.setPosition(9, 8);
        
        
    }
    
    public void saveGame(){
        
        
        try{
		   
		   /*
		    * Responsável por carregar o arquivo address.ser
		    * */
                   
                    System.out.println("Serialized data is saved in "+new java.io.File(".").getCanonicalPath()+Consts.SER_PATH+"savedData.ser");
		    FileOutputStream fileOut =
         new FileOutputStream(new java.io.File(".").getCanonicalPath()+Consts.SER_PATH+"savedData.ser");
                    
                    ObjectOutputStream out = new ObjectOutputStream(fileOut);
                    
                    out.writeObject(this);
                    out.close();
                    fileOut.close();
                    
                    System.out.println("Serialized data is saved in "+Consts.SER_PATH+"savedData.ser");
 
	   }catch(Exception ex){
		   ex.printStackTrace(); 
	   } 
    
    }

    
}
