package elements;

import java.awt.Graphics;
import utils.Drawing;


public class Wall extends Element{
    
    public Wall(String imageName){
        super(imageName);
        this.isTransposable = false;
    }

    @Override
    public void autoDraw(Graphics g) {
          Drawing.draw(g, this.imageIcon, pos.getY(), pos.getX());
    }
    
}
