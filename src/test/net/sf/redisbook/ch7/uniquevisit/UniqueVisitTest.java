package net.sf.redisbook.ch7.uniquevisit;

import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.*;
import net.sf.redisbook.JedisHelper;
import org.junit.*;

public class UniqueVisitTest {
	static JedisHelper helper;
	private UniqueVisit uniqueVisit;
	private static final int VISIT_COUNT = 1000;
	private static final int TOTAL_USER = 10000000;
	private static final String TEST_DATE = "19500101";
	static Random rand = new Random();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		helper = JedisHelper.getInstance();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		helper.destoryPool();
	}

	@Before
	public void setUp() throws Exception {
		this.uniqueVisit = new UniqueVisit(helper);
		assertNotNull(this.uniqueVisit);
	}

	@Test
	public void testRandomPV() {
		int pv = this.uniqueVisit.getPVCount(getToday());
		for (int i = 0; i < VISIT_COUNT; i++) {
			this.uniqueVisit.visit(rand.nextInt(TOTAL_USER));
		}

		assertEquals(pv + VISIT_COUNT, this.uniqueVisit.getPVCount(getToday()));
	}

	@Test
	public void testInvalidPV() {
		assertEquals(0, this.uniqueVisit.getPVCount(TEST_DATE));
		assertEquals(new Long(0), this.uniqueVisit.getUVCount(TEST_DATE));
	}

	@Test
	public void testPV() {
		int result = this.uniqueVisit.getPVCount(getToday());
		this.uniqueVisit.visit(65487);

		assertEquals(result + 1, this.uniqueVisit.getPVCount(getToday()));
	}

	@Test
	public void testUV() {
		this.uniqueVisit.visit(65487);
		Long result = this.uniqueVisit.getUVCount(getToday());
		this.uniqueVisit.visit(65487);

		assertEquals(result, this.uniqueVisit.getUVCount(getToday()));
	}

	private String getToday() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return sdf.format(new Date());
	}
}
