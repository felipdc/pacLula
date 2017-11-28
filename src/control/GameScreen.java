package control;

import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;
import elements.Blinky;
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
    
  
    private final GameController controller = new GameController();  
    private final BackgroundElement bgElement;
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
        
        bgElement = new BackgroundElement(this);

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

        this.controller.drawAllElements(bgElement.elemArray, g2);
        this.controller.processAllElements(bgElement.elemArray);
        this.setTitle("-> Cell: " + bgElement.lolo.getStringPosition() + "-> Score: R$"+ bgElement.lolo.getScore() + "-> Lifes: " + bgElement.lolo.getLifes() + bgElement.lolo.getPelletPowered());
        
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
                bgElement.blinky.seekLolo(bgElement.lolo);
                bgElement.pinky.seekLolo(bgElement.lolo);
                bgElement.inky.seekLolo(bgElement.lolo);
                bgElement.glyde.seekLolo(bgElement.lolo);
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 0, Consts.DELAY);
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
        
        bgElement.lolo.decreaseLifes();
        
        bgElement.lolo.setPosition(1, 1);
        bgElement.blinky.setPosition(10, 7);
        bgElement.pinky.setPosition(10,8);
        bgElement.inky.setPosition(9,7);
        bgElement.glyde.setPosition(9, 8);
        
        
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                bgElement.lolo.setDesireDirection(Lolo.MOVE_UP);
                break;
        //repaint(); /*invoca o paint imediatamente, sem aguardar o refresh*/
            case KeyEvent.VK_DOWN:
                bgElement.lolo.setDesireDirection(Lolo.MOVE_DOWN);
                break;
            case KeyEvent.VK_LEFT:
                bgElement.lolo.setDesireDirection(Lolo.MOVE_LEFT);
                break;
            case KeyEvent.VK_RIGHT:
                bgElement.lolo.setDesireDirection(Lolo.MOVE_RIGHT);
                break;
            case KeyEvent.VK_SPACE:
                bgElement.lolo.setDesireDirection(Lolo.STOP);
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
