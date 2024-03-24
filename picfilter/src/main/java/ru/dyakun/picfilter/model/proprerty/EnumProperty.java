package ru.dyakun.picfilter.model.proprerty;

import java.util.Collection;
import java.util.Map;

public class EnumProperty extends Property {

    private Object val;
    private String valKey;
    private final Map<String, Object> map;

    public EnumProperty(String val, Map<String, Object> map, String name) {
        super(name);
        this.map = map;
        this.valKey = val;
        this.val = map.get(val);
    }

    public void setVal(String key) {
        valKey = key;
        val = map.get(key);
        notify(val);
    }

    public Collection<String> getKeys() {
        return map.keySet();
    }

    public String getValKey() {
        return valKey;
    }

    public Object getVal() {
        return val;
    }

    @Override
    protected Object getValue() {
        return val;
    }

}
