/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tautable;

import java.awt.Graphics;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JTextField;

/**
 *
 * @author Franklin Xam
 */
public class RoundedTextField extends JTextField {
    private Shape shape;
    int h;
    int radius;

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }
    
    public int getH() {
        return h;
    }

    public void setH(int size) {
        this.h = size;
        
               
    }
    public RoundedTextField() {
        super(5);
        setOpaque(false);
    }
    
   
    
    @Override
    public void paintComponent(Graphics g) {
         g.setColor(getBackground());
         g.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
         super.paintComponent(g);
    }
    @Override
    public void paintBorder(Graphics g) {
         g.setColor(getForeground());
         g.drawRoundRect(2, 2, getWidth()-4, getHeight()-4, radius, radius);
    }
    @Override
    public boolean contains(int x, int y) {
         if (shape == null || !shape.getBounds().equals(getBounds())) {
             shape = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), radius, radius);
         }
         return shape.contains(x, y);
    }
}
