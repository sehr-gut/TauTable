package corefunctions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Main {
    final static Interpreter interpreter = new Interpreter();
    
    public static void main(String [] args) throws IOException {
        if(args.length == 1) {
            runFile(args[0]);
        } else if (args.length == 0) {
            run();
        } else {
            System.out.println("Illegal usage of args");
            System.exit(1);
        }
        

    } 
    public static void run() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        
        while(true) {
            System.out.print(">> ");
            String a = reader.readLine();
            if(a.equals("exit")) System.exit(0);
            doThis(a);
        }
    }
    public static void doThis(String source) {
        Lexer l = new Lexer(source);
        l.scan();
        Parser parser = new Parser(l.tokens);
        Expression head = parser.parse();
        System.out.println(interpreter.interpret(head));
        System.out.println(interpreter.results.toString());
        TruthTable truthTable = new TruthTable();
        ASTPrinter ap = new ASTPrinter();
        ap.print(head);
        List<String> props = ap.getPropositions();
        List<String> results = interpreter.results;
        
    }

    
    public static void runFile(String path) throws IOException {
        byte [] bytes = Files.readAllBytes(Paths.get(path));
        String program = new String(bytes, Charset.defaultCharset());
        doThis(program);
    }

}