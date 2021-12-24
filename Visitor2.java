import minipython.analysis.*;
import minipython.node.*;
import java.util.*;

public class Visitor2 extends DepthFirstAdapter{
    private Hashtable symtable;

    Visitor2(Hashtable symtable){
        this.symtable = symtable;
    }

    // 2o elegxos (klhsh mh dhlwmenhs synarthshs meros 2 , elegxoume an oi synarhthseis pou kalountai einai dhlwmenes)

    public void inAFunctionCall(AFunctionCall node){
        String funccallname = node.getId().toString();
        int line = ((TId) node.getId()).getLine();
        if(!symtable.containsKey(funccallname)){
            System.out.println("Line " + line + " : Function " + funccallname + " has not been declared.");
        }
    }

    // 7os elegxos 
    public void inAFunction(AFunction node){
        //AFunction args = (AFunction) node.getArgument();
    }
}