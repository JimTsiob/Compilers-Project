import java.util.*;

/**
 * Every instance of this class represents a function that was found in the
 * program.
 */
public class Function {

	private String name;

	private int non_default_args;

	private int default_args;

	private int lineFound;

	private String returns;

	public Function() {
		this.name = "";
		this.non_default_args = 0;
		this.default_args = 0;
		this.lineFound = 0;
		this.returns = "";
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setNonDefaultArgs(int args) {
		this.non_default_args = args;
	}

	public int getNonDefaultArgs() {
		return this.non_default_args;
	}

	public void setDefaultArgs(int args) {
		this.default_args = args;
	}

	public int getDefaultArgs() {
		return this.default_args;
	}

	public void setLineFound(int line) {
		this.lineFound = line;
	}

	public int getLineFound() {
		return this.lineFound;
	}

	public void setReturns(String type) {
		this.returns = type;
	}

	public String getReturns() {
		return this.returns;
	}

}