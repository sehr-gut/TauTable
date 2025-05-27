/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tautable;

import com.formdev.flatlaf.FlatDarkLaf;
import corefunctions.TruthTable;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.UIManager;

/**
 *
 * @author Franklin Xam
 */
public class View {
    // The V of the design pattern
    private Panel1 p1;    
    private Panel2 p2;
    private Panel3 p3;
    private JFrame f; 
    
    public View() {
        try {
            FlatDarkLaf.registerCustomDefaultsSource("style");
            // using the style package for the stylinng
                // adding custom styling using FlatDarkLaf
            UIManager.setLookAndFeel(new FlatDarkLaf());
            f = new JFrame();
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setSize(410, 320);
        } catch (Exception e) {
            
        }
        
    }      
    public void updateView() { // for handling the changes in the panel1
        JTextField jtf = p1.getInputField();
        String in = jtf.getText().toLowerCase();
        if(!in.equals("") && !in.equals("input here")) {
            showLoading();
        }
    }
    public void firstPage() { // this is for reloading the first page
        p1 = new Panel1();
        f.add(p1);
    }
    public void showLoading() { // changing the view to the loading screen
        p2 = new Panel2();
        removePanels(); // to avoid backlog of panels
        f.add(p2);
        f.validate();
        f.repaint();
    }
    public void showTable(TruthTable t) { // showing of the table in panel3
        removePanels();
        p3 = new Panel3();
        f.add(p3);
        p3.getjTable1().setModel(t);
        f.validate();
        f.repaint();
    }
    
    public JScrollPane getScrollPane() {
        return p3.getScrollPane();
    }
    public Panel3 getP3() {
        return p3;
    }
    public Panel2 getP2() {
        return p2;
    }
    public Panel1 getP1() { 
        return p1;
    }
    public JFrame getFrame() {
        return f;
    }
    public JButton getGenButton() { //pane1
        return p1.getButton();
    }
    public JTextField p1GetInputField() { // panel 
        return p1.getInputField();
    }
    public JLabel p1GetLabel() { //panel1
        return p1.getLabel();
    }
    public void removePanels() {
        if(p1 != null) f.remove(p1);
        if(p2 != null) f.remove(p2);
        if(p3 != null) f.remove(p3);
    }
    public JButton getRestartButton() { // panel1
        return p3.getRestartButton();
    }
}
