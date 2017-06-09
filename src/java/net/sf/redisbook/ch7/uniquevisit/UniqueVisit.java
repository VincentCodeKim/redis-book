package net.sf.redisbook.ch7.uniquevisit;

import java.text.SimpleDateFormat;
import java.util.Date;
import redis.clients.jedis.*;
import net.sf.redisbook.JedisHelper;

public class UniqueVisit {
	private Jedis jedis;
	private static final String KEY_PAGE_VIEW = "page:view:";
	private static final String KEY_UNIQUE_VISITOR = "unique:visitors:";

	public UniqueVisit(JedisHelper helper) {
		this.jedis = helper.getConnection();
	}

	/**
	 * �듅�젙 �궗�슜�옄�쓽 �닚 諛⑸Ц�슏�닔�� �늻�쟻 諛⑸Ц�슏�닔瑜� 利앷� �떆�궓�떎.
	 * @param userNo �궗�슜�옄踰덊샇
	 */
	public void visit(int userNo) {
		Pipeline p = this.jedis.pipelined();
		p.incr(KEY_PAGE_VIEW + getToday());
		p.setbit(KEY_UNIQUE_VISITOR + getToday(), userNo, true);
		p.sync();
	}

	/**
	 * �슂泥��맂 �궇�옄�쓽 �늻�쟻 諛⑸Ц�옄�닔瑜� 議고쉶�븳�떎.
	 * @param date 議고쉶���긽 �궇�옄
	 * @return �늻�쟻 諛⑸Ц�옄�닔
	 */
	public int getPVCount(String date) {
		int result = 0;
		try {
			result = Integer.parseInt(jedis.get(KEY_PAGE_VIEW + date));
		}
		catch (Exception e) {
			result = 0;
		}
		return result;
	}

	/**
	 * �슂泥��맂 �궇�옄�쓽 �닚 諛⑸Ц�옄�닔瑜� 議고쉶�븳�떎.
	 * @param date 議고쉶���긽 �궇�옄
	 * @return �닚 諛⑸Ц�옄�닔
	 */
	public Long getUVCount(String date) {
		return jedis.bitcount(KEY_UNIQUE_VISITOR + date);
	}

	private String getToday() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return sdf.format(new Date());
	}
}
