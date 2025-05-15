/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tautable;
import corefunctions.TruthTable;
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
    TruthTable t;
    KeyHandler kh;
    
    public Controller(View view, Model model) {
        m = model;
        v = view;
        initKey(v.p1GetInputField(), v.p1GetLabel());
    }
    public void interpret(String s) {
        Thread task = new Thread(new Runnable(){
            @Override
            public void run() {
                t = m.interpret(s);
            }
        });
        task.start();
    }
    public void initController() {
        v.getRB1().addActionListener(e -> updateView());
    }
    public void initView() {
        v.showView();
        
    }
    public void updateView() {
        v.updateView();
        String s = v.p1GetInputField().getText();

        if(!s.equals("")) {
            
        }
        new SwingWorker<Void, Void>() {
            @Override
                protected Void doInBackground() throws Exception {
                    // Simulate a long-running task
                    interpret(s);
                    Thread.sleep(1000);
                    return null;
                }

                @Override
                protected void done() {
                    // Close the loading screen and show the result
                    v.getFrame().remove(v.getP2());
                    v.showTable(t);
                }
        }.execute();
        
    }
    public void showTable() {
        v.showTable(t);
        
    }

    private void initKey(JTextField jTxtf, JLabel lab) {
        kh = new KeyHandler(jTxtf, lab);
        jTxtf.addKeyListener(kh);
    }
    public static void main(String[] args) {
        Controller c = new Controller(new View(), new Model());
        c.initView();
        c.initController();
        
    }
            
}
