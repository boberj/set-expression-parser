package se.boberg.sets.ast.nodes;

import se.boberg.sets.ast.Visitor;


public class Union extends SetOperation {
    
    public static final String OPERATOR = "|";

	public Union(Node left, Node right) {
		super(left, right);
	}
	
	@Override
    public String getOperator() {
        return OPERATOR;
    }

	@Override
	public <T> T accept(Visitor<T> visitor) {
		return visitor.visit(this);
	}

}
