/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tautable;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;

/**
 *
 * @author Franklin Xam
 */
public class RoundedButton extends JButton {
    boolean over;
    int radius;
    Color color;   
    Color colorOver;
    Color colorClick;

    public Color getColorOver() {
        return colorOver;
    }

    public void setColorOver(Color colorOver) {
        this.colorOver = colorOver;
    }

    public Color getColorClick() {
        return colorClick;
    }

    public void setColorClick(Color colorClick) {
        this.colorClick = colorClick;
    }

    public Color getColorBorder() {
        return colorBorder;
    }

    public void setColorBorder(Color colorBorder) {
        this.colorBorder = colorBorder;
    }
    Color colorBorder;

    
    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }
    
    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
        setBackground(color);
    }



    public RoundedButton() {
        setColor(Color.WHITE);
        setContentAreaFilled(false);
        addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    // Code to handle mouse release event
                    if(over) {
                        setBackground(colorOver);
                    } else {
                        setBackground(color);
                    }
                }
                @Override
                public void mouseEntered(MouseEvent e) {
                    setBackground(colorOver);
                    over = true;
                    // Code to handle mouse enter event
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    setBackground(color);
                    over = false;

                    // Code to handle mouse exit event
                }
                @Override
                public void mousePressed(MouseEvent e) {
                    setBackground(colorClick);
                    System.out.println("a");
                }
        });
    }  
    public RoundedButton(int height, int width, Color color) {
        setColor(color);
        setContentAreaFilled(false);
        addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    // Code to handle mouse release event
                    if(over) {
                        setBackground(colorOver);
                    } else {
                        setBackground(color);
                    }
                }
                @Override
                public void mouseEntered(MouseEvent e) {
                    setBackground(colorOver);
                    over = true;
                    // Code to handle mouse enter event
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    setBackground(color);
                    over = false;

                    // Code to handle mouse exit event
                }
                @Override
                public void mousePressed(MouseEvent e) {
                    setBackground(colorClick);
                    System.out.println("a");
                }
        });
    }
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
 
        //Draws the rounded panel with borders.
        graphics.setColor(color);
        graphics.fillRoundRect(0, 0,getWidth(), getHeight(), radius, radius);//paint background
        graphics.setColor(getBackground());
        graphics.drawRoundRect(2, 2, getWidth()-4, getHeight()-4, radius, radius);//paint border
        super.paintComponent(g);
        
    }
}
