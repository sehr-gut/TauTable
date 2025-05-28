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

// this uses MVC pattern in handling classes and UI
public class Controller {
    // The c of the design pattern
    private Model m;
    private View v;
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
                    m.saveToCsv();
                }
            }.execute();
        }          
    }
    public void firstPage() { // restarting the 
        v.firstPage();
        v.getGenButton().addActionListener(e -> loadingView());
        initKey();
    }
    public void showTable() { // showing table 
        v.showTable(m.tt); 
        v.getP3().getRestartButton().addActionListener(e -> restart());
            // for the actionlistener
        getSelectedPreviousInput(v.getP3().getjTable3()); // for 
            // mouselistener of the previous tables
        populatePrevious(); // gathers the previous tables generated
    }
    public void populatePrevious() {
       m.readFromCsv(); // reading from csv
       DefaultTableModel dtm = new DefaultTableModel() {
           @Override
           public boolean isCellEditable(int row, int column) {
               return false;
           }
       }; // creating an uneditable table for the previous tables
       dtm.setColumnIdentifiers(new Object[]{"previous Inputs"}); 
        // table generation
       for(String props: m.previousInputs) {
           dtm.insertRow(0, new Object[] {props});
       }
       // setting panel 3 jtable to current model
       v.getP3().getPrevList().setModel(dtm);
    }
    public void getSelectedPreviousInput(JTable t) {
        // for the mouse listener handler
        t.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e){
               int row = m.prevTables.size() - 1 - t.getSelectedRow();
               if(row >= 0 && m.prevTables.size() > 0) {
                   System.out.println("row: " + m.prevTables.get(row));
                   m.tt = m.prevTables.get(row);
                   showTable();
                   v.getFrame().revalidate();
                   v.getFrame().repaint();
               }
            }
        });
    }
    private void restart() { // for inputing new propositions
        JFrame f = v.getFrame();
        f.remove(v.getP3());
        firstPage();   
        f.repaint();
        f.revalidate();
    }

    public static void main(String[] args) { // main method
        Controller c = new Controller(new View(), new Model());
        c.firstPage();
        c.initView();
        c.initKey();
        
        
    }
            
}
