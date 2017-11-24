/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elements;

import java.awt.Graphics;
import utils.Consts;
import utils.Drawing;

/**
 *
 * @author Felipe
 */
public class PowerPellet extends Element{
        
    public PowerPellet(String imageName) {
        super(imageName);
        elementId = Consts.ID_PELLET;
    }

    @Override
    public void autoDraw(Graphics g) {
        Drawing.draw(g, this.imageIcon, pos.getY(), pos.getX());
    }
    
}
