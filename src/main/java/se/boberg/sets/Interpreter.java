package se.boberg.sets;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.Set;

import se.boberg.sets.ast.Visitor;
import se.boberg.sets.ast.nodes.Difference;
import se.boberg.sets.ast.nodes.Intersection;
import se.boberg.sets.ast.nodes.Node;
import se.boberg.sets.ast.nodes.SetName;
import se.boberg.sets.ast.nodes.Union;
import se.boberg.sets.setproviders.SetProvider;

import com.google.common.collect.Sets;

/**
 * The interpreter operates on the generated AST and returns the resulting set.
 * 
 * This is only a demo implementation since it has a cubic complexity to the number of nodes in the tree.
 * See the comments on the methods in {@link Sets}.
 */
public class Interpreter<T> implements Visitor<Set<T>> {
	
	private final SetProvider<T> setProvider;
	
	public Interpreter(SetProvider<T> setProvider) {
        this.setProvider = setProvider;
    }
	
	public Set<T> interpret(Node node) {
		return node.accept(this);
	}

	@Override
	public Set<T> visit(SetName node) {
		final Set<T> set = setProvider.get(node.getName()); 
		
		checkArgument(set != null, "Unknown set: " + node.getName());
		
		return set;
	}
	
	@Override
	public Set<T> visit(Union node) {
		return Sets.union(node.getLeft().accept(this), node.getRight().accept(this));
	}

	@Override
	public Set<T> visit(Intersection node) {
		return Sets.intersection(node.getLeft().accept(this), node.getRight().accept(this));
	}

	@Override
    public Set<T> visit(Difference node) {
        return Sets.difference(node.getLeft().accept(this), node.getRight().accept(this));
    }

}
