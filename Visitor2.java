import minipython.analysis.*;
import minipython.node.*;
import java.util.*;

public class Visitor2 extends DepthFirstAdapter {
    private SymbolTable symtable;

    Visitor2(SymbolTable symtable){


        this.symtable = symtable;
    }

    // 2o elegxos (klhsh mh dhlwmenhs synarthshs meros 2 , elegxoume an oi synarhthseis pou kalountai einai dhlwmenes)

    public void inAFunctionCall(AFunctionCall node)  {

        String funccallname = node.getId().toString();
        int line = ((TId) node.getId()).getLine();

        if(!symtable.getFunctions().containsKey(funccallname)){
            System.out.println("Line " + line + " : Function " + funccallname + " has not been declared.");
            return;
        }


        Function f = symtable.getFunctions().get(funccallname);

        

        int def_args = f.getDefaultArgs();
        int non_def_args = f.getNonDefaultArgs();






        // ARGUMENTS CHECK 


        // we want the function call to consist of , at least, non_def_arguments
        // and max non_def_args + def_args

        LinkedList arglist = node.getArglist();


        // function call has no arguments
        
        if(arglist.size() == 0) {

                int num_of_args = 0;

                if(num_of_args < non_def_args | num_of_args > non_def_args+ def_args){
                    System.out.println("Line " + line + " : Function " + funccallname + " has wrong number of parameters." + " Given : " + num_of_args + ". Required(at least) : " + non_def_args + ". Additionally: " + def_args + " more"); 
                }
        }



        else {


            AArglist argument_list = (AArglist)arglist.get(0);

            LinkedList arguments = argument_list.getCommaexpr();


            int num_of_args = arguments.size() + 1;

            if(num_of_args < non_def_args | num_of_args > non_def_args + def_args)
            System.out.println("Line " + line + " : Function " + funccallname + " has wrong number of parameters." + " Given : " + num_of_args + ". Required(at least) : " + non_def_args + ". Additionally: " + def_args + " more");
        }

        


    }

    // Edw kalyptontai oi parakatw elegxoi
    // Elegxos 5 (praxeis me None)
    // Elegxos 6 (Lathos tropos xrhshs synarthshs)

    // boolean variable gia na bgainei ENA sfalma me kathe anadromh.
    Boolean first_time = true;
    // DRY
    public void inArithmeticExpression(PExpression left,PExpression right,String operation){
        String left_type = get_simple_expression_type(left);

        String right_type = get_simple_expression_type(right);
        
        
        // Elegxos 5

        if (left_type.equals("none") || right_type.equals("none") ) {

            System.out.println(operation +  " Expression using none  ");
        }

        else if(left_type.equals("unknown") || right_type.equals("unknown") ){

            // if at least one variable is not declared (unknown) nothing is printed ,
            // as this error is catched by rule_check 1.
        }

        // Elegxos 6
        else if(!left_type.equals(right_type)  && first_time == true) {
            System.out.println(left_type);
            System.out.println(right_type);
            System.out.println(operation +  " Expression using variables of different types. " );
            first_time = false;
        }
    }


    // arxika xekiname me prosthesh
    public void inAAddExpression(AAddExpression node) {
        PExpression left_expression = node.getL();

        PExpression right_expression = node.getR();

        //int line =  node.getL().getLine();

        inArithmeticExpression(left_expression, right_expression, "Add");
        
    }


    // afairesh
    public void inASubExpression(ASubExpression node){
        PExpression left_expression = node.getL();

        PExpression right_expression = node.getR();

        //int line =  node.getL().getLine();
        inArithmeticExpression(left_expression, right_expression, "Sub");

    }

    // pollaplasiasmos
    public void inAMultExpression(AMultExpression node){
        PExpression left_expression = node.getL();

        PExpression right_expression = node.getR();

        //int line =  node.getL().getLine();
        inArithmeticExpression(left_expression, right_expression, "Mult");

    }

    // diairesh
    public void inADivExpression(ADivExpression node){
        PExpression left_expression = node.getL();

        PExpression right_expression = node.getR();

        //int line =  node.getL().getLine();
        inArithmeticExpression(left_expression, right_expression, "Div");
    }

    // Modulo
    public void inAModExpression(AModExpression node){
        PExpression left_expression = node.getL();

        PExpression right_expression = node.getR();

        //int line =  node.getL().getLine();
        inArithmeticExpression(left_expression, right_expression, "Mod");
    }

