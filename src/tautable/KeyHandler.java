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
    // Handles the logic and communications between
    // the display and the input
    private JTextField jtx;
    private JLabel lab;
    private boolean hasError;
    private int n = 0;
            
    public boolean isHasError() { // returns any error
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
    }
    @Override
    public void keyReleased(KeyEvent e) {
        addHandler(); // handles the connection between the handler and the
                       // and the input field
//        System.out.println(hasError);
        
    }
    public void addHandler() {
        String s = jtx.getText(); // gets the text from the panel
        lab.setText(s);// setting the text of the display
        if(!s.equals("")){ // checks if the input is not empty
             Lexer lx = new Lexer(s); // scans for tokens
             lx.scan();
             List<Token> tokens = lx.getTokens();
             boolean fail = lx.getError(); // checks if any error occured
             if(fail) {
                 lab.setForeground(new Color(70, 70, 70)); // unhighlights 
                                                    // the display
                 lab.setText("Wrong character used"); // error message
                                            //for lexicographical errors
                 hasError = true;
             } else {
                 Parser p = new Parser(tokens);  // primary parsing to 
                                                // check for parse errors
                 Expression expr = p.parse();
                 if(p.getErrorStatus()) { // if an error occurs
                     lab.setForeground(new Color(70, 70, 70));// unhighlights 
                                                    // the display
                     lab.setText("Not a propositional statement"); // error message
                     hasError = true; // parse error
                 } else {
                     String strProp = new ASTPrinter().print(expr); // else
                                           // display the right expressions
                     lab.setForeground(new Color(220, 220, 220));
                                            // highlight it
                     lab.setText(strProp);
                 }
             } 
       }
    }
}
