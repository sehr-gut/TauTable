/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tautable;

import corefunctions.TruthTable;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JTextField;

/**
 *
 * @author Franklin Xam
 */
public class View {
    final Panel1 p1 = new Panel1();    
    final Panel2 p2 = new Panel2();
    final Panel3 p3 = new Panel3();
    final JFrame f = new JFrame(); 
    public View() {
        
    }
    public void showView() {
        f.setSize(410, 320);
        f.add(p1);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }
    public JFrame getFrame() {
        return f;
    }
    public RoundedButton getRB1() {
        return p1.getRoundedButton1();
    }
        
        
    public void updateView() {
        JTextField jtf = p1.getInput();
        String in = jtf.getText().toLowerCase();
        System.out.println(in);
        if(!in.equals("") && !in.equals("input here")) {
            f.remove(p1);
            f.add(p2);
            f.validate();
        } else {
            jtf.setBackground(Color.red);
        }
        
    }
    public Panel2 getP2() {
        return p2;
    }
    public String getInput() {
        return p1.getInput().getText();
    }
    public void showTable(TruthTable t) {
        
        f.add(p3);
        f.validate();
    }
}