    // Power
    public void inAPowerExpression(APowerExpression node){
        PExpression left_expression = node.getL();

        PExpression right_expression = node.getR();

        //int line =  node.getL().getLine();
        inArithmeticExpression(left_expression, right_expression, "Power");
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

        else if(expression instanceof AValueExpression) {

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

            else if (value instanceof ACallonidValue) {


                ACallonidValue real_value = (ACallonidValue)value;

                PFunctionCall function_call = real_value.getFunctionCall();

                AFunctionCall real_function_call = (AFunctionCall)function_call;

                String funccallname = real_function_call.getId().toString();

                Function f = symtable.getFunctions().get(funccallname);

                expression_type = f.getReturns();



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



        else{ // case given expression is not simple
            // elegxoume kathe ypoperiptwsh expression me anadromh
            if (expression instanceof APowerExpression){
                APowerExpression newexp = (APowerExpression) expression;
                PExpression left = newexp.getL();
                PExpression right = newexp.getR();
                inArithmeticExpression(left, right, "Power");
            }
            else if (expression instanceof ADivExpression){
                ADivExpression newexp = (ADivExpression) expression;
                PExpression left = newexp.getL();
                PExpression right = newexp.getR();
                inArithmeticExpression(left, right, "Div");
            }
            else if (expression instanceof AModExpression){
                AModExpression newexp = (AModExpression) expression;
                PExpression left = newexp.getL();
                PExpression right = newexp.getR();
                inArithmeticExpression(left, right, "Mod");
            }
            else if (expression instanceof AMultExpression){
                AMultExpression newexp = (AMultExpression) expression;
                PExpression left = newexp.getL();
                PExpression right = newexp.getR();
                inArithmeticExpression(left, right, "Mult");
            }
            else if (expression instanceof AAddExpression){
                AAddExpression newexp = (AAddExpression) expression;
                PExpression left = newexp.getL();
                PExpression right = newexp.getR();
                inArithmeticExpression(left, right, "Add");
            }
            else if (expression instanceof ASubExpression){
                ASubExpression newexp = (ASubExpression) expression;
                PExpression left = newexp.getL();
                PExpression right = newexp.getR();
                inArithmeticExpression(left, right, "Sub");
            }
            
        }                        
            


        return expression_type;


    }

    public void MaxMinExpression(LinkedList<ACommavalue> ll, String functype, PValue fv){
        // lista pou kratame tous typous twn values
        LinkedList<String> types = new LinkedList<String>();
        String expression_type = "";

        for (int i = 0; i < ll.size(); i++){
            PValue value = ll.get(i).getValue();
            //PValue value = value_expression.getValue();
            
            if(value instanceof AStringValue){

                expression_type = "string";
            }

            else if (value instanceof ANumberValue) {
                expression_type = "integer";
            }

            else if (value instanceof ANoneValue) {
               
                expression_type = "none";
            }

            else if (value instanceof ACallonidValue) {


                ACallonidValue real_value = (ACallonidValue)value;

                PFunctionCall function_call = real_value.getFunctionCall();

                AFunctionCall real_function_call = (AFunctionCall)function_call;

                String funccallname = real_function_call.getId().toString();

                Function f = symtable.getFunctions().get(funccallname);

                expression_type = f.getReturns();



            }

            types.add(i,expression_type);
        }
        //PValue first_value = node.getValue();
        String first_type = "";
        if(fv instanceof AStringValue){

            first_type = "string";
        }

        else if (fv instanceof ANumberValue) {
            first_type = "integer";
        }

        else if (fv instanceof ANoneValue) {
           
            first_type = "none";
        }

        else if (fv instanceof ACallonidValue) {


            ACallonidValue real_value = (ACallonidValue)fv;

            PFunctionCall function_call = real_value.getFunctionCall();

            AFunctionCall real_function_call = (AFunctionCall)function_call;

            String funccallname = real_function_call.getId().toString();

            Function f = symtable.getFunctions().get(funccallname);

            first_type = f.getReturns();



        }
        for (int i = 0; i < types.size(); i++){
            String type = types.get(i);
            if(!type.equals(first_type)){
                System.out.println(functype + " function is using variables of different types.");
            }
        }
    }


    // periptwsh xrhshs twn synarthsewn Max min 
    public void inAMinExpression(AMinExpression node){
        LinkedList<ACommavalue> args = node.getCommavalue();
        PValue first_value = node.getValue();
        MaxMinExpression(args, "Min",first_value);
        
    }

    public void inAMaxExpression(AMaxExpression node){
        LinkedList<ACommavalue> args = node.getCommavalue();
        PValue first_value = node.getValue();
        MaxMinExpression(args, "Max",first_value);
    }

    /*
    // Epanalhspsh dhlwshs synarthshs me ton idio arithmo orismatwn (7os elegxos)
    public void inAFunction(AFunction node){

        String funcname = node.getId().toString();

        int line = ((TId) node.getId()).getLine();

        LinkedList args = node.getArgument();

        int current_non_def_args = 0;
        int current_def_args = 0;

        // elegxoume an exoume orismata

        if(!args.isEmpty()){
            AArgument real_arguments = (AArgument)args.get(0);

            // elegxos prwths parametrou

            AParam first_arg = (AParam)real_arguments.getParam();

            LinkedList value = first_arg.getValue(); // possible default value for first argument


            // if there is no value, we have 1 non default argument

            if(value.isEmpty()) {
                current_non_def_args++;
                
            }


            else if(!value.isEmpty()) {
                current_def_args++;

            }

            // elegxos twn ypoloipwn parametrwn (an yparxoun)

            LinkedList params = real_arguments.getCommaparam();

            int paramsize = params.size() + 1;

            if(!params.isEmpty()){

                for (int i=0; i < params.size(); i++) {


                    ACommaparam p = (ACommaparam)params.get(i);

                    AParam param  = (AParam)p.getParam();

                    //System.out.println(param.getId().toString());


                    LinkedList val = param.getValue(); // possible default value for first argument


                    // if there is no value, we have 1 non default argument

                    if(val.isEmpty()) {
                        current_non_def_args++;

                    }

                    // if there is a value, we have 1 default argument

                    else if(!val.isEmpty()) {
                        current_def_args++;

                    }

                }
                
            }

        }

        Function f = symtable.getFunctions().get(funcname);
        // elegxos na vgazei to error mia fora.
        if (f.getLineFound() == line){
            return;
        }

        int def_args = f.getDefaultArgs();
        int non_def_args = f.getNonDefaultArgs();

        
        // prwto if : elegxos an exoun idio arithmo parametrwn (2 kai 2 px, eite default eite non-default)
        if (current_non_def_args + current_def_args == def_args + non_def_args){
            System.out.println("Line : " + line + " Function " + funcname + "has already been declared.");
        }else{
            if (current_non_def_args == non_def_args){
                System.out.println("Line : " + line + " Function " + funcname + "has already been declared.");
            }
        }
    }

    */
}