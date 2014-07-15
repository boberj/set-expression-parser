package se.boberg.sets.setproviders;

import java.util.Map;
import java.util.Set;

/**
 * A SestProvider that stores all the sets in a map.
 */
public class MapSetProvider<T> implements SetProvider<T> {
    
    private final Map<String, Set<T>> sets;
    
    public MapSetProvider(Map<String, Set<T>> sets) {
        this.sets = sets;
    }

    @Override
    public Set<T> get(String name) {
        return sets.get(name);
    }

}
