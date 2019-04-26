package com.rjpa.redis;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * redis缓存帮助类
 */
@Repository
public class RedisDao {
    Gson gson = new Gson();
    @Autowired
    private RedisTemplate redisTemplate;

    public void setKey(String key, String value) {
        ValueOperations ops = redisTemplate.opsForValue();
        ops.set(key, value, 36000, TimeUnit.MINUTES); // 1分钟后数据过期
    }

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key   缓存的键值
     * @param value 缓存的值
     * @return 缓存的对象
     */
    public <T> ValueOperations<String, T> setCacheObject(String key, T value) {
        ValueOperations<String, T> operation = (ValueOperations<String, T>) redisTemplate.opsForValue();
        operation.set(key, value, 36000, TimeUnit.MINUTES);
        return operation;
    }

    /**
     * 获得缓存的基本对象。
     *
     * @param key 缓存键值
     * @return 缓存键值对应的数据
     */
    public <T> T getCacheObject(String key) {
        ValueOperations<String, T> operation = (ValueOperations<String, T>) redisTemplate.opsForValue();
        return operation.get(key);
    }

    /**
     * 缓存List数据
     *
     * @param key      缓存的键值
     * @param dataList 待缓存的List数据
     * @return 缓存的对象
     */
    public <T> ListOperations<String, T> setCacheList(String key, List<T> dataList) {
        ListOperations listOperation = redisTemplate.opsForList();
        if (null != dataList) {
            int size = dataList.size();
            for (int i = 0; i < size; i++) {
                listOperation.leftPush(key, dataList.get(i));
            }
        }
        return listOperation;
    }

    /**
     * 获得缓存的list对象
     *
     * @param key 缓存的键值
     * @return 缓存键值对应的数据
     */
    public <T> List<T> getCacheList(String key) {
        List<T> dataList = new ArrayList<T>();
        ListOperations<String, T> listOperation = (ListOperations<String, T>) redisTemplate.opsForList();
        Long size = listOperation.size(key);

        for (int i = 0; i < size; i++) {
            dataList.add(listOperation.index(key, i));
        }
        return dataList;
    }

    /**
     * 缓存Set
     *
     * @param key     缓存键值
     * @param dataSet 缓存的数据
     * @return 缓存数据的对象
     */
    public <T> BoundSetOperations<String, T> setCacheSet(String key, Set<T> dataSet) {
        BoundSetOperations<String, T> setOperation = (BoundSetOperations<String, T>) redisTemplate.boundSetOps(key);
        Iterator<T> it = dataSet.iterator();
        while (it.hasNext()) {
            setOperation.add(it.next());
        }
        return setOperation;
    }

    /**
     * 缓存Map
     *
     * @param key
     * @param dataMap
     * @return
     */
    public <T> HashOperations<String, String, T> setCacheMap(String key, Map<String, T> dataMap) {

        HashOperations hashOperations = redisTemplate.opsForHash();
        if (null != dataMap) {
            for (Map.Entry<String, T> entry : dataMap.entrySet()) {
                hashOperations.put(key, entry.getKey(), entry.getValue());
            }
        }
        return hashOperations;
    }

    /**
     * 获得缓存的Map
     *
     * @param key
     * @return
     */
    public <T> Map<Object, Object> getCacheMap(String key) {
        Map<Object, Object> map = redisTemplate.opsForHash().entries(key);
        return map;
    }


    /**
     * 缓存Map
     *
     * @param key
     * @param dataMap
     * @return
     */
    public <T> HashOperations<String, Integer, T> setCacheIntegerMap(String key, Map<Integer, T> dataMap) {
        HashOperations hashOperations = redisTemplate.opsForHash();
        if (null != dataMap) {
            for (Map.Entry<Integer, T> entry : dataMap.entrySet()) {
                hashOperations.put(key, entry.getKey(), entry.getValue());
            }
        }
        return hashOperations;
    }

    /**
     * 获得缓存的Map
     *
     * @param key
     * @return
     */
    public <T> Map<Object, Object> getCacheIntegerMap(String key) {
        Map<Object, Object> map = redisTemplate.opsForHash().entries(key);
        return map;
    }

    public void addSessionInfo(String sessionIdKey, final String sessionId, final SessionInformation sessionInformation) {
        BoundHashOperations<String, String, SessionInformation> hashOperations = redisTemplate.boundHashOps(sessionIdKey);
        hashOperations.put(sessionId, sessionInformation);
    }

    public SessionInformation getSessionInfo(String sessionIdKey, final String sessionId) {
        BoundHashOperations<String, String, SessionInformation> hashOperations = redisTemplate.boundHashOps(sessionIdKey);
        return hashOperations.get(sessionId);
    }

    public void removeSessionInfo(String sessionIdKey, final String sessionId) {
        BoundHashOperations<String, String, SessionInformation> hashOperations = redisTemplate.boundHashOps(sessionIdKey);
        hashOperations.delete(sessionId);
    }

    public Set<String> putIfAbsentPrincipals(String principalKey, final String key, final Set<String> set) {
        BoundHashOperations<String, String, Set<String>> hashOperations = redisTemplate.boundHashOps(principalKey);
        hashOperations.putIfAbsent(key, set);
        return hashOperations.get(key);
    }

    public void putPrincipals(String principalKey, final String key, final Set<String> set) {
        BoundHashOperations<String, String, Set<String>> hashOperations = redisTemplate.boundHashOps(principalKey);
        hashOperations.put(key, set);
    }

    public Set<String> getPrincipals(String principalKey, final String key) {
        BoundHashOperations<String, String, Set<String>> hashOperations = redisTemplate.boundHashOps(principalKey);
        return hashOperations.get(key);
    }

    public Set<String> getPrincipalsKeySet(String principalKey) {
        BoundHashOperations<String, String, Set<String>> hashOperations = redisTemplate.boundHashOps(principalKey);
        return hashOperations.keys();
    }

    public void removePrincipal(String principalKey, final String key) {
        BoundHashOperations<String, String, Set<String>> hashOperations = redisTemplate.boundHashOps(principalKey);
        hashOperations.delete(key);
    }
}
