/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tautable;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import javax.swing.JButton;

/**
 *
 * @author Franklin Xam
 */
public class MouseButtonHandler extends MouseAdapter {
    @Override
    public void mouseExited(MouseEvent e) {
        super.mouseExited(e); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        JButton jb = (JButton)e.getSource();
        
        jb.setBackground(new Color(31,39,18));
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println("yes");
        super.mousePressed(e); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        JButton jb = (JButton)e.getSource();
        
        jb.setBackground(new Color(31,39,18));
    }
}
