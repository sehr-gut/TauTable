/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tautable;


import corefunctions.ASTPrinter;
import corefunctions.Expression;
import corefunctions.Lexer;
import corefunctions.Parser;
import corefunctions.Token;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.util.List;
import javax.swing.JLabel;

import javax.swing.JTextField;

/**
 *
 * @author Franklin Xam
 */
public class KeyHandler implements KeyListener {
    private JTextField jtx;
    private JLabel lab;
    private boolean hasError;
    private int n = 0;
            
    public boolean isHasError() {
        return hasError;
    }
    
    public KeyHandler(JTextField txt, JLabel area) {
        jtx = txt;
        lab = area;
    }
    @Override
    public void keyTyped(KeyEvent e){
       
       
        
    }
    @Override
    public void keyPressed(KeyEvent e) {
        hasError = false;
        addHandler();
        
        if(e.getKeyCode() == KeyEvent.VK_UP) {
            if(n >= Model.previousInputs.size()) {
                jtx.setText("");
                n = 0;
            } else {
                jtx.setText(Model.previousInputs.get(n));  
                n++;     
            }   
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
        addHandler();
        
    }
    public void addHandler() {
        String s = jtx.getText();
        lab.setText(s);
        if(!s.equals("")){
             Lexer lx = new Lexer(s);
             lx.scan();
             List<Token> tokens = lx.getTokens();
             boolean fail = lx.getError();
             if(fail) {
                 lab.setForeground(new Color(56,56,56));
                 lab.setText("Wrong character used");
                 hasError = true;
             } else {
                 Parser p = new Parser(tokens); 
                 Expression expr = p.parse();
                 if(p.getErrorStatus()) {
                     lab.setForeground(new Color(56, 56, 56));
                     lab.setText("Not a propositional statement");
                     hasError = true;
                 } else {
                     String strProp = new ASTPrinter().print(expr);
                     lab.setForeground(new Color(220, 220, 220));
                     lab.setText(strProp);
                 }
             } 
       }
    }
}
