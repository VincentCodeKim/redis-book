/******************************************************
 * author : Kris Jeong
 * published : 2013. 6. 15.
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
package net.sf.redisbook.ch8.replication;

import net.sf.redisbook.ch7.redislogger.KeyMaker;


/**
 * @author <A HREF="mailto:smufu@naver.com">kris jeong</A> smufu@naver.com
 *
 * class desc
 */
public class ReplicationKeyMaker implements KeyMaker {
	private static final String keyPrefix = "Replication-";

	private int index;

	/**
	 * �궎 硫붿씠而� �겢�옒�뒪瑜� �쐞�븳 �깮�꽦�옄.
	 * @param index �궎 �깮�꽦�쓣 �쐞�븳 �씤�뜳�뒪 媛� 
	 */
	public ReplicationKeyMaker(int index) {
		this.index = index;
	}

	/* (non-Javadoc)
	 * @see net.sf.redisbook.ch7.redislogger.KeyMaker#getKey()
	 */

	public String getKey() {
		return keyPrefix + this.index;
	}
}
