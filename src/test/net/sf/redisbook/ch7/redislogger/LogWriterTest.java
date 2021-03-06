package net.sf.redisbook.ch7.redislogger;

import static org.junit.Assert.assertTrue;

import java.util.Random;

import net.sf.redisbook.JedisHelper;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogWriterTest {
	static JedisHelper helper;
	static LogWriter logger;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		helper = JedisHelper.getInstance();
		logger = new LogWriter(helper);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		helper.destoryPool();
	}

	@Test
	public void testLogger() {
		Random random = new Random(System.currentTimeMillis());
		for (int i = 0; i < 100; i++) {
			assertTrue(logger.log(i + ",This is test log message") > 0);

			try {
				Thread.sleep(random.nextInt(50));
			}
			catch (InterruptedException e) {
				// do nothing.
			}
		}
	}
}
