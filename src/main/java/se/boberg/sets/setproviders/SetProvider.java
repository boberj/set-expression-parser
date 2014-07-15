package se.boberg.sets.setproviders;

import java.util.Set;

/**
 * Returns a set keyed by its name.
 */
public interface SetProvider<T> {

    Set<T> get(String name);
    
}
