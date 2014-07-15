package se.boberg.sets;

import se.boberg.sets.ast.Visitor;
import se.boberg.sets.ast.nodes.Difference;
import se.boberg.sets.ast.nodes.Intersection;
import se.boberg.sets.ast.nodes.Node;
import se.boberg.sets.ast.nodes.SetName;
import se.boberg.sets.ast.nodes.Union;

import com.google.common.base.Strings;

/**
 * Another implementation of the Visitor.
 * 
 * @see Interpreter
 */
public class TreePrinter<T> implements Visitor<Void> {
	
	private int indent;
	
	public void print(Node node) {
		indent = 0;
		node.accept(this);
	}

	@Override
	public Void visit(SetName node) {
		indent();
		System.out.println(node.getName());
		
		return null;
	}

	@Override
	public Void visit(Union node) {
		indent();
		System.out.println("Union(");
		indent++;
		node.getLeft().accept(this);
		node.getRight().accept(this);
		indent--;
		indent();
		System.out.println(")");
		
		return null;
	}

	@Override
	public Void visit(Intersection node) {
		indent();
		System.out.println("Intersection(");
		indent++;
		node.getLeft().accept(this);
		node.getRight().accept(this);
		indent--;
		indent();
		System.out.println(")");
		
		return null;
	}
	
	private void indent() {
		System.out.print(Strings.repeat(" ", indent * 2));
	}

    @Override
    public Void visit(Difference node) {
        indent();
        System.out.println("Difference(");
        indent++;
        node.getLeft().accept(this);
        node.getRight().accept(this);
        indent--;
        indent();
        System.out.println(")");
        
        return null;
    }
	
}
