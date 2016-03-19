package library.neetoffice.com.xmlparser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Deo on 2016/3/10.
 */
public class ElementMap {
    private final HashMap<String, ArrayList<ElementValue>> map = new HashMap<String, ArrayList<ElementValue>>();


    public void clear() {
        map.clear();
    }


    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }


    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }


    public Set<Map.Entry<String, ArrayList<ElementValue>>> entrySet() {
        return map.entrySet();
    }


    public ArrayList<ElementValue> get(Object key) {
        return map.get(key);
    }


    public boolean isEmpty() {
        return map.isEmpty();
    }


    public Set<String> keySet() {
        return map.keySet();
    }


    public ArrayList<ElementValue> put(String key, ArrayList<ElementValue> value) {
        return map.put(key, value);
    }

    public void putAll(Map<? extends String, ? extends ArrayList<ElementValue>> map) {
        this.map.putAll(map);
    }

    public ArrayList<ElementValue> remove(Object key) {
        return map.remove(key);
    }

    public int size() {
        return map.size();
    }

    public Collection<ArrayList<ElementValue>> values() {
        return map.values();
    }
}
