package cn.nuofankj.myblog.util;

import org.apache.log4j.Logger;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisUtil {

    // Redis服务器IP
    private static String ADDR = "localhost";

    // Redis的端口号
    private static int PORT = 6379;

    // 可用连接实例的最大数目，默认值为8；
    // 如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
    private static int MAX_ACTIVE = 2000;

    // 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
    private static int MAX_IDLE = 200;

    // 等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
    private static int MAX_WAIT = 10000;

    private static int TIMEOUT = 10000;// 0是关闭此设置

    // 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
    private static boolean TEST_ON_BORROW = true;

    private static JedisPool jedisPool = null;

    /**
     * 初始化Redis连接池
     */
    static {
        try {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(MAX_ACTIVE);
            config.setMaxIdle(MAX_IDLE);
            config.setMaxWaitMillis(MAX_WAIT);
            config.setTestOnBorrow(TEST_ON_BORROW);
            config.setTestOnReturn(TEST_ON_BORROW);
            jedisPool = new JedisPool(config, ADDR, PORT, TIMEOUT);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取redis大小
     *
     * @return
     */
    public static long dbSize() {
        Jedis jedis = null;
        long size = 0;
        try {
            jedis = getJedis();
            size = jedis.dbSize();
        } catch (Exception e) {
            //释放redis对象
            returnResource(jedis);
            Logger.getLogger(RedisUtil.class).error("set error", e);
        } finally {
            returnResource(jedis);
        }
        return size;
    }

    /**
     * 判断字节key是否消失
     *
     * @param key
     * @return
     */
    public static boolean exists(byte[] key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.exists(key);
        } catch (Exception e) {
            //释放redis对象
            returnResource(jedis);
            Logger.getLogger(RedisUtil.class).error("exists error", e);
        } finally {
            returnResource(jedis);
        }
        return false;
    }

    public static boolean exists(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.exists(key);
        } catch (Exception e) {
//释放redis对象
            returnResource(jedis);
            Logger.getLogger(RedisUtil.class).error("exists error", e);
        } finally {
            returnResource(jedis);
        }
        return false;
    }

    /**
     * 为字节对象设置时间
     *
     * @param key
     * @param time
     */
    public static void expire(byte[] key, int time) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.expire(key, time);
        } catch (Exception e) {
            //释放redis对象
            returnResource(jedis);
            Logger.getLogger(RedisUtil.class).error("expire error", e);
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * @param key
     * @param time void
     * @throws
     * @方法名称:expire
     * @内容摘要: ＜设置数据有限时间＞
     * @author:鹿伟伟
     * @创建日期:2016年3月3日-上午10:37:51
     */
    public static void expire(String key, int time) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.expire(key, time);
        } catch (Exception e) {
            //释放redis对象
            returnResource(jedis);
            Logger.getLogger(RedisUtil.class).error("expire error", e);
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * @param key
     * @return String
     * @throws
     * @方法名称:get
     * @内容摘要: ＜直接从从库中获得信息＞
     * @author:鹿伟伟
     * @创建日期:2016年3月16日-上午10:52:22
     */
    public static String get(String key) {
        String value = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            value = jedis.get(key);
        } catch (Exception e) {
            //释放redis对象
            returnBrokenResource(jedis);
            Logger.getLogger(RedisUtil.class).error("get error", e);
        } finally {
            returnResource(jedis);
        }
        return value;
    }

    /**
     * 获取Jedis实例
     *
     * @return
     */
    public static Jedis getJedis() {
        try {
            if (jedisPool != null) {
                Jedis jedis = jedisPool.getResource();
                //jedisPool.returnBrokenResource(jedis);
                return jedis;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 取出字节
     *
     * @param key
     * @return
     */
    public static byte[] getObject(byte[] key) {
        byte[] value = null;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            value = jedis.get(key);
        } catch (Exception e) {
            //释放redis对象
            returnBrokenResource(jedis);
            Logger.getLogger(RedisUtil.class).error("get error", e);
        } finally {
            returnResource(jedis);
        }
        return value;
    }

    /**
     * 释放jedis资源
     *
     * @param jedis
     */
    public static void returnBrokenResource(final Jedis jedis) {
        if (jedis != null && jedisPool != null) {
            jedisPool.close();
        }
    }

    /**
     * 返还到连接池
     *
     * @param jedis
     */
    public static void returnResource(final Jedis jedis) {
        if (jedis != null && jedisPool != null) {
            //jedisPool.returnResource(jedis);
            jedis.close();
        }
    }

    /**
     * @param key
     * @param value void
     * @throws
     * @方法名称:set
     * @内容摘要: ＜设置数据＞
     * @author:鹿伟伟
     * @创建日期:2016年3月3日-上午10:34:50
     */
    public static void set(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.set(key, value);
        } catch (Exception e) {
//释放redis对象
            returnResource(jedis);
            Logger.getLogger(RedisUtil.class).error("set error", e);
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 设置对象的同时设置时间
     *
     * @param key
     * @param value
     * @param time
     */
    public static void setObjectTime(byte[] key, byte[] value, int time) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.set(key, value);
            jedis.expire(key, time);
        } catch (Exception e) {
            //释放redis对象
            returnResource(jedis);
            Logger.getLogger(RedisUtil.class).error("set error", e);
        } finally {
            returnResource(jedis);
        }
    }

    /**
     * 设置值的同时设置时间
     *
     * @param key
     * @param value
     * @param time
     */
    public static void setStringTime(String key, String value, int time) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.set(key, value);
            jedis.expire(key, time);
        } catch (Exception e) {
            //释放redis对象
            returnResource(jedis);
            Logger.getLogger(RedisUtil.class).error("set error", e);
        } finally {
            returnResource(jedis);
        }
    }
}