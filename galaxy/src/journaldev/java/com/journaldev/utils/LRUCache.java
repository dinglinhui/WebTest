package com.journaldev.utils;

import java.util.LinkedHashMap;
import java.util.Map;
 
@SuppressWarnings("serial")
public class LRUCache<K, V> extends LinkedHashMap<K, V> {
  private int cacheSize;
 
  public LRUCache(int cacheSize) {
    super(16, (float) 0.75, true);
    this.cacheSize = cacheSize;
  }
 
  protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
    return size() >= cacheSize;
  }
}