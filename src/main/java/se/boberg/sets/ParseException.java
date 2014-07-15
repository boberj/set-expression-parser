package se.boberg.sets;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import org.parboiled.errors.InvalidInputError;
import org.parboiled.errors.ParseError;
import org.parboiled.matchers.Matcher;
import org.parboiled.support.MatcherPath;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.FluentIterable;

public class ParseException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private final List<ParseError> errors;
	private final String expression;

	public ParseException(String expression, List<ParseError> errors) {
		super("Could not parse " + expression);
		this.expression = expression;
		this.errors = errors;
	}

	public List<ParseError> getErrors() {
		return errors;
	}
	
	public String getErrorsAsString() {
		StringWriter string = new StringWriter();
		PrintWriter writer = new PrintWriter(string);
		
		for (ParseError error : errors) {
	        if (error instanceof InvalidInputError) {
	            writer.print(String.format("[%s, %s] (%s): ", error.getStartIndex(), error.getEndIndex(), expression.subSequence(error.getStartIndex(),  error.getEndIndex())));
	            writer.print("expected ");
	            Iterable<String> alternatives = FluentIterable
	                .from(((InvalidInputError) error).getFailedMatchers())
	                .transform(toMatcher)
	                .transform(toString);
	            
	            writer.println(Joiner.on(" or ").join(alternatives));
	        } else {
	        	writer.println(error);
	        }
	    }
		
		return string.toString();
	}
	
	
	@Override
	public String toString() {
		return getMessage() + "\n" + getErrorsAsString();
	}



	private static final Function<MatcherPath, Matcher> toMatcher= new Function<MatcherPath, Matcher>() {
        @Override
        public Matcher  apply(MatcherPath input) {
            return input.element.matcher;
        }
    };
    
    private static final Function<Object, String> toString = new Function<Object, String>() {
        @Override
        public String  apply(Object  input) {
            return input.toString();
        }
    };


}
