package se.boberg.sets;

import java.util.Set;

import org.parboiled.Parboiled;
import org.parboiled.parserunners.ReportingParseRunner;
import org.parboiled.support.ParsingResult;

import se.boberg.sets.ast.nodes.Node;
import se.boberg.sets.setproviders.SetProvider;


/**
 * Parser that recognizes the set operations union (|), intersection (&) and difference (-).
 * The parser operates on the given expression and fetches the sets using the given SetProvider.
 */
public class Parser<T> {

	private final Grammar grammar = Parboiled.createParser(Grammar.class);
	private final Interpreter<T> interpreter;
	
	public Parser(SetProvider<T> setProvider) {
		this.interpreter = new Interpreter<T>(setProvider);
	}
	
	public Set<T> parse(String expression) throws ParseException {
		// Remove whitespace
		expression = expression.replace(" ", "");
		
		ParsingResult<Node> parseResult = new ReportingParseRunner<Node>(grammar.Model()).run(expression);
		
		if (parseResult.hasErrors()) {
	        throw new ParseException(expression, parseResult.parseErrors);
	    }
		
		return interpreter.interpret(parseResult.resultValue);
	}

}
