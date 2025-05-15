/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tautable;

import corefunctions.TruthTable;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author Franklin Xam
 */
public class View {
    private Panel1 p1 = new Panel1();    
    private Panel2 p2 = new Panel2();
    private Panel3 p3 = new Panel3();
    private JFrame f; 
    public View() {
        f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        showView();
    }
    public void showView() {
        
        f.setSize(410, 320);
        f.add(p1); 
        f.setVisible(true);
    }
    public JFrame getFrame() {
        return f;
    }
    public RoundedButton getRB1() {
        return p1.getRoundedButton1();
    }
        
        
    public void updateView() {
        JTextField jtf = p1.getInputField();
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
    public Panel3 getP3() {
        return p3;
    }
    public Panel2 getP2() {
        return p2;
    }
    public Panel1 getP1() {
        return p1;
    }
    public JTextField p1GetInputField() {
        return p1.getInputField();
    }
    public JLabel p1GetLabel() {
        return p1.getLabel();
    }
    
    public void firstPage() {
        p1 = new Panel1();
        f.add(p1);
        
        f.validate();
        f.repaint();
    }
    public void showTable(TruthTable t) {
        p3.setT(t);
        p3.initTable();
        f.add(p3);
        f.validate();
    }
    public JButton getRestartButton() {
        return p3.getRestartButton();
    }
}
