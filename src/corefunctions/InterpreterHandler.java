/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package corefunctions;


import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Franklin Xam
 */
public class InterpreterHandler {
    // for the Interface between the model and the interpreter
    final Interpreter in = new Interpreter();
    TruthTable truthTable;
    ASTPrinter ap;

    
    Lexer lx;
    Parser parser;
    
    public void setTruthTable(TruthTable truthTable) { // getters and setters
        this.truthTable = truthTable;
    }

    public void setAp(ASTPrinter ap) {
        this.ap = ap;
    }
    public void setLx(Lexer lx) {
        this.lx = lx;
    }

    public void setParser(Parser parser) {
        this.parser = parser;
    }
    // called by the model
    public TruthTable interpret(String p) {
        setLx(new Lexer(p)); // sets a new Lexer
        setAp(new ASTPrinter()); // sets new ASTprinter
        lx.scan(); // starts the token scanning

        setParser(new Parser(lx.getTokens()));
        //  setting up the parser (above) parsing the tokens (below)
        Expression head = parser.parse();
        in.interpret(head); // calling the interpreter method
        
        // this is where the truth table creation starts
        
        ap.print(head); // this outputs the propositions used
        List<String> props = ap.getPropositions(); 
        List<String> result =  in.results;
        List<String> finalProps = new ArrayList<>();
        List<String> finalResults = new ArrayList<>();
        // why does this work?
        // the interpreter and the ast printer uses the same AST for generating
        // their outputs. ensuring the same length and positions for the 
        // output proposition and results
        
        
        for(int i = 0; i < props.size(); i++) {
            // setting up a truth table
            String prop = props.get(i).trim();
            String res = result.get(i);
            if(!finalProps.contains(prop)) {
               finalProps.add(prop);
               finalResults.add(res);
            }
        }        
        truthTable = new TruthTable(in.n);
        truthTable.generateTable(finalProps, finalResults);
        
        return truthTable;
    }

    public static void error(Token t, String message) {
        
    }
}
