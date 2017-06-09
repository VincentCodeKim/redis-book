package net.sf.redisbook.ch5;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;


public class RedisInsertTest {
	private static final float TOTAL_OP = 1000000f;

	public static void main(String args[]) {
		JedisPool pool = new JedisPool("192.168.56.102", 6379);
		Jedis jedis = pool.getResource();
		String key, value;
		long start = now();
		long loopTime = now();

		for (int i = 1; i <= TOTAL_OP; i++) {
			key = value = String.valueOf("key" + (100000000 + i));
			jedis.set(key, value);
		}

		long elapsed = now() - start;
		System.out.println("초당 처리건수 " + TOTAL_OP / elapsed * 1000f);
		System.out.println("소요시간 " + elapsed / 1000f + "초");
		jedis.disconnect();
	}

	private static long now() {
		return System.currentTimeMillis();
	}
}
