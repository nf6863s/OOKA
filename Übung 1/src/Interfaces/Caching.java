package Interfaces;

import java.util.List;

public interface Caching<T> {
    public void cacheResult(String key, List<T> value);
    public List<T> readCache(String key);
    public boolean isCached(String key);
}
