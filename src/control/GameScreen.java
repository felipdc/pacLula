package control;

import elements.Skull;
import elements.Lolo;
import elements.Element;
import elements.Wall;
import elements.Coin;
import elements.Fruit;
import utils.Consts;
import utils.Drawing;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Projeto de POO 2017
 * 
 * @author Luiz Eduardo
 * Baseado em material do Prof. Jose Fernando Junior
 */
public final class GameScreen extends javax.swing.JFrame implements KeyListener {
    
    private final Lolo lolo;
    private final ArrayList<Element> elemArray;
    private final GameController controller = new GameController();
    private final Stage stage = new Stage(1);
    private final Wall[] walls = new Wall[stage.getWallNumber()+1];
    private final Coin[] coins = new Coin[utils.Consts.NUM_CELLS*utils.Consts.NUM_CELLS+1];
    private final Fruit[] fruits = new Fruit[2];
 

    public GameScreen() {
        Drawing.setGameScreen(this);
        initComponents();
        
        this.addKeyListener(this);   /*teclado*/
        
        /*Cria a janela do tamanho do tabuleiro + insets (bordas) da janela*/
        this.setSize(Consts.NUM_CELLS * Consts.CELL_SIZE + getInsets().left + getInsets().right,
                     Consts.NUM_CELLS * Consts.CELL_SIZE + getInsets().top + getInsets().bottom);

        elemArray = new ArrayList<Element>();

        /*Cria e adiciona elementos*/
        lolo = new Lolo("lula.png");
        lolo.setPosition(0, 0);
        this.addElement(lolo);
        
        Skull skull = new Skull("ghost_1.png");
        skull.setPosition(9, 1);
        stage.wallCords[9][1]=2;
        this.addElement(skull);

        fruitSpawn();
        
        int k=0;
        int z=0;
        
        //adding walls and coins to stage 
        
        for(int i=0;i<Consts.NUM_CELLS;i++){
            for(int j=0;j<Consts.NUM_CELLS;j++){
                    
                if(stage.wallCords[i][j]==1){
                    
                    walls[k]=new Wall("wall1.png");
                    walls[k].setPosition(i,j);
                    this.addElement(walls[k]);
                    k++;
                    System.out.println(k);
                    
                }if(stage.wallCords[i][j]==0){
                    coins[z]=new Coin("money1.png");
                    coins[z].setPosition(i,j);
                    this.addElement(coins[z]);
                    z++;
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
        this.setTitle("-> Cell: " + lolo.getStringPosition() + "-> Score: R$"+ controller.getScore());
        
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
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 0, Consts.DELAY);
    }
    
    public void fruitSpawn(){
        
        
        Fruit f1 = new Fruit("fire.png");
        TimerTask spawnCut = new TimerTask(){
            public void run(){
                System.out.println("Adding f1");
                f1.setPosition(10,10);
                addElement(f1);                 
            }
        };
        
        TimerTask removeCut = new TimerTask(){
            public void run(){
                if(elemArray.contains(f1)){
                    System.out.println("Removing f1");
                    removeElement(f1);                            
                    }
                }
        };
                
        Timer timeToRemoveCut = new Timer();
        timeToRemoveCut.schedule(removeCut,60000, 15000);
        
        Timer timeToSpawnCut = new Timer();
        timeToSpawnCut.schedule(spawnCut,61000, 61000);
        
        
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                lolo.setMovDirection(Lolo.MOVE_UP);
                break;
        //repaint(); /*invoca o paint imediatamente, sem aguardar o refresh*/
            case KeyEvent.VK_DOWN:
                lolo.setMovDirection(Lolo.MOVE_DOWN);
                break;
            case KeyEvent.VK_LEFT:
                lolo.setMovDirection(Lolo.MOVE_LEFT);
                break;
            case KeyEvent.VK_RIGHT:
                lolo.setMovDirection(Lolo.MOVE_RIGHT);
                break;
            case KeyEvent.VK_SPACE:
                lolo.setMovDirection(Lolo.STOP);
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
