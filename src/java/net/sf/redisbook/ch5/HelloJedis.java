package net.sf.redisbook.ch5;

import redis.clients.jedis.Jedis;

/**
 * first Jedis sample
 */
public class HelloJedis {
	/**
	 * main function
	 * @param args none
	 */
	public static void main(String[] args) {
		// connect to redis server running on 192.168.56.102
		Jedis jedis = new Jedis("localhost", 6379);

		// set data to key redisbook
		String result = jedis.set("redisbook", "hi redis World!");
		System.out.println(result);
		
		// get data from key redisbook
		System.out.println(jedis.get("redisbook"));
		
		Jedis jediss=new Jedis("localhost",6379);
		String result2 =jediss.set("redisbook:code1","hi vinecet");
		System.out.println(result2);
	   System.out.println(jediss.get("redisbook:code1"));
	}
}
