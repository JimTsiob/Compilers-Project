import minipython.analysis.*;
import minipython.node.*;
import java.util.*;

public class Visitor2 extends DepthFirstAdapter{
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
}