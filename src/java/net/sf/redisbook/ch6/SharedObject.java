/******************************************************
 * author : Kris Jeong
 * published : 2013. 7. 8.
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
package net.sf.redisbook.ch6;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import net.sf.redisbook.JedisHelper;


/**
 * @author <A HREF="mailto:smufu@naver.com">kris jeong</A> smufu@naver.com
 *
 * class desc
 */
public class SharedObject {
	private Jedis jedis;

	private static final int MAX_SHARED_LIMIT = 65530;

	private static final String KEY_PREFIX = "INTKEY:";

	/**
	 * 레디스 공유객체 테스트를 위한 클래스 생성자
	 * @param helper 제디스 도우미 객체
	 */
	public SharedObject(JedisHelper helper) {
		this.jedis = helper.getConnection();
	}

	/**
	 * 공유객체를 테스트 하기 위한 데이터 베이스 초기화.
	 */
	public void prepareRedis() {
		this.jedis.flushDB();
	}

	/**
	 * MAX_SHARED_LIMIT 상수에 지정된 개수만큼 숫자 데이터를 저장한다.
	 */
	public void makeIntegerData() {
		Pipeline p = this.jedis.pipelined();
		for (int i = 0; i < MAX_SHARED_LIMIT; i++) {
			p.set(KEY_PREFIX + i, String.valueOf(i));
		}

		p.sync();
	}

	/**
	 * 레디스가 사용중인 메모리 정보를 조회한다.
	 * @return 메모리 사용량 정보
	 */
	public String getMemoryInfo() {
		String info = this.jedis.info("memory");
		System.out.println(info);
		return info;
	}

	/**
	 * 
	 */
	public String getNumberOfKeys() {
		String info = this.jedis.info("keyspace");
		String line[] = info.split("\n");
		String result = line[1].split("[,]")[0].split("[=]")[1];

		return result;
	}

	public String getSharedLimit() {
		return String.valueOf(MAX_SHARED_LIMIT);
	}
}
