/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elements;

import java.awt.Graphics;
import utils.Drawing;

/**
 *
 * @author metalgas
 */
public class Fruit extends Element{
    
    public Fruit(String imageName) {
        super(imageName);
        this.isMortal = true;
    }
    
    public void autoDraw(Graphics g) {
        Drawing.draw(g, this.imageIcon, pos.getY(), pos.getX());
    }
    
}
