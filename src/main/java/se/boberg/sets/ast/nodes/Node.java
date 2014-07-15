package se.boberg.sets.ast.nodes;

import se.boberg.sets.ast.Visitor;

public abstract class Node  {

    public abstract <T> T accept(Visitor<T> visitor);

}
