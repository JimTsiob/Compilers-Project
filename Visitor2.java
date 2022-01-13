import minipython.analysis.*;
import minipython.node.*;
import java.util.*;

public class Visitor2 extends DepthFirstAdapter {
    private SymbolTable symtable;

    Visitor2(SymbolTable symtable) {

        this.symtable = symtable;
    }

    // 2o elegxos (klhsh mh dhlwmenhs synarthshs meros 2 , elegxoume an oi
    // synarhthseis pou kalountai einai dhlwmenes)

    public void inAFunctionCall(AFunctionCall node) {

        String funccallname = node.getId().toString();
        int line = ((TId) node.getId()).getLine();
        int real_line = (line + 1 )/ 2 ;

        if (!symtable.getFunctions().containsKey(funccallname)) {
            System.out.println("Line " + real_line + " : Function " + funccallname + " has not been declared.");
            return;
        }

        Function f = symtable.getFunctions().get(funccallname);

        int def_args = f.getDefaultArgs();
        int non_def_args = f.getNonDefaultArgs();

        // ARGUMENTS CHECK

        // we want the function call to consist of , at least, non_def_arguments
        // and max non_def_args + def_args

        LinkedList arglist = node.getArglist();

        //System.out.println("Sto func call exei: " + arglist.size());

        // function call has no arguments

        if (arglist.size() == 0) {

            int num_of_args = 0;


            if (num_of_args < non_def_args | num_of_args > non_def_args + def_args) {
                System.out.println("Line " + real_line + " : Function " + funccallname + " has wrong number of parameters."
                        + " Given : " + num_of_args + ". Required(at least) : " + non_def_args + ". Additionally: "
                        + def_args + " more");
            }
        }



        else {

            //System.out.println("`Mpainei edo");


            AArglist argument_list = (AArglist) arglist.get(0);


            //PExpression first_expr = argument_list.getExpression();

           

            LinkedList arguments = argument_list.getCommaexpr();

            //System.out.println("Commaexpr has:  " + arguments.size());

            int num_of_args = arguments.size() + 1;

            //System.out.println("Sto func call exei: " + num_of_args);

            if (num_of_args < non_def_args | num_of_args > non_def_args + def_args)
                System.out.println("Line " + real_line + " : Function " + funccallname + " has wrong number of parameters."
                        + " Given : " + num_of_args + ". Required(at least) : " + non_def_args + ". Additionally: "
                        + def_args + " more");
        }

 

        //////////////////////////////////////////
        // WHEN FUNCTION THAT IS CALLED RETURNS AN OPERATION (ADD, MULT , ...)
        // THE GIVEN PARAMETERS GET CHECKED IF THEY ARE OF THE SAME TYPE
        // ELEGXOS 4


        String return_type = f.getReturns();

        if (return_type.equals("operation") && arglist.size() != 0 ) {

            //check given parameters type

            AArglist argument_list = (AArglist) arglist.get(0);


            PExpression first_arg = argument_list.getExpression();

            HashMap first_arg_type = get_simple_expression_type(first_arg);



            LinkedList arguments = argument_list.getCommaexpr(); 


            for (int i = 0; i < arguments.size(); i++)  {

                ACommaexpr arg = (ACommaexpr)arguments.get(i);

                PExpression real_arg = arg.getExpression();

                HashMap arg_type = get_simple_expression_type(real_arg);

                first_arg_type.putAll(arg_type);

            }


            first_arg_type.remove("real_line");

            if (first_arg_type.containsKey("none")) {
                System.out.println("Line " + real_line + " : function_call using none argument ");
                }
                else if (first_arg_type.containsKey("string") && first_arg_type.containsKey("integer") ) {
                //System.out.println(types);
                System.out.println("Line " + real_line + " : function_call using wrong type arguments ");
            }

            
        }

    }




    public void inAAddExpression(AAddExpression node) {
        evaluateExpressions(node, "add +");
    }

    // afairesh
    public void inASubExpression(ASubExpression node) {
        evaluateExpressions(node, "sub -");

        //System.out.println("tora vgainei apo to sub ");

    }

    // pollaplasiasmos
    public void inAMultExpression(AMultExpression node) {
        evaluateExpressions(node, "mult *");

        //System.out.println("tora vgainei apo to mult ");

    }

    // diairesh
    public void inADivExpression(ADivExpression node) {
        evaluateExpressions(node, "div /");
    }

    // Modulo
    public void inAModExpression(AModExpression node) {
        evaluateExpressions(node, "mod %");
    }

    // Power
    public void inAPowerExpression(APowerExpression node) {
        evaluateExpressions(node, "power **");
    }


   static int current_line = 0;

   

   //static boolean none = false;

