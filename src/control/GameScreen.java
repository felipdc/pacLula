package control;

import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;
import elements.Blinky;
import elements.Skull;
import elements.Lolo;
import elements.Element;
import elements.Wall;
import elements.Coin;
import elements.Fruit;
import elements.Glyde;
import elements.Inky;
import elements.Pinky;
import elements.PowerPellet;
import java.awt.Dimension;
import utils.Consts;
import utils.Drawing;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

/**
 * Projeto de POO 2017
 * 
 * @author Luiz Eduardo
 * Baseado em material do Prof. Jose Fernando Junior
 */
public final class GameScreen extends javax.swing.JFrame implements KeyListener {
    
    private final Lolo lolo;
    private final Blinky blinky;
    private final Pinky pinky;
    private final Inky inky;
    private final Glyde glyde;
    private final ArrayList<Element> elemArray;
    private final GameController controller = new GameController();
    private final Stage stage = new Stage(1);
    private final Wall[] walls = new Wall[stage.getWallNumber()+1];
    private final Coin[] coins = new Coin[utils.Consts.NUM_CELLS*utils.Consts.NUM_CELLS+1];
    private final Fruit[] fruits = new Fruit[2];
    private final PowerPellet[] pellets = new PowerPellet[4];
    private boolean finishedScreen = false;
    private int lifes = 3;
    GameOverScreen overScreen;
 

    public GameScreen() {
        
        Drawing.setGameScreen(this);
        initComponents();
        setFrameIconImage();
        
        this.addKeyListener(this);   /*teclado*/
        
        /*Cria a janela do tamanho do tabuleiro + insets (bordas) da janela*/
        this.setSize(Consts.NUM_CELLS * Consts.CELL_SIZE + getInsets().left + getInsets().right,
                     Consts.NUM_CELLS * Consts.CELL_SIZE + getInsets().top + getInsets().bottom);

        elemArray = new ArrayList<Element>();

        /*Cria e adiciona elementos*/
        
        //adding lolo to stage
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
        
        
        //start fruit counter
        fruitSpawn();
        
        int k=0;
        int z=0;
        int p=0;
        
        //adding walls,coins and power-pellets to stage        
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
    
    public final void addElement(Element elem) {
        elemArray.add(elem);
    }
    
    public void removeElement(Element elem) {
        elemArray.remove(elem);
    }
    
    @Override
    public void paint(Graphics gOld) {
        Graphics g = getBufferStrategy().getDrawGraphics();
        
        /*Criamos um contexto grafico*/
        Graphics g2 = g.create(getInsets().right, getInsets().top, getWidth() - getInsets().left, getHeight() - getInsets().bottom);
        
        for (int i = 0; i < Consts.NUM_CELLS; i++) {
            for (int j = 0; j < Consts.NUM_CELLS; j++) {
                try {
                    Image newImage = Toolkit.getDefaultToolkit().getImage(new java.io.File(".").getCanonicalPath() + Consts.PATH + "ground1.png");
                    g2.drawImage(newImage,
                            j * Consts.CELL_SIZE, i * Consts.CELL_SIZE, Consts.CELL_SIZE, Consts.CELL_SIZE, null);
                    
                } catch (IOException ex) {
                    Logger.getLogger(GameScreen.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        this.controller.drawAllElements(elemArray, g2);
        this.controller.processAllElements(elemArray);
        this.setTitle("-> Cell: " + lolo.getStringPosition() + "-> Score: R$"+ controller.getScore() + "-> Lifes: " + lolo.getLifes() + lolo.getPelletPowered());
        
        g.dispose();
        g2.dispose();
        if (!getBufferStrategy().contentsLost()) {
            getBufferStrategy().show();
        }
       
    }
    
    public void go() {
        TimerTask task = new TimerTask() {
            
            public void run() {
                repaint();
                //start ghost search for lolo
                blinky.seekLolo(lolo);
                pinky.seekLolo(lolo);
                inky.seekLolo(lolo);
                glyde.seekLolo(lolo);
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 0, Consts.DELAY);
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
    
    private void setFrameIconImage(){
        try{
            ImageIcon img = new ImageIcon(new java.io.File(".").getCanonicalPath() + Consts.PATH + "lula.png");
            this.setIconImage(img.getImage());
        }catch(IOException ex){
            Logger.getLogger(StartScreen.class.getName()).log(Level.SEVERE, null, ex);
        }
        

    }
    
    public void gameOver(){
        if(!finishedScreen){
            finishedScreen = true;
            this.setVisible(false);
            overScreen = new GameOverScreen();
            overScreen.setVisible(true);
            this.dispose();
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
    
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                lolo.setDesireDirection(Lolo.MOVE_UP);
                break;
        //repaint(); /*invoca o paint imediatamente, sem aguardar o refresh*/
            case KeyEvent.VK_DOWN:
                lolo.setDesireDirection(Lolo.MOVE_DOWN);
                break;
            case KeyEvent.VK_LEFT:
                lolo.setDesireDirection(Lolo.MOVE_LEFT);
                break;
            case KeyEvent.VK_RIGHT:
                lolo.setDesireDirection(Lolo.MOVE_RIGHT);
                break;
            case KeyEvent.VK_SPACE:
                lolo.setDesireDirection(Lolo.STOP);
                break;
            default:
                break;
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("SCC0604 - Pacman");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setLocation(new java.awt.Point(20, 20));
        setResizable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    
    @Override
    public void keyTyped(KeyEvent e) {
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
    }
    
}
