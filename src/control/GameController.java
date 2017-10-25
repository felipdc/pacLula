package control;

import elements.Element;
import elements.Lolo;
import java.awt.Graphics;
import java.util.ArrayList;
import utils.Consts;

/**
 * Projeto de POO 2017
 * 
 * @author Luiz Eduardo
 * Baseado em material do Prof. Jose Fernando Junior
 */

public class GameController {
    
    private int score=0;
    
    public void drawAllElements(ArrayList<Element> elemArray, Graphics g){
        for(int i=0; i<elemArray.size(); i++){
            elemArray.get(i).autoDraw(g);
        }
    }
    public void processAllElements(ArrayList<Element> e){
        if(e.isEmpty())
            return;
        
        Lolo lLolo = (Lolo)e.get(0);
        if (!isValidPosition(e, lLolo)) {
            lLolo.backToLastPosition();
            lLolo.setMovDirection(Lolo.STOP);
            return;
        }
        
        Element eTemp;
        String eTempName;
        
        for(int i = 1; i < e.size(); i++){
            eTemp = e.get(i);
            if(lLolo.overlap(eTemp)){
                if(eTemp.isTransposable()){
                    e.remove(eTemp);
                    //check which element was removed
                    eTempName = String.valueOf(eTemp);
                    if(eTempName.contains("Coin")){
                        score=score+Consts.COIN_POINT;
                    }
                    if(eTempName.contains("Fruit")){
                        score=score+Consts.FRUIT1_POINT;
                    }
                }
            }
            
        }
        
        lLolo.move();
    }
    public boolean isValidPosition(ArrayList<Element> elemArray, Element elem){
        Element elemAux;
        for(int i = 1; i < elemArray.size(); i++){
            elemAux = elemArray.get(i);            
            if(!elemAux.isTransposable())
                if(elemAux.overlap(elem))
                    return false;
        }        
        return true;
    }
    
    public int getScore(){
        return score;
    }
}
