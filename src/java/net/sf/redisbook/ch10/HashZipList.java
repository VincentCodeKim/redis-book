/******************************************************
 * author : Kris Jeong
 * published : 2013. 6. 30.
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
package net.sf.redisbook.ch10;

import java.util.Random;

import redis.clients.jedis.Jedis;
import net.sf.redisbook.JedisHelper;


/**
 * @author <A HREF="mailto:smufu@naver.com">kris jeong</A> smufu@naver.com
 *
 * class desc
 */
public class HashZipList {

	private Jedis jedis;

	private static final int INIT_HASH_ENTRY = 512;

	private static final int INIT_HASH_DATA_LENGTH = 64;

	private static final String KEY_HASH_ENTRY_TEST = "ziplist:hash:entry:test";

	private static final String KEY_HASH_LENGTH_TEST = "ziplist:hash:length:test";

	String chars[] = "a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z".split(",");

	/**
	 * @param helper
	 */
	public HashZipList(JedisHelper helper) {
		this.jedis = helper.getConnection();
	}

	/**
	 * 511개의 데이터가 저장된 해시 데이터의 인코딩 정보를 가져온다. 
	 * @return 인코딩 상태 문자열
	 */
	public String getBeforeEncoding1() {
		initHashForEntryTest();
		return this.jedis.objectEncoding(KEY_HASH_ENTRY_TEST);
	}

	/**
	 * 511개의 데이터가 저장된 해시 데이터의 인코딩 정보를 가져온다.
	 * @return 인코딩 상태 문자열
	 */
	public String getAfterEncoding1() {
		this.jedis.hset(KEY_HASH_ENTRY_TEST, "lastfield", "test value");
		return this.jedis.objectEncoding(KEY_HASH_ENTRY_TEST);
	}

	/**
	 * 한개의 64byte의 데이터가 저장된 해시 데이터의 인코딩 정보를 가져온다.
	 * @return 인코딩 상태 문자열
	 */
	public String getBeforeEncoding2() {
		initHashForLengthTest();
		return this.jedis.objectEncoding(KEY_HASH_LENGTH_TEST);
	}

	/**
	 * 한개의 65byte의 데이터가 추가된 해시 데이터의 인코딩 정보를 가져온다.
	 * @return 인코딩 상태 문자열
	 */
	public String getAfterEncoding2() {
		String testValue = this.makeRandomString(INIT_HASH_DATA_LENGTH + 1);
		this.jedis.hset(KEY_HASH_LENGTH_TEST, "field2", testValue);

		return this.jedis.objectEncoding(KEY_HASH_LENGTH_TEST);
	}

	/**
	 * 지정된 길이의 랜덤 문자열을 생성한다.
	 * @param size
	 */
	private String makeRandomString(int size) {
		Random rand = new Random();
		StringBuilder builder = new StringBuilder();

		for (int i = 0; i < size; i++) {
			builder.append(this.chars[rand.nextInt(this.chars.length)]);
		}

		return builder.toString();
	}

	private void initHashForEntryTest() {
		this.jedis.del(KEY_HASH_ENTRY_TEST);

		for (int i = 0; i < INIT_HASH_ENTRY; i++) {
			this.jedis.hset(KEY_HASH_ENTRY_TEST, "field1" + (i + 1), "test value" + i);
		}
	}
	
	private void initHashForLengthTest() {
		this.jedis.del(KEY_HASH_LENGTH_TEST);
		String testValue = this.makeRandomString(INIT_HASH_DATA_LENGTH);
		this.jedis.hset(KEY_HASH_LENGTH_TEST, "field", testValue);
	}
}
