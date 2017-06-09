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

import static org.junit.Assert.*;

import net.sf.redisbook.JedisHelper;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


/**
 * @author <A HREF="mailto:smufu@naver.com">kris jeong</A> smufu@naver.com
 *
 * class desc
 */
public class HashZipListTest {
	static JedisHelper helper;

	private HashZipList hashZipList;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		helper = JedisHelper.getInstance();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		helper.destoryPool();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		this.hashZipList = new HashZipList(helper);
	}

	@Test
	public void testEncodingTestForEntrySize() {
		assertEquals("ziplist", this.hashZipList.getBeforeEncoding1());

		assertEquals("hashtable", this.hashZipList.getAfterEncoding1());
	}
	
	@Test
	public void testEncodingTestForDataSize() {
		assertEquals("ziplist", this.hashZipList.getBeforeEncoding2());
		
		assertEquals("hashtable", this.hashZipList.getAfterEncoding2());
	}

}
