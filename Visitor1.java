import minipython.analysis.*;
import minipython.node.*;
import java.util.*;

import javax.crypto.interfaces.PBEKey;

public class Visitor1 extends DepthFirstAdapter{

    private SymbolTable symtable;

    Visitor1(SymbolTable symtable)  {

        this.symtable = symtable;

    }

    // Elegxos mh dhlwmenhs metavlhths (1os elegxos)

    // Elegxos periptwshs pou to id einai prin apo =.

    public void inAAssignStatement(AAssignStatement node){

        String assignname = node.getId().toString();
        int line = ((TId) node.getId()).getLine();

        Variable var = new Variable();
        var.setName(assignname);
        var.setLineFound(line);

        // variable type missing, var.setType


        if(!symtable.getVariables().containsKey(assignname)){
            symtable.addVariable(assignname,var);
        }
    }

    // Elegxos periptwshs pou to id einai argument se function.


    public void inAParam(AParam node){

        String paramname = node.getId().toString();
        int line = ((TId) node.getId()).getLine();

        Variable var = new Variable();
        var.setName(paramname);
        var.setLineFound(line);

        // variable type missing, var.setType

        if(!symtable.getVariables().containsKey(paramname)){
            symtable.addVariable(paramname,var);
        }
    }
    

    

    public void inAIdExpression(AIdExpression node){

        String idname = node.getId().toString();
        int line = ((TId) node.getId()).getLine();



        /* if(symtable.containsKey(idname)){
            symtable.put(idname,node);
        }
        */

        if(!symtable.getVariables().containsKey(idname) ) {

            System.out.println("Line " + line + " : variable " + idname + " has not been declared.");
        }
    }

    

    // Klhsh mh dhlwmenhs synarthshs (2os elegxos)

    public void inAFunction(AFunction node){

        Function f = new Function();

        String funcname = node.getId().toString();

        int line = ((TId) node.getId()).getLine();


        f.setName(funcname);
        f.setLineFound(line);


        LinkedList arguments = node.getArgument();

        //System.out.println("Arguments size for function " + funcname + "is " + arguments.size() );

        if(arguments.size() == 0) {

            // Function with no arguments
            f.setNonDefaultArgs(0);
            f.setDefaultArgs(0);

        }


        else {      // if arguments.size() == 1 , function has 1 or more arguments



            AArgument real_arguments = (AArgument)arguments.get(0);


            AParam first_arg = (AParam)real_arguments.getParam();



            //System.out.println(first_arg.toString());


            // possibly needed to append first_arg to arguments LinkedList for Function



            LinkedList value = first_arg.getValue(); // possible default value for first argument


            // if there is no value, we have 1 non default argument

            if(value.isEmpty()) {
                f.setNonDefaultArgs(f.getNonDefaultArgs() + 1 );

            }


            else if(!value.isEmpty()) {
                f.setDefaultArgs(f.getDefaultArgs() + 1 );

            }




            // next check for the other parameters if exist


            LinkedList next_args = real_arguments.getCommaparam();


            if(!next_args.isEmpty())   {


                for(int i=0; i< next_args.size() ; i++) {


                    ACommaparam p = (ACommaparam)next_args.get(i);

                    AParam param  = (AParam)p.getParam();

                    //System.out.println(param.getId().toString());


                    LinkedList val = param.getValue(); // possible default value for first argument


                    // if there is no value, we have 1 non default argument

                    if(val.isEmpty()) {
                        f.setNonDefaultArgs(f.getNonDefaultArgs() + 1 );

                    }

                    // if there is a value, we have 1 default argument

                    else if(!val.isEmpty()) {
                        f.setDefaultArgs(f.getDefaultArgs() + 1 );

                    }

            }


        }


        }




        symtable.addFunction(funcname,f);
        // synexeia sto 2o visitor.

        // Epanalhspsh dhlwshs synarthshs me ton idio arithmo orismatwn (7os elegxos)
        
        

    }

    

    

}
