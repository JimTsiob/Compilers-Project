/**
 * Every instance of this class represents a variable that was found in the
 * program.
 */
public class Variable {

	private String name;

	private String type;

	private int lineFound;

	public Variable() {
		this.name = "";
		this.type = "";
		this.lineFound = -1;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return this.type;
	}

	public void setLineFound(int line) {
		this.lineFound = line;
	}

	public int getLineFound() {
		return this.lineFound;
	}
}