    public void evaluateExpressions(PExpression expression, String operation) {


        HashMap<String, Integer> types = get_simple_expression_type(expression);

        // pop the real_line key to get the expression line

        int expression_line;

        try {

             expression_line = types.get("real_line");

        }

        catch(NullPointerException e){

            expression_line = 0;


            System.out.println("Line " + expression_line + " : expression is full of none values (wrong line when expression is full of nones!)");
            return;
        }


        if(current_line != expression_line) {

            current_line = expression_line;


            types.remove("real_line");



            if (types.containsKey("none")) {

                System.out.println("Line " + expression_line + " : expression  has none value ");
                //none = true;

            }


      

        

            else if (types.size() > 1  && (types.containsKey("string") || types.containsKey("integer"))){ //|| types.containsKey("operation"))) {

                //System.out.println(types);
                System.out.println("Line " + expression_line + " : expression using wrong value types ");
                }

            }


    }

    public HashMap<String, Integer> get_simple_expression_type(PExpression expression)  {



        HashMap<String, Integer> evaluations = new HashMap<String, Integer>();


        // check if given expression is Id : 

        if (expression instanceof AIdExpression) {


            String id_name = ((AIdExpression) expression).getId().toString();

            int line = ((AIdExpression) expression).getId().getLine();

            int real_line = (line + 1 )/ 2 ;

            //System.out.println(real_line);

            if (!symtable.getVariables().containsKey(id_name)) {
                evaluations.put("unknown", 0);

                // maybe there is no need for action , because in this case check no 1 throws an error
            } 

            else {

                Variable id = symtable.getVariables().get(id_name);

                evaluations.put(id.getType(), 0);

                evaluations.put("real_line", real_line);
            }

            return evaluations;
        } 





        // check if given expression is Value (string,number,none,id.function_call)

        else if (expression instanceof AValueExpression) {


            AValueExpression value_expression = (AValueExpression) expression;


            PValue value = value_expression.getValue();


            if (value instanceof AStringValue) {


                AStringValue real_value = (AStringValue)value;

                TString string = real_value.getString();

                int line = string.getLine();

                int real_line = (line + 1 )/ 2 ;


                evaluations.put("string", 0);

                evaluations.put("real_line", real_line);

            } 

            else if (value instanceof ANumberValue) {


                ANumberValue real_value = (ANumberValue)value;

                TNumber number = real_value.getNumber();

                int line = number.getLine();

                int real_line = (line + 1 )/ 2 ;


                evaluations.put("integer", 0);

                evaluations.put("real_line", real_line);

            } 

            else if (value instanceof ANoneValue) {

                // nonevalue has no token to get line

                evaluations.put("none", 0);

            } 

            else if (value instanceof ACallonidValue) {


                ACallonidValue real_value = (ACallonidValue) value;

                TId id = real_value.getId();

                int line = id.getLine();

                int real_line = (line + 1 )/ 2 ;


                PFunctionCall function_call = real_value.getFunctionCall();

                AFunctionCall real_function_call = (AFunctionCall) function_call;

                String funccallname = real_function_call.getId().toString();

                Function f = symtable.getFunctions().get(funccallname);


                evaluations.put(f.getReturns(), 0);

                evaluations.put("real_line", real_line);


            }


            return evaluations;
        } 



        // check if given expression is a function call


        else if (expression instanceof AFunctioncallExpression) {


            AFunctioncallExpression function_call_expression = (AFunctioncallExpression) expression;

            PFunctionCall function_call = function_call_expression.getFunctionCall();

            AFunctionCall real_function_call = (AFunctionCall) function_call;

            TId id = real_function_call.getId();

            int line = id.getLine();

            int real_line = (line + 1 )/ 2 ;

            String funccallname = real_function_call.getId().toString();

            Function f = symtable.getFunctions().get(funccallname);

            String return_type = f.getReturns();


            ///////////////////////////////////////////////


            LinkedList arglist = real_function_call.getArglist();


            if (arglist.size() != 0 && return_type.equals("operation")) {

            



                AArglist argument_list = (AArglist) arglist.get(0);

                PExpression first_arg = argument_list.getExpression();

                HashMap first_arg_type = get_simple_expression_type(first_arg);

                first_arg_type.remove("real_line");

            


                //System.out.println(return_type);

        


                Optional<String> firstKey = first_arg_type.keySet().stream().findFirst();
            
                if (firstKey.isPresent()) {

                    //System.out.println("Mpainei edo");

                    return_type = firstKey.get();

                    //System.out.println(return_type);
                
                }

            ///////////////////////////////////////

            }

            evaluations.put(return_type, 0);

            

            evaluations.put("real_line", real_line);

            //System.out.println(evaluations.keySet());



            return evaluations;

        } 


            // case given expression is not simple
                 // elegxoume kathe ypoperiptwsh expression me anadromh

        else { 


            if (expression instanceof APowerExpression) {

                APowerExpression newexp = (APowerExpression) expression;
                PExpression left = newexp.getL();
                PExpression right = newexp.getR();
                evaluations.putAll(get_simple_expression_type(left));
                evaluations.putAll(get_simple_expression_type(right));

            } 

            else if (expression instanceof ADivExpression) {

                ADivExpression newexp = (ADivExpression) expression;
                PExpression left = newexp.getL();
                PExpression right = newexp.getR();
                evaluations.putAll(get_simple_expression_type(left));
                evaluations.putAll(get_simple_expression_type(right));

            } 

            else if (expression instanceof AModExpression) {
                AModExpression newexp = (AModExpression) expression;
                PExpression left = newexp.getL();
                PExpression right = newexp.getR();
                evaluations.putAll(get_simple_expression_type(left));
                evaluations.putAll(get_simple_expression_type(right));
            } 

            else if (expression instanceof AMultExpression) {
                AMultExpression newexp = (AMultExpression) expression;
                PExpression left = newexp.getL();
                PExpression right = newexp.getR();
                evaluations.putAll(get_simple_expression_type(left));
                evaluations.putAll(get_simple_expression_type(right));
            } 

            else if (expression instanceof AAddExpression) {
                AAddExpression newexp = (AAddExpression) expression;
                PExpression left = newexp.getL();
                PExpression right = newexp.getR();
                evaluations.putAll(get_simple_expression_type(left));
                evaluations.putAll(get_simple_expression_type(right));
            } 

            else if (expression instanceof ASubExpression) {
                ASubExpression newexp = (ASubExpression) expression;
                PExpression left = newexp.getL();
                PExpression right = newexp.getR();
                evaluations.putAll(get_simple_expression_type(left));
                evaluations.putAll(get_simple_expression_type(right));
            }

            return evaluations;
        }


    }




