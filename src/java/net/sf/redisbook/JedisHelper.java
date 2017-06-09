package net.sf.redisbook;
import java.util.*;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.commons.pool.impl.GenericObjectPool.Config;
import redis.clients.jedis.*;

public class JedisHelper {
	protected static final String REDIS_HOST = "192.168.56.102";
	protected static final int REDIS_PORT = 6379;
	private final Set<Jedis> connectionList = new HashSet<Jedis>();
	private JedisPool pool;

	/**
	 * �젣�뵒�뒪 �뿰寃고� �깮�꽦�쓣 �쐞�븳 �룄�슦誘� �겢�옒�뒪 �궡遺� �깮�꽦�옄. �떛湲��넠 �뙣�꽩�씠誘�濡� �쇅遺��뿉�꽌 �샇異쒗븷 �닔 �뾾�떎.
	 */
	private JedisHelper() {
		Config config = new Config();
		config.maxActive = 20;
		config.whenExhaustedAction = GenericObjectPool.WHEN_EXHAUSTED_BLOCK;

		this.pool = new JedisPool(config, REDIS_HOST, REDIS_PORT, 5000);
	}

	
	/**
	 * �떛湲��넠 泥섎━瑜� �쐞�븳 ���뜑 �겢�옒�뒪, �젣�뵒�뒪 �뿰寃고��씠 �룷�븿�맂 �룄�슦誘� 媛앹껜瑜� 諛섑솚�븳�떎.
	 */
	private static class LazyHolder {
		@SuppressWarnings("synthetic-access")
		private static final JedisHelper INSTANCE = new JedisHelper();
	}

	/**
	 * �떛湲��넠 媛앹껜瑜� 媛��졇�삩�떎.
	 * @return �젣�뵒�뒪 �룄�슦誘멸컼泥�
	 */
	@SuppressWarnings("synthetic-access")
	public static JedisHelper getInstance() {
		return LazyHolder.INSTANCE;
	}

	/**
	 * �젣�뵒�뒪 �겢�씪�씠�뼵�듃 �뿰寃곗쓣 媛��졇�삩�떎.
	 * @return �젣�뵒�뒪 媛앹껜
	 */
	final public Jedis getConnection() {
		Jedis jedis = this.pool.getResource();
		this.connectionList.add(jedis);

		return jedis;
	}
	
	/**
	 * �궗�슜�씠 �셿猷뚮맂 �젣�뵒�뒪 媛앹껜瑜� �쉶�닔�븳�떎.
	 * @param jedis �궗�슜 �셿猷뚮맂 �젣�뵒�뒪 媛앹껜
	 */
	final public void returnResource(Jedis jedis)	{
		this.pool.returnResource(jedis);
	}

	/**
	 * �젣�뵒�뒪 �뿰寃고��쓣 �젣嫄고븳�떎.
	 */
	final public void destoryPool() {		
		Iterator<Jedis> jedisList = this.connectionList.iterator();
		while (jedisList.hasNext()) {
			Jedis jedis = jedisList.next();
			this.pool.returnResource(jedis);
		}

		this.pool.destroy();
	}
}
