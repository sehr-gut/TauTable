package corefunctions;

// this class is for the token
// more reminicent of structs from C or C++
// than OOP class
public class Token {
    public Token(String le, TokenType t, int pos) {
        lexeme = le;
        type = t;
        this.pos = pos;
    }

    final String lexeme;
    final TokenType type;
    final int pos;

    public String toString() {
        return String.format("Token\nLexeme: %s\nType: %s\n", 
                           lexeme, type);
    }
}