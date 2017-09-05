package com.lol.common.utils;

/**
 * <p>describing: map链，用于快速设置键值对
 * example: new FastMap<String, String>().newHashMap().set("key1", "value1").
 * set("key2", "value2")
 * </p>
 * @author yangli
 *
 */
public class FastMap<K,V> {
    private HashMap<K, V> hashMap;
    private LinkedHashMap<K, V> linkedHashMap;
    
    public FastMap(){
        
    }
    
    public HashMap<K, V> newHashMap(){
        this.hashMap = new HashMap<K, V>();
        
        return hashMap;
    }
    
    public LinkedHashMap<K, V> newLinkedHashMap(){
        this.linkedHashMap = new LinkedHashMap<K, V>();
        
        return linkedHashMap;
    }
    
    @SuppressWarnings("hiding")
    public class HashMap<K, V> extends java.util.HashMap<K, V>{
        private static final long serialVersionUID = 3144023201049252921L;
        public HashMap<K, V> set(K key, V value){
            super.put(key, value);
            return this;
        }
    }
    
    @SuppressWarnings("hiding")
    public class LinkedHashMap<K, V> extends java.util.LinkedHashMap<K, V>{
        private static final long serialVersionUID = 5642668879628839812L;
        public LinkedHashMap<K, V> set(K key, V value){
            super.put(key, value);
            return this;
        }
    }
}

