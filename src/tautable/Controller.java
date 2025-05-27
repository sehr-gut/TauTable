/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tautable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

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
    }
    private void interpret(String s) {
         m.interpret(s); // core functions
    }

    public void initView() {
        v.getFrame().setVisible(true);
 
    }
    
    private void initKey() {
        JTextField jTxtf= v.getP1().getInputField();
        JLabel  lab = v.getP1().getLabel();
        kh = new KeyHandler(jTxtf, lab); // this handles the key input 
                                         // and the displays
        jTxtf.addKeyListener(kh);
    }
    
    private void loadingView() {
        String s = v.p1GetInputField().getText();
        if(!kh.isHasError() && !s.equals("") && !s.equals("Input Here")) { 
            // this is to check if the input field is correct
           v.updateView(); // changes the loading screen
           new SwingWorker<Void, Void>() { // using swing worker to add a 
                                            //simple loading screen
                @Override
                protected Void doInBackground() throws Exception {
                    // Loading logic
                    interpret(s);
//                    System.out.println("Done");
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
    private void firstPage() { // restarting the 
        v.firstPage();
        v.getGenButton().addActionListener(e -> loadingView());
        initKey();
    }
    public void showTable() {
        v.showTable(m.tt); 
        v.getP3().getRestartButton().addActionListener(e -> restart());
        getSelectedPreviousInput(v.getP3().getjTable3());
        populatePrevious();
        m.saveToCsv();
    }
    public void populatePrevious() {
       m.readFromCsv();
       DefaultTableModel dtm = new DefaultTableModel() {
           @Override
           public boolean isCellEditable(int row, int column) {
               return false;
           }
       };
       dtm.setColumnIdentifiers(new Object[]{"previous Inputs"});
       for(String props: m.previousInputs) {
           dtm.insertRow(0, new Object[] {props});
       }
       
       v.getP3().getPrevList().setModel(dtm);
    }
    public void getSelectedPreviousInput(JTable t) {
        t.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e){
               int row = m.prevTables.size() - 1 - t.getSelectedRow();
               if(row >= 0) {
                   v.showTable(m.prevTables.get(row));
                   System.out.println("row: " + m.prevTables.get(row));
                   showTable();
                   v.getFrame().revalidate();
                   v.getFrame().repaint();
               }
            }
            @Override 
            public void mouseReleased(MouseEvent e) {
                
            }
        });
    }
    private void restart() {
        JFrame f = v.getFrame();
        f.remove(v.getP3());
        firstPage();   
        f.repaint();
        f.revalidate();
    }

    public static void main(String[] args) {
        Controller c = new Controller(new View(), new Model());
        c.firstPage();
        c.initView();
        c.initKey();
        
        
    }
            
}
