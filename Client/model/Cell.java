package model;

public class Cell {
	private boolean visited;
	private String value;
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public boolean isVisited() {
		return visited;
	}
	public void setVisited(boolean visited) {
		this.visited = visited;
	}
	
	public Cell() {
		this.value = "No";
		this.visited = false;
	}
	

}
