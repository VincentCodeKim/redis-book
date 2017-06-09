/******************************************************
 * author : Kris Jeong
 * published : 2013. 6. 18.
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
package net.sf.redisbook;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.pool.impl.GenericObjectPool;

import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.util.Hashing;
import sun.security.krb5.Config;

/**
 * @author <A HREF="mailto:smufu@naver.com">kris jeong</A> smufu@naver.com
 *
 * class desc
 */
public class ShardedJedisHelper {
	protected static final String SHARD1_HOST = "192.168.56.102";
	protected static final int SHARD1_PORT = 6380;
	protected static final String SHARD2_HOST = "192.168.56.102";
	protected static final int SHARD2_PORT = 6381;

	private final Set<ShardedJedis> connectionList = new HashSet<ShardedJedis>();

	private ShardedJedisPool shardedPool;
	
	/**
	 * �떛湲��넠 泥섎━瑜� �쐞�븳 ���뜑 �겢�옒�뒪, �젣�뵒�뒪 �뿰寃고��씠 �룷�븿�맂 �룄�슦誘� 媛앹껜瑜� 諛섑솚�븳�떎.
	 */
	private static class LazyHolder {
		@SuppressWarnings("synthetic-access")
		private static final ShardedJedisHelper INSTANCE = new ShardedJedisHelper();
	}

	/**
	 * �깶�뵫�맂 �젣�뵒�뒪 �뿰寃고� �깮�꽦�쓣 �쐞�븳 �룄�슦誘� �겢�옒�뒪 �궡遺� �깮�꽦�옄. 
	 * �떛湲��넠 �뙣�꽩�씠誘�濡� �쇅遺��뿉�꽌 �샇異쒗븷 �닔 �뾾�떎.
	 */
	private ShardedJedisHelper() {
		Config config = new Config();
		config.maxActive = 20;
		config.whenExhaustedAction = GenericObjectPool.WHEN_EXHAUSTED_BLOCK;

		List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
		shards.add(new JedisShardInfo(SHARD1_HOST, SHARD1_PORT));
		shards.add(new JedisShardInfo(SHARD2_HOST, SHARD2_PORT));

		this.shardedPool = new ShardedJedisPool(config, shards, Hashing.MURMUR_HASH);
	}

	/**
	 * �떛湲��넠 媛앹껜瑜� 媛��졇�삩�떎.
	 * @return �젣�뵒�뒪 �룄�슦誘멸컼泥�
	 */
	@SuppressWarnings("synthetic-access")
	public static ShardedJedisHelper getInstance() {
		return LazyHolder.INSTANCE;
	}

	/**
	 * �젣�뵒�뒪 �겢�씪�씠�뼵�듃 �뿰寃곗쓣 媛��졇�삩�떎.
	 * @return �젣�뵒�뒪 媛앹껜
	 */
	final public ShardedJedis getConnection() {
		ShardedJedis jedis = this.shardedPool.getResource();
		this.connectionList.add(jedis);

		return jedis;
	}

	/**
	 * �궗�슜�씠 �셿猷뚮맂 �젣�뵒�뒪 媛앹껜瑜� �쉶�닔�븳�떎.
	 * @param jedis �궗�슜 �셿猷뚮맂 �젣�뵒�뒪 媛앹껜
	 */
	final public void returnResource(ShardedJedis jedis) {
		this.shardedPool.returnResource(jedis);
	}

	/**
	 * �젣�뵒�뒪 �뿰寃고��쓣 �젣嫄고븳�떎.
	 */
	final public void destoryPool() {
		Iterator<ShardedJedis> jedisList = this.connectionList.iterator();
		while (jedisList.hasNext()) {
			ShardedJedis jedis = jedisList.next();
			this.shardedPool.returnResource(jedis);
		}

		this.shardedPool.destroy();
	}
}
