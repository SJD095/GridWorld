import static org.junit.Assert.*;

import java.util.Stack;

import org.junit.Before;
import org.junit.Test;

import info.gridworld.grid.Location;

public class LocationNodeTest {
	
    LocationNode locN;
    Location loc = new Location(1, 3);

	@Before
	public void setUp() throws Exception {
		
		Stack<Location> newStack = new Stack<Location>();
		locN = new LocationNode(loc, true, null, newStack);
	}

	@Test
	public void testLocationNode() {
		
		Location loc2 = new Location(3, 3);
		LocationNode locN2 = new LocationNode(loc2, false, null, null);
		
		assertEquals(loc2, locN2.getLoc());
		assertEquals(false, locN2.isSuperNode());
		assertEquals(null, locN2.getLastNode());
	}

	@Test
	public void testGetLoc() {
		assertEquals(loc, locN.getLoc());
	}

	@Test
	public void testIsSuperNode() {
		assertEquals(true, locN.isSuperNode());
	}

	@Test
	public void testGetLastNode() {
		assertEquals(null, locN.getLastNode());
	}

}
