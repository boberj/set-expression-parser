package se.boberg.sets;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Map;
import java.util.Set;

import org.junit.Test;

import se.boberg.sets.ParseException;
import se.boberg.sets.Parser;
import se.boberg.sets.setproviders.MapSetProvider;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;

public class ParserTests {
	
	private static final Set<String> a = Sets.newHashSet("a", "b", "c");
	private static final Set<String> b = Sets.newHashSet("d");
	private static final Set<String> c = Sets.newHashSet("c", "d", "e");
	
	private static final Map<String, Set<String>> sets = ImmutableMap.of(
		"a", a, 
		"b", b, 
		"c", c
	);
	
	private static final Parser<String> parser = new Parser<String>(new MapSetProvider<String>(sets));
	
	@Test
	public void testSetName() {
		assertThat(parse("a"), is(a));
	}
	
	@Test
	public void testUnion() {
		assertThat(parse("a|b"), is(union(a, b)));
	}
	
	@Test
	public void testIntersection() {
		assertThat(parse("a&c"), is(intersection(a, c)));
	}
	
	@Test
	public void testPrecedence() {
	    // Union has higher precedence than difference
	    assertThat(parse("c-a|b"), is(difference(difference(c, b), union(a, b)))); // e vs d, e
	    
	    // Intersection has higher precedence than difference
	    assertThat(parse("c-a&b"), is(difference(c, intersection(a, b)))); // c, e vs d
	    
		// Union and intersection have same precedence
		assertThat(parse("a&b|c"), is(union(intersection(a, b), c)));
		assertThat(parse("c|a&b"), is(intersection(union(c, a), b)));
		
		// Brackets have higher precedence than anything
		assertThat(parse("(a|b)&c"), is(intersection(union(a, b), c)));
	}
	
    @Test
	public void testWhitespace() {
	    assertThat(parse("a | b | ( b )"), is(union(a, b)));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void invalid() {
	    parse("a||b");
	}

	private Set<String> parse(String expression) {
	    try {
			return parser.parse(expression);
		} catch (ParseException e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	private Set<String> union(Set<String> set1, Set<String> set2) {
		return Sets.union(set1, set2);
	}
	
	private Set<String> intersection(Set<String> set1, Set<String> set2) {
		return Sets.intersection(set1, set2);
	}
	
	private Set<String> difference(Set<String> set1, Set<String> set2) {
        return Sets.difference(set1, set2);
    }

}
