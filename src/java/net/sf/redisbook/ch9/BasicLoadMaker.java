/******************************************************
 * author : Kris Jeong
 * published : 2013. 7. 13.
 * project name : redis-book
 * 
 * Email : smufu@naver.com, smufu1@hotmail.com
 * 
 * Develop JDK Ver. 1.6.0_13
 * 
 * Issue list.
 * 
 * 	function.
 *     1. 
 *     
 ********** process *********
 *
 ********** edited **********
 *
 ******************************************************/
package net.sf.redisbook.ch9;

import net.sf.redisbook.JedisHelper;
import redis.clients.jedis.Jedis;


/**
 * @author <A HREF="mailto:smufu@naver.com">kris jeong</A> smufu@naver.com
 *
 * class desc
 */
public class BasicLoadMaker {
	private Jedis jedis;

	private static final String KEY_PREFIX = "load:test";

	/**
	 * 레디스 부하 테스트를 위한 클래스 생성자
	 * @param helper 제디스 도우미 객체
	 */
	public BasicLoadMaker(JedisHelper helper) {
		this.jedis = helper.getConnection();
	}

	public void setData(String data) {
		jedis.lpush(KEY_PREFIX, data);
	}
	
	public String getData()	{
		return jedis.rpop(KEY_PREFIX);
	}
}
