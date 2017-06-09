package net.sf.redisbook.ch7.redislogger;

import net.sf.redisbook.JedisHelper;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogReceiverTest {
	static JedisHelper helper;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		helper = JedisHelper.getInstance();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		helper.destoryPool();
	}

	@Test
	public void testLogger() {
		LogReceiver receiver = new LogReceiver();
		receiver.start();
	}
}
