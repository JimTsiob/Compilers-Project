import java.io.*;
import minipython.lexer.Lexer;
import minipython.parser.Parser;
import minipython.node.*;
import java.util.*;

public class ParserTest
{
  public static void main(String[] args)
  {
    try
    {
      Parser parser =
        new Parser(
        new Lexer(
        new PushbackReader(
        new FileReader(args[0].toString()), 1024)));

    
    SymbolTable symtable = new SymbolTable();

     Start ast = parser.parse();
     ast.apply(new Visitor1(symtable));
     ast.apply(new Visitor2(symtable));
     /* Gia ton deutero visitor grapste thn entolh
      * ast.apply(new mysecondvisitor(symtable));
      */
    }
    catch (Exception e)
    {
      //System.err.println(e);
      e.printStackTrace();
    }
  }
}

