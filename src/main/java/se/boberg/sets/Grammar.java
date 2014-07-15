package se.boberg.sets;

import org.parboiled.BaseParser;
import org.parboiled.Rule;

import se.boberg.sets.ast.nodes.Difference;
import se.boberg.sets.ast.nodes.Intersection;
import se.boberg.sets.ast.nodes.Node;
import se.boberg.sets.ast.nodes.SetName;
import se.boberg.sets.ast.nodes.Union;


/**
 * Model <- Expression <EOI>
 * Expression <- UnionOrIntersection ('-' UnionOrIntersection)*
 * UnionOrIntersection <- Primary (('|' / '&') Primary)*
 * Primary <- SetName | ('(' Expression ')')
 * SetName <- [a..z]+
 * 
 * See http://blog.efftinge.de/2010/08/parsing-expressions-with-xtext.html for how to implement precedence.
 */
class Grammar extends BaseParser<Node> {
	
	/**
	 * Expression <EOI>
	 */
    public Rule Model() {
    	// EOI makes sure we try to parse the whole line instead of returning prematurely on mismatch
        return Sequence(Expression(), EOI);
    }
    
    /**
     * UnionOrIntersection ('-' UnionOrIntersection)*
     */
    Rule Expression() {
        return Sequence(
    		UnionOrIntersection(),
            ZeroOrMore(
                Difference.OPERATOR, 
                UnionOrIntersection(),
                swap() && push(new Difference(pop(), pop()))
            )
        );
    }
    
    /**
     * Primary (('|' / '&') Primary)*
     */
    Rule UnionOrIntersection() {
    	return Sequence(
			Primary(),
			ZeroOrMore(
				FirstOf(Union(), Intersection())
			)
		);
    }

    Rule Union() {
        return Sequence(
    		Union.OPERATOR, 
    		Primary(),
    		swap() && push(new Union(pop(), pop()))
        );
    }

    Rule Intersection() {
        return Sequence(
    		Intersection.OPERATOR, 
    		Primary(),
    		swap() && push(new Intersection(pop(), pop()))
        );
    }

    /**
     * SetName | '(' Expression ')'
     */
    Rule Primary() {
        return FirstOf(
            SetName(),
            Sequence("(", Expression(), ")")
        );
    }

    /**
     * [a..z]+
     */
    Rule SetName() {
        return Sequence(
    		OneOrMore(CharRange('a', 'z')),
			push(new SetName(match()))
		);
    }
    
}