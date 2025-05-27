/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tautable;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JTextField;

/**
 *
 * @author Franklin Xam
 */
public class FocusHandler implements FocusListener { 
    // handles the input field behaviors
    String message;
    JTextField txtf;
    Font font;
    Color background, foreground, backgroundFocus, foregroundFocus;

   
   
    // uses BUILDER Design pattern
    public FocusHandler() {
        
    }
     public Color getForegroundFocus() {
        return foregroundFocus;
    }

    public FocusHandler setForegroundFocus(Color txtFoc) {
        this.foregroundFocus = txtFoc;
        return this;
    }

    public Color getBackground() {
        return background;
    }

    public FocusHandler setBackground(Color b) {
        this.background = b;
        return this;
    }

    public Color getForeground() {
        return foreground;
    }

    public FocusHandler setForeground(Color fr) {
        this.foreground = fr;
        return this;
    }

    public Color getBackgroundFocus() {
        return backgroundFocus;
    }

    public FocusHandler setBackgroundFocus(Color foc) {
        this.backgroundFocus = foc;
        return this;
    }
    
    public String getMessage() {
        return message;
    }
     public Font getFont() {
        return font;
    }

    public FocusHandler setFont(Font font) {
        this.font = font;
        txtf.setFont(font);
        return this;
    }

    public FocusHandler setMessage(String message) {
        this.message = message;
        return this;
    }

    public JTextField getTxtf() {
        return txtf;
    }

    public FocusHandler setTxtf(JTextField txtf) {
        this.txtf = txtf;
        return this;
    }
    
    public void setColors() {
        setBackground(txtf.getBackground());
        setForeground(txtf.getForeground());
    }
    

    @Override
    public void focusGained(FocusEvent e) { 
        // this removes the placeholder text of the input field
                                            
        String txt = txtf.getText();
        txtf.setBackground(backgroundFocus);

        if(txt.equals(message)) {
            txtf.setText("");
            txtf.setForeground(foregroundFocus);
        }
            
    }
    @Override
    public void focusLost(FocusEvent e) {
        // this handles the behavior of the input field
        // such as puting back the placeholder if the 
        // field is empty
        String t = txtf.getText();
        txtf.setBackground(background);
        
        if(t.equals("")) { 
            txtf.setText(message);
            txtf.setForeground(foreground);
        } else {
            txtf.setText(t);
        }
    }
}