    public void MaxMinExpression(LinkedList<ACommavalue> ll, String functype, PValue fv) {
        // lista pou kratame tous typous twn values
        LinkedList<String> types = new LinkedList<String>();
        String expression_type = "";
        int real_line = 0;
        boolean flag = true; // gia na vgazei ta sfalmata mia fora.
        for (int i = 0; i < ll.size(); i++) {
            PValue value = ll.get(i).getValue();
            // PValue value = value_expression.getValue();

            if (value instanceof AStringValue) {
                expression_type = "string";
                AStringValue v = (AStringValue) value;
                TString str = v.getString();
                int line = str.getLine();
                real_line = (line + 1)/2;
            }

            else if (value instanceof ANumberValue) {
                expression_type = "integer";
                ANumberValue v = (ANumberValue) value;
                TNumber num = v.getNumber();
                int line = num.getLine();
                real_line = (line + 1)/2;
            }

            else if (value instanceof ANoneValue) {
                expression_type = "none";
            }

            else if (value instanceof ACallonidValue) {

                ACallonidValue real_value = (ACallonidValue) value;

                PFunctionCall function_call = real_value.getFunctionCall();

                AFunctionCall real_function_call = (AFunctionCall) function_call;

                String funccallname = real_function_call.getId().toString();

                Function f = symtable.getFunctions().get(funccallname);

                expression_type = f.getReturns();

                int line = real_function_call.getId().getLine();

                real_line = (line + 1) / 2;
            }

            types.add(i, expression_type);
        }
        // PValue first_value = node.getValue();
        String first_type = "";
        if (fv instanceof AStringValue) {
            first_type = "string";
            AStringValue v = (AStringValue) fv;
            TString str = v.getString();
            int line = str.getLine();
            real_line = (line + 1)/2;
        }

        else if (fv instanceof ANumberValue) {
            first_type = "integer";
            ANumberValue v = (ANumberValue) fv;
            TNumber num = v.getNumber();
            int line = num.getLine();
            real_line = (line + 1)/2;
        }

        else if (fv instanceof ANoneValue) {
            first_type = "none";
        }

        else if (fv instanceof ACallonidValue) {

            ACallonidValue real_value = (ACallonidValue) fv;

            PFunctionCall function_call = real_value.getFunctionCall();

            AFunctionCall real_function_call = (AFunctionCall) function_call;

            String funccallname = real_function_call.getId().toString();

            Function f = symtable.getFunctions().get(funccallname);

            first_type = f.getReturns();

            int line = real_function_call.getId().getLine();

            real_line = (line + 1) / 2;

        }
        for (int i = 0; i < types.size(); i++) {
            String type = types.get(i);
            if (!type.equals(first_type) && flag) {
                System.out.println("Line " + real_line + " : " + functype + " function is using variables of different types.");
                flag = false;
            }
        }
    }

    // periptwsh xrhshs twn synarthsewn Max min
    public void inAMinExpression(AMinExpression node) {
        LinkedList<ACommavalue> args = node.getCommavalue();
        PValue first_value = node.getValue();
        MaxMinExpression(args, "Min", first_value);
    }

    public void inAMaxExpression(AMaxExpression node) {
        LinkedList<ACommavalue> args = node.getCommavalue();
        PValue first_value = node.getValue();
        MaxMinExpression(args, "Max", first_value);
    }


}
