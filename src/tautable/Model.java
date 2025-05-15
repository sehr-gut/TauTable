/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tautable;
import corefunctions.InterpreterHandler;
import corefunctions.TruthTable;
import javax.swing.JPanel;
/**
 *
 * @author Franklin Xam
 */
public class Model {
    final static InterpreterHandler ih = new InterpreterHandler();
 
    public TruthTable interpret(String propositions){
           return ih.interpret(propositions);
    }} 
