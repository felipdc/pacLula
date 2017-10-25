package elements;

import utils.Drawing;
import java.awt.Graphics;
import java.io.Serializable;
import utils.Consts;

public class Coin extends Element{
    
    public Coin(String imageName) {
        super(imageName);
        super.elementId = Consts.ID_COIN;
        this.isMortal = true;
    }
    
    public void autoDraw(Graphics g) {
        Drawing.draw(g, this.imageIcon, pos.getY(), pos.getX());
    }
    

}
