import minipython.analysis.*;
import minipython.node.*;
import java.util.*;

import javax.crypto.interfaces.PBEKey;

public class Visitor1 extends DepthFirstAdapter {

    private SymbolTable symtable;

    Visitor1(SymbolTable symtable)  {

        this.symtable = symtable;

    }

    // Elegxos mh dhlwmenhs metavlhths (1os elegxos)

    // Elegxos periptwshs pou to id einai prin apo =.

    public void inAAssignStatement(AAssignStatement node) {

        String assignname = node.getId().toString();
        int line = ((TId) node.getId()).getLine();

        Variable var = new Variable();
        var.setName(assignname);
        var.setLineFound(line);

        // set variable type according to expression being assigned

        

        PExpression expression = node.getExpression();

        if (expression instanceof AValueExpression) {

            AValueExpression value_expression = (AValueExpression)expression;

            PValue value = value_expression.getValue();

            if(value instanceof AStringValue){

                var.setType("string");
                }

            else if (value instanceof ANumberValue) {
                var.setType("integer");
            }

            else if (value instanceof ANoneValue) {
                var.setType("none");
            }
        

        }

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

    

   



    

    

    // Check every new function declaration
    // Take its name , num of default args , num of non default args
    // Then check if there is other func with the same name in the symbol table
    // with same num of parameters.
    // If so , dont add this func to symbol table and print error (Elegxos 7)


    public void inAFunction(AFunction node) {

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


        // check for return types


        PStatement statement = node.getStatement();


        if(statement instanceof AReturnStatement ) {


            AReturnStatement return_statement = (AReturnStatement) statement;

            PExpression return_expression = return_statement.getExpression();


            String expression_type = get_simple_expression_type(return_expression);


            if(!expression_type.equals("not_simple")){

                f.setReturns(expression_type);
            }


            else{   // if return statement is not simple, e.g. return x+y


            }



        }



        // if function statement is not a return statement , set function return type
        // as NONE

        else {


            f.setReturns("none");

        }



        

        /////////////////////////////////////////////////////////

        boolean add = true;

        if(symtable.getFunctions().containsKey(funcname))  {

            Function f_2 = symtable.getFunctions().get(funcname);


            int def_args = f_2.getDefaultArgs();
            int non_def_args = f_2.getNonDefaultArgs();


            if (f.getDefaultArgs() + f.getNonDefaultArgs() == def_args + non_def_args) {

                System.out.println("Line : " + line + " Function " + funcname + "has already been declared.");

                add = false;

                    }

            else  {

                if (f.getNonDefaultArgs() == non_def_args) {
                    System.out.println("Line : " + line + " Function " + funcname + "has already been declared.");
                    
                    add = false;

                    }


            }


        }


        if(add) {
            symtable.addFunction(funcname,f);
            }
        // synexeia sto 2o visitor.

        // Epanalhspsh dhlwshs synarthshs me ton idio arithmo orismatwn (7os elegxos)
        
        

    }


    public String get_simple_expression_type(PExpression expression) {


        String expression_type = " ";

        if(expression instanceof AIdExpression){

            String id_name = ( (AIdExpression) expression).getId().toString();

            if(!symtable.getVariables().containsKey(id_name)) {

                expression_type = "unknown";

                
            }

            else {

            Variable id = symtable.getVariables().get(id_name);

            expression_type = id.getType();

            }

        }

        else if(expression instanceof AValueExpression){

            AValueExpression value_expression = (AValueExpression)expression;

            PValue value = value_expression.getValue();


            if(value instanceof AStringValue){

                expression_type = "string";
            }

            else if (value instanceof ANumberValue) {
                expression_type = "integer";
            }

            else if (value instanceof ANoneValue) {
               
                expression_type = "none";
            }


           


        }

        else if(expression instanceof AFunctioncallExpression){

            AFunctioncallExpression function_call_expression = (AFunctioncallExpression)expression;

            PFunctionCall function_call = function_call_expression.getFunctionCall();

            AFunctionCall real_function_call = (AFunctionCall)function_call;

            String funccallname = real_function_call.getId().toString();

            Function f = symtable.getFunctions().get(funccallname);

            expression_type = f.getReturns();

        }



        else                        // case given expression is not simple
            expression_type = "not_simple";


        return expression_type;


    }

    




}
