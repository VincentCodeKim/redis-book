package net.sf.redisbook.ch7.recentview;
import static org.junit.Assert.*;
import java.util.Set;
import net.sf.redisbook.JedisHelper;
import org.junit.*;

public class RecentViewListTestV2 {
	static JedisHelper helper;
	private RecentViewListV2 viewList;
	private static final String TEST_USER = "123";
	private int listMaxSize;

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
		this.viewList = new RecentViewListV2(helper, TEST_USER);
		assertNotNull(this.viewList);
		this.listMaxSize = this.viewList.getListMaxSize();
	}

	@Test
	public void testAdd() {
		for (int i = 1; i <= 50; i++) {
			this.viewList.add(String.valueOf(i));
		}
	}

	@Test
	public void checkMaxSize() {
		int storedSize = this.viewList.getRecentViewList().size();
		assertEquals(this.listMaxSize, storedSize);
	}

	@Test
	public void checkRecentSize() {
		int checkSize = 4;
		int redisSize = this.viewList.getRecentViewList(checkSize).size();

		assertEquals(redisSize, checkSize);
	}

	@Test
	public void checkProductNo() {
		this.viewList.add("45");
		assertEquals(this.viewList.getRecentViewList().size(), this.listMaxSize);
		Set<String> itemList = this.viewList.getRecentViewList(5);
		
		for(String item: itemList)	{
			System.out.println(item);
		}
		
		String[] list = itemList.toArray(new String[0]);
		assertEquals("45", list[0]);
	}
}
