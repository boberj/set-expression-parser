package se.boberg.sets;

import java.util.Map;
import java.util.Set;

import se.boberg.sets.setproviders.MapSetProvider;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;

/**
 * Simple usage example. Also see the tests.
 */
public class SetsDemo {
	
	private static Map<String, Set<String>> sets = ImmutableMap.<String, Set<String>>of(
		"fruits", Sets.newHashSet("apple", "orange", "strawberry"), 
		"sweets", Sets.newHashSet("chocolate", "liqourice"), 
		"deliciousness", Sets.newHashSet("strawberry", "chocolate", "whipped cream")
	);
	
	private static Parser<String> parser = new Parser<String>(new MapSetProvider<String>(sets));

	public static void main(String[] args) {
		String expression = "(fruits | sweets) & deliciousness";
		Set<String> result;
		
		try {
			result = parser.parse(expression);
		} catch (ParseException e) {
			System.out.println(e);
			return;
		}
		
		System.out.println(expression + " = " + result);
	}
	
	
}
