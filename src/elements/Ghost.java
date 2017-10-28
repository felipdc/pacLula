package elements;

import java.awt.Graphics;
import utils.Drawing;

public class Ghost extends Element{

    public Ghost(String imageName) {
        super(imageName);
    }

    @Override
    public void autoDraw(Graphics g) {
       Drawing.draw(g, this.imageIcon, pos.getY(), pos.getX());
    }
    
}
