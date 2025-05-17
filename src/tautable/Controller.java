/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tautable;
import java.awt.event.KeyEvent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

/**
 *
 * @author Franklin Xam
 */
public class Controller {
    Model m;
    View v;
    KeyHandler kh;
    
    
    
    public Controller(View view, Model model) {
        m = model;
        v = view;
        initKey(v.p1GetInputField(), v.p1GetLabel());
        
        
    }
    private void interpret(String s) {
         m.interpret(s);
    }
    public void initController() {
        
        v.getGenerateButton().addActionListener(e -> updateView());
        v.getRestartButton().addActionListener(e -> restart());
        m.readFromCsv();
    }
    public void initView() {
        v.getFrame().setVisible(true);
    }
    
    private void initKey(JTextField jTxtf, JLabel lab) {
        kh = new KeyHandler(jTxtf, lab);
        jTxtf.addKeyListener(kh);
    }
    private void updateView() {
        String s = v.p1GetInputField().getText();
        if(!kh.isHasError() && !s.equals("") && !s.equals("Input Here")) {
           v.updateView();
           new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() throws Exception {
                    // Simulate a long-running task
                    interpret(s);

                    System.out.println("Done");
                    Thread.sleep(1000);
                    return null;
                }

                @Override
                protected void done() {
                    // Close the loading screen and show the result
                    showTable();
                }
            }.execute();
        }        
        
    }
    private void showFirstPage() {
        v.firstPage();
        v.getGenerateButton().addActionListener(e -> updateView());
        initKey(v.p1GetInputField(), v.p1GetLabel());
        
        
    }
    public void showTable() {
        v.getFrame().remove(v.getP2());
        v.getP3().setT(m.tt);
        v.getP3().initTable();
        v.showTable(m.tt); 
        m.saveToCsv();
    }
    private void restart() {
        v.getFrame().remove(v.getP3());
        showFirstPage();   
    }

    public static void main(String[] args) {
        Controller c = new Controller(new View(), new Model());
        c.initView();      
        c.initController();
        
    }
            
}
