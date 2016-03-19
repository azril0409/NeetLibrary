package library.neetoffice.com.xmlparser;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Deo on 2016/3/10.
 */
final class AttributeMap {
    private final HashMap<String, String> map = new HashMap<String, String>();

    public void clear() {
        map.clear();
    }

    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }

    public Set<Map.Entry<String, String>> entrySet() {
        return map.entrySet();
    }

    public String get(Object key) {
        return map.get(key);
    }

    public boolean isEmpty() {
        return map.isEmpty();
    }

    public Set<String> keySet() {
        return map.keySet();
    }

    public String put(String key, String value) {
        return map.put(key, value);
    }

    public void putAll(Map<? extends String, ? extends String> map) {
        this.map.putAll(map);
    }

    public String remove(Object key) {
        return map.remove(key);
    }

    public int size() {
        return map.size();
    }

    public Collection<String> values() {
        return map.values();
    }
}
