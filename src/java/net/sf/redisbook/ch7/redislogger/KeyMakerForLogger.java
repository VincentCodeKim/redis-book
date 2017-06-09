package net.sf.redisbook.ch7.redislogger;

public class KeyMakerForLogger implements KeyMaker {
	private static final String KEY_WAS_LOG = "was:log:list";
	private final String serverId;

	public KeyMakerForLogger(String serverId) {
		this.serverId = serverId;
	}

	/* (non-Javadoc)
	 * @see net.sf.redisbook.ch7.redislogger.KeyMaker#getKey()
	 */

	public String getKey() {
		return KeyMakerForLogger.KEY_WAS_LOG;
	}
	
	/**
	 * �꽌踰� �븘�씠�뵒瑜� 議고쉶�븳�떎.
	 * @return �꽌踰� �븘�씠�뵒
	 */
	public String getServerId() {
		return this.serverId;
	}
}
