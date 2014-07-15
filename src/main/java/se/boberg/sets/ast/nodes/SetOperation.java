package se.boberg.sets.ast.nodes;


public abstract class SetOperation extends Node {
    
    private final Node left;
    private final Node right;
    
    public SetOperation(Node left, Node right) {
        this.left = left;
        this.right = right;
    }
    
    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }
    
    public abstract String getOperator();
    
    @Override
    public String toString() {
        return "(" + getLeft() + getOperator() + getRight() + ")";
    }

}
