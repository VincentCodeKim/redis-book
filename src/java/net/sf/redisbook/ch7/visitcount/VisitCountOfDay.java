package net.sf.redisbook.ch7.visitcount;
import java.text.SimpleDateFormat;
import java.util.*;
import net.sf.redisbook.JedisHelper;
import redis.clients.jedis.Jedis;

public class VisitCountOfDay {
	private JedisHelper jedisHelper;
	private Jedis jedis;
	private static final String KEY_EVENT_CLICK_DAILY_TOTAL = "event:click:daily:total:";
	private static final String KEY_EVENT_CLICK_DAILY = "event:click:daily:";

	/**
	 * 날자별 방문횟수 처리를 위한 클래스 생성자. 
	 * @param jedisHelper 제디스 도우미 객체
	 */
	public VisitCountOfDay(JedisHelper jedisHelper) {
		this.jedisHelper = jedisHelper;
		this.jedis = this.jedisHelper.getConnection();
	}

	/**
	 * 이벤트 아이디에 해당하는 날자의 방문횟수와 날자별 전체 방문횟수를 증가시킨다.
	 * @param eventId 이벤트 아이디
	 * @return 이벤트 페이지 방문횟수 
	 */
	public Long addVisit(String eventId) {
		this.jedis.incr(KEY_EVENT_CLICK_DAILY_TOTAL + getToday()); 
		return this.jedis.incr(KEY_EVENT_CLICK_DAILY + getToday() + ":" + eventId); 
	}

	/**
	 * 요청된 날자에 해당하는 전체 이벤트 페이지 방문횟수를 조회한다.
	 * @return 전체 이벤트 페이지 방문횟수
	 */
	public String getVisitTotalCount(String date) {
		return this.jedis.get(KEY_EVENT_CLICK_DAILY_TOTAL + date);
	}

	/**
	 * 이벤트 아이디에 해당하는 요청된 날자들의 방문횟수를 조회한다.
	 * @param eventId 요청된 이벤트아이디 
	 * @param dateList 요청날자 목록
	 * @return 날자 목록에 대한 방문횟수 목록 
	 */
	public List<String> getVisitCountByDate(String eventId, String[] dateList) { 
		List<String> result = new ArrayList<String>();
		for (int i = 0; i < dateList.length; i++) {
			result.add(jedis.get(KEY_EVENT_CLICK_DAILY + dateList[i] + "" + eventId));
		}

		return result;
	}

	private String getToday() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return sdf.format(new Date());
	}
}
