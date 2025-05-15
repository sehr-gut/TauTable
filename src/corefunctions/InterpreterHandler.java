/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package corefunctions;


import static corefunctions.Main.interpreter;
import java.util.HashMap;
import java.util.List;
/**
 *
 * @author Franklin Xam
 */
public class InterpreterHandler {
    static final Interpreter i = new Interpreter();
    TruthTable truthTable;
    ASTPrinter ap;

    
    Lexer lx;
    Parser parser;
    
    public void setTruthTable(TruthTable truthTable) {
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
    public TruthTable interpret(String p) {
        setLx(new Lexer(p));
        setAp(new ASTPrinter());
        lx.scan();
        for (Token n : lx.getTokens()) {
            System.out.println(n);
        }
        System.out.println("Balls");
        setParser(new Parser(lx.getTokens()));
        Expression head = parser.parse();
        i.interpret(head);
        ap.print(head);
        setTruthTable(new TruthTable());
        truthTable.setn(interpreter.n);
        truthTable.generateTable(ap.getPropositions(), interpreter.results);
        return truthTable;
    }

    public static void error(Token t, String message) {
        
    }
}
