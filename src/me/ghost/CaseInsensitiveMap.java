package me.ghost;

import java.util.HashMap;
import java.util.Map;

public class CaseInsensitiveMap<V> extends HashMap<String, V> {

    private static final long serialVersionUID = 1L;

    /**
     * {@inheritDoc}
     */
    public CaseInsensitiveMap(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    public CaseInsensitiveMap(int initialCapacity) {
        super(initialCapacity);
    }

    public CaseInsensitiveMap() {
    }

    public CaseInsensitiveMap(Map<? extends String, ? extends V> m) {
        super(m);
    }

    /** 
     * @param key
     * @return V
     */
    @Override
    public V get(Object key) {
        if (!(key instanceof String)) {
            throw new IllegalArgumentException("CaseInsensitiveMap only accepts strings as keys");
        }

        return super.get(((String) key).toLowerCase());
    }

    /** 
     * @param key
     * @param value
     * @return V
     */
    @Override
    public V put(String key, V value) {
        return super.put(key.toLowerCase(), value);
    }
}
