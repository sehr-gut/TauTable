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
    
    
    
    
    String message;
    JTextField txtf;
    Font font;
    Color background, foreground, backgroundFocus, foregroundFocus;

   
   

    public FocusHandler(String s, JTextField t, Font f,
            Color c, Color textFoc) {
        message = s;
        txtf = t;
        font = f;
        setBackgroundFocus(c);
        setForegroundFocus(textFoc);
        setColors(); 
    }
     public Color getForegroundFocus() {
        return foregroundFocus;
    }

    public void setForegroundFocus(Color txtFoc) {
        this.foregroundFocus = txtFoc;
    }

    public Color getBackground() {
        return background;
    }

    public void setBackground(Color b) {
        this.background = b;
    }

    public Color getForeground() {
        return foreground;
    }

    public void setForeground(Color fr) {
        this.foreground = fr;
    }

    public Color getBackgroundFocus() {
        return backgroundFocus;
    }

    public void setBackgroundFocus(Color foc) {
        this.backgroundFocus = foc;
    }
    
    public String getMessage() {
        return message;
    }
     public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
        txtf.setFont(font);
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public JTextField getTxtf() {
        return txtf;
    }

    public void setTxtf(JTextField txtf) {
        this.txtf = txtf;
    }
    
    public void setColors() {
        setBackground(txtf.getBackground());
        setForeground(txtf.getForeground());
    }
    

    @Override
    public void focusGained(FocusEvent e) {
        String txt = txtf.getText();
        txtf.setBackground(backgroundFocus);

        if(txt.equals(message)) {
            txtf.setText("");
            txtf.setForeground(foregroundFocus);
        }
            
    }
    @Override
    public void focusLost(FocusEvent e) {
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
