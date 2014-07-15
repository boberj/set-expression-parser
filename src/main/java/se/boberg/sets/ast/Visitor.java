package se.boberg.sets.ast;

import se.boberg.sets.ast.nodes.Difference;
import se.boberg.sets.ast.nodes.Intersection;
import se.boberg.sets.ast.nodes.SetName;
import se.boberg.sets.ast.nodes.Union;

public interface Visitor<T> {
	
	T visit(SetName node);
	T visit(Union node);
	T visit(Intersection node);
    T visit(Difference node);
	
}
