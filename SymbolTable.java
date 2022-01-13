import java.util.*;

/**
 * This class is used for our symbol table.
 */
public class SymbolTable {

	// For storing functions mapped to their name.
	private HashMap<String, Function> functions;

	// For storing variables.
	private HashMap<String, Variable> variables;

	public SymbolTable() {
		this.functions = new HashMap<String, Function>();
		this.variables = new HashMap<String, Variable>();
	}

	public void addFunction(String key, Function func) {
		functions.put(key, func);
	}

	public void addVariable(String key, Variable varr) {
		this.variables.put(key, varr);
	}

	public HashMap<String, Function> getFunctions() {
		return this.functions;
	}

	public HashMap<String, Variable> getVariables() {
		return this.variables;
	}

}