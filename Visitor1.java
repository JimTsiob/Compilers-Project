import minipython.analysis.*;
import minipython.node.*;
import java.util.*;

import javax.crypto.interfaces.PBEKey;

public class Visitor1 extends DepthFirstAdapter{

    private Hashtable symtable;

    Visitor1(Hashtable symtable){
        this.symtable = symtable;
    }

    // Elegxos mh dhlwmenhs metavlhths (1os elegxos)

    // Elegxos periptwshs pou to id einai prin apo =.

    public void inAAssignStatement(AAssignStatement node){
        String assignname = node.getId().toString();
        int line = ((TId) node.getId()).getLine();
        if(!symtable.containsKey(assignname)){
            symtable.put(assignname,node);
        }
    }

    // Elegxos periptwshs pou to id einai argument se function.

    public void inAParam(AParam node){
        String paramname = node.getId().toString();
        int line = ((TId) node.getId()).getLine();
        if(!symtable.containsKey(paramname)){
            symtable.put(paramname,node);
        }
    }
    

    public void inAIdExpression(AIdExpression node){
        String idname = node.getId().toString();
        int line = ((TId) node.getId()).getLine();
        if(symtable.containsKey(idname)){
            symtable.put(idname,node);
        }else{
            System.out.println("Line " + line + " : variable " + idname + " has not been declared.");
        }
    }

    // Klhsh mh dhlwmenhs synarthshs (2os elegxos)

    public void inAFunction(AFunction node){
        String funcname = node.getId().toString();
        int line = ((TId) node.getId()).getLine();
        symtable.put(funcname,node);
        // synexeia sto 2o visitor.
    }


    // Lathos orismos orismatwn se klhsh synarthshs (3os elegxos)

    //public void 

    // Epanalhspsh dhlwshs synarthshs me ton idio arithmo orismatwn (7os elegxos)

}
