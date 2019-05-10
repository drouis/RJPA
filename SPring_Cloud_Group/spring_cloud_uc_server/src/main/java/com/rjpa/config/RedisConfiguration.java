package com.rjpa.config;

import com.rjpa.redis.GenericJackson2JsonRedisSerializerEx;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.lang.reflect.Method;
import java.net.UnknownHostException;
import java.time.Duration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * redis 工具类
 */
@Configuration
@EnableCaching
public class RedisConfiguration extends CachingConfigurerSupport {

    public final String PRE_KEY = "MicoUC";

    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private int port;
    @Value("${spring.redis.timeout}")
    private int timeout;

    /**
     * retemplate相关配置
     *
     * @param redisConnectionFactory
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(name = "redisTemplate")
    public RedisTemplate<Object, Object> redisTemplate(
            RedisConnectionFactory redisConnectionFactory)
            throws UnknownHostException {
        RedisTemplate<Object, Object> template = new RedisTemplate<Object, Object>();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

    @Bean
    @ConditionalOnMissingBean(StringRedisTemplate.class)
    public StringRedisTemplate stringRedisTemplate(
            RedisConnectionFactory redisConnectionFactory)
            throws UnknownHostException {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);
        template.afterPropertiesSet();
        return template;
    }

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        // 1 更改值的序列化方式，否则在Redis可视化软件中会显示乱码。默认为 JdkSerializationRedisSerializer
        RedisSerializationContext.SerializationPair<Object> pair = RedisSerializationContext.SerializationPair
                .fromSerializer(new GenericJackson2JsonRedisSerializerEx());
        // 2 登陆配置，默认设置24小时
        RedisCacheConfiguration loginCacheConfig = RedisCacheConfiguration.defaultCacheConfig()
                // 设置序列化方式
                .serializeValuesWith(pair)
                .entryTtl(Duration.ofHours(24));// 设置缓存的默认过期时间，也是使用Duration设置
        // 默认配置， 默认超时时间为30s
        RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig();  // 生成一个默认配置，通过config对象即可对缓存进行自定义配置
        defaultCacheConfig = defaultCacheConfig.entryTtl(Duration.ofMinutes(2))     // 设置缓存的默认过期时间，也是使用Duration设置
                .disableCachingNullValues();     // 不缓存空值
        // 3 设置一个初始化的缓存空间set集合
        Set<String> cacheNames = new HashSet<>();
        cacheNames.add(PRE_KEY + "_loggin_cache");
        cacheNames.add(PRE_KEY + "_cache2");
        // 对每个缓存空间应用不同的配置
        Map<String, RedisCacheConfiguration> configMap = new HashMap<>();
        configMap.put(PRE_KEY + "_loggin_cache", loginCacheConfig);
        configMap.put(PRE_KEY + "_cache2", defaultCacheConfig);
        // 4 配置缓存
        RedisCacheManager cacheManager = RedisCacheManager
                .builder(RedisCacheWriter.nonLockingRedisCacheWriter(connectionFactory))// 使用自定义的缓存配置初始化一个cacheManager
                .initialCacheNames(cacheNames) // 注意这两句的调用顺序，一定要先调用该方法设置初始化的缓存名，再初始化相关的配置
                .withInitialCacheConfigurations(configMap)
                .build();
        return cacheManager;
    }

    @Override
    public KeyGenerator keyGenerator() {
        // key前缀，用于区分不同项目的缓存，建议每个项目单独设置
        final char sp = ':';
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuilder sb = new StringBuilder();
                sb.append(PRE_KEY);
                sb.append(sp);
                sb.append(target.getClass().getSimpleName());
                sb.append(sp);
                sb.append(method.getName());
                for (Object obj : params) {
                    sb.append(sp);
                    sb.append(obj.toString());
                }
                return sb.toString();
            }
        };
    }
}
