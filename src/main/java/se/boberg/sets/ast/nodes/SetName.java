package se.boberg.sets.ast.nodes;

import se.boberg.sets.ast.Visitor;

public class SetName extends Node {
	
	private final String name;
	
	public SetName(final String name) {
		this.name = name;
	}
	
	public String getName() {
	    return name;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	@Override
	public <T> T accept(Visitor<T> visitor) {
		return visitor.visit(this);
	}
	
}
