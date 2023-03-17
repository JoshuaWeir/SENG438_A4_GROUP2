package org.jfree.data;

import static org.junit.Assert.*; import org.jfree.data.Range; import org.junit.*;

public class RangeTest {
    private Range exampleRange;
    private Range demoRange;
    private Range nullRange;
	
    @BeforeClass public static void setUpBeforeClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception { 
	exampleRange = new Range(-1, 1);
	demoRange = new Range (-5,5); 
	nullRange = null;
    }
    
    //ctor tests
    @Test
    public void testCorrectLowerBound() {
    	assertEquals("Test lower bound on range", -5, demoRange.getLowerBound(), .000000001d);
    }
    
    @Test
    public void testCorrectUpperBound() {
    	assertEquals("Test lower bound on range", 5, demoRange.getUpperBound(), .000000001d);
    }
    
    @Test
    public void testLowerGreaterThanUpper() {
    	try {
    		Range r = new Range(5,2);
    		fail("No constructor exception thrown");
    	} catch(IllegalArgumentException e) {
    	}
    }
    
    @Test
    public void testCtorExceptionMessage() {
    	String expectedMsg = "Range(double, double): require lower (5.0) <= upper (2.0).";
    	try {
    		Range r = new Range(5,2);
    		fail("No constructor exception thrown");
    	} catch(IllegalArgumentException e) {
    		assertTrue("Test exception message", expectedMsg.equals(e.getMessage()));
    	}
    }

    @Test
    public void firstNullValueGiven() {
    	Range expected = new Range(3.8, 10.0);
    	Range actual = Range.combine(null, expected);
        assertEquals("One null value given return should be equal to the non-null value",
        expected, actual);
    }

    @Test
    public void secondNullValueGiven() {
        Range expected = new Range(1.1, 4.5);
        Range actual = Range.combine(expected, null);
        assertEquals("One null value given return should be equal to the non-null value",
        expected, actual);
    }
    
    @Test
    public void bothNullGiven() {
    	Range actual = Range.combine(null, null);
    	assertNull("Two null values given should return null",
    	actual);
    }
    
    @Test
    public void incompleteOverlapRange2LessThanRange1() {
    	Range r1 = new Range(3, 8);
    	Range r2 = new Range(1, 5);
    	Range actual = Range.combine(r1, r2);
    	Range expected = new Range(1, 8);
    	assertEquals("Ranges of incomplete overlap", expected, actual);
    }
    
    @Test ()
    public void incompleteOverlapRange1LessThanRange2() {
    	try {
    	Range r1 = new Range(1, 9);
    	Range r2 = new Range(5, 15);
    	Range actual = Range.combine(r1, r2);
    	Range expected = new Range(1, 15);
    	assertEquals("Ranges of incomplete overlap", expected, actual);
		}
		catch(Exception e) {
			fail("Unexpected exception was thrown: " + e.getMessage());
		}
    }
    
    @Test
    public void noOverlapOnRanges() {
    	Range r1 = new Range(10, 15);
    	Range r2 = new Range(1, 5);
    	Range actual = Range.combine(r1, r2);
    	Range expected = new Range(1,15);
    	assertEquals("Ranges do not overlap",
    	expected, actual);
    }
    
    @Test
    public void rangesEqual() {
    	Range r1 = new Range(1.1, 2.4);
    	Range r2 = new Range(1.1, 2.4);
    	Range actual = Range.combine(r1, r2);
    	assertEquals("Ranges are equal",
    	r1, actual);
    	
    }
    
    @Test
    public void completeOverlap() {
    	try {
    	Range r1 = new Range(1.6, 7.5);
    	Range r2 = new Range(3.2, 4.5);
    	Range expected = new Range(1.6, 7.5);
    	Range actual = Range.combine(r1, r2);
    	assertEquals("One range fully encloses the other",
    	expected, actual);
    	}
    	catch(Exception e) {
    		fail("Unexpected exception was thrown: " + e.getMessage());
    	}

    }
    
    @Test
    public void oneNegativeRange() {
    	Range r1 = new Range(3.3, 5.3);
    	Range r2 = new Range(-1.0, 3.6);
    	Range expected = new Range(-1.0, 5.3);
    	Range actual = Range.combine(r1, r2);
    	assertEquals("One range was given a negative lower bound",
    	expected, actual);
    }
    
    @Test
    public void bothNegative() {
    	Range r1 = new Range(-3.2, -0.7);
    	Range r2 = new Range(-4.5, -0.5);
    	Range expected = new Range(-4.5, -0.5);
    	Range actual = Range.combine(r1, r2);
    	assertEquals("Both ranges where given negative values",
    	expected, actual);
    }
	
    @Test
    public void GetLowerBoundFunctionality() {
        assertEquals("The lower bound value of -1 and 1 should be -1",
        -1, exampleRange.getLowerBound(), .000000001d);
    }
	
    @Test
    public void GetOutsideLowerBound() {
    	assertFalse("The range should not be less than -1",exampleRange.contains(-2));
    }
	
    @Test
    public void GetLowerBoundEquivUpperBound() {
    	Range example = new Range(1,1);
        assertEquals("The lower bound value of 1 and 1 should be 1",
        1, example.getLowerBound(), .000000001d);
    }
	
    //contains() tests
	@Test
	public void containsValueInRange() {
		assertTrue("Value of 3 should be true as it is in range.", demoRange.contains(3));
	}
	@Test
	public void containsValueOutsideRangeHigh() {
		assertFalse("Value of 8 should be false as it is not in range.", demoRange.contains(8));
	}
	@Test
	public void containsValueOutsideRangeLow() {
		assertFalse("Value of -8 should be false as it is not in range.", demoRange.contains(-8));
	}
	@Test
	public void containsValueIsLowerBound() {
		assertTrue("Value of -5 should be true as it is in range.", demoRange.contains(-5));
	}
	@Test
	public void containsValueIsUpperBound() {
		assertTrue("Value of 5 should be true as it is in range.", demoRange.contains(5));
	}
	
	//toString() tests
	@Test
	public void correctString() {
		assertEquals("Function should return correct string.","Range[-5.0,5.0]",demoRange.toString());
	}
	@Test
	public void nullString() {
		try {
			nullRange.toString();
		}
		catch(NullPointerException e) {
			//works as intended if it throws the exception
		}
	}
	
	//intersects() tests
	@Test
	public void testLowerIteration() {
		assertTrue("Unwanted iteration.", demoRange.getLowerBound() == -5);
	}
	@Test
	public void testUpperIteration() {
		assertTrue("Unwanted iteration.", demoRange.getUpperBound() == 5);
	}
	@Test
	public void intersectLowerBound() {
		assertTrue("Range should overlap on lower bound.",demoRange.intersects(-10, -5));
	}
	@Test
	public void intersectUpperBound() {
		assertTrue("Range should overlap on upper bound.",demoRange.intersects(5, 15));
	}
	@Test
	public void intersectLowerHalf() {
		assertTrue("Range should overlap on lower half.",demoRange.intersects(-10, -2));
	}
	@Test
	public void intersectUpperHalf() {
		assertTrue("Range should overlap on upper half.",demoRange.intersects(2, 10));
	}
	@Test
	public void noIntersectLower() {
		assertFalse("Range should not overlap under the lower bound.",demoRange.intersects(-15, -7));
	}
	@Test
	public void noIntersectUpper() {
		assertFalse("Range should not overlap over the upper bound.",demoRange.intersects(7, 15));
	}
	@Test
	public void oneValueInRange() {
		assertTrue("Range of (3,3) should intersect.",demoRange.intersects(3, 3));
	}
	@Test
	public void oneValueNotInRange() {
		assertFalse("Range of (8,8) should not intersect.",demoRange.intersects(8, 8));
	}
	@Test
	public void intersectNullException() {
		try {
			nullRange.intersects(0, 5);
		}
		catch(NullPointerException e) {
			//works as intended if it throws the exception
		}
	}
	@Test
	public void completeIntersectInside() {
		assertTrue("Range encapsulated inside should intersect.",demoRange.intersects(-3, 3));
	}
	@Test
	public void completeIntersectOutside() {
		assertTrue("Range encapsulating outside should intersect.",demoRange.intersects(-13, 13));
	}
	
	//NEW
	@Test
	public void partialIntersectRight() {
		Range right = new Range(3,8);
		assertTrue("Range should intersect.",demoRange.intersects(right));
	}
	@Test
	public void partialIntersectLeft() {
		Range left = new Range(-8,-3);
		assertTrue("Range should intersect.",demoRange.intersects(left));
	}
	
	@Test
    public void firstNullValueGivenNaN() {
    	Range expected = new Range(3.8, 10.0);
    	Range actual = Range.combineIgnoringNaN(null, expected);
        assertEquals("One null value given return should be equal to the non-null value",
        expected, actual);
    }

    @Test
    public void secondNullValueGivenNaN() {
        Range expected = new Range(2.4, 2.8);
        Range actual = Range.combineIgnoringNaN(expected, null);
        assertEquals("One null value given return should be equal to the non-null value",
        expected, actual);
    }
    
    @Test
    public void bothNullGivenNaN() {
    	Range actual = Range.combineIgnoringNaN(null, null);
    	assertNull("Two null values given should return null",
    	actual);
    }
    @Test
    public void incompleteOverlapRange2LessThanRange1NaN() {
    	Range r1 = new Range(2, 4);
    	Range r2 = new Range(1, 3);
    	Range actual = Range.combineIgnoringNaN(r1, r2);
    	Range expected = new Range(1, 4);
    	assertEquals("Ranges of incomplete overlap", expected, actual);
    }
    @Test
    public void bothNaNGiven() {
    	Range isNaN = new Range(Double.NaN,Double.NaN);
    	Range actual = Range.combineIgnoringNaN(isNaN, isNaN);
    	assertNull("Two NaN values given should return null",
    	actual);
    }
    @Test
    public void OneNullTwoNaN() {
    	Range isNaN = new Range(Double.NaN,Double.NaN);
    	Range actual = Range.combineIgnoringNaN(isNaN, null);
    	assertNull("One NaN value given should return null",
    	actual);
    }
    @Test
    public void TwoNullOneNaN() {
    	Range isNaN = new Range(Double.NaN,Double.NaN);
    	Range actual = Range.combineIgnoringNaN(null, isNaN);
    	assertNull("One NaN value given should return null",
    	actual);
    }
    
    @Test
    public void getUpperBoundFuncionality() {
    	assertEquals("Upper bound should be 5.", 5, demoRange.getUpperBound(), .000000001d);
    }
    @Test(expected = IllegalArgumentException.class)
    public void createValuesBackwards() {
    	Range r = new Range(5,-5);
    }
    @Test
    public void testNaNRange() {
    	Range nanRange = new Range(Double.NaN,Double.NaN);
    	assertTrue("Range is NaN.",nanRange.isNaNRange());
    }
	
//Tests for equals

	@Test
	public void twoobjectsEqual() {
		Range r1 = new Range(0, 1.1);
		Range r2 = new Range(0, 1.1);
		boolean expected = true;
		boolean actual = r1.equals(r2);
		assertEquals("Two Equals ranges given", expected, actual);
	}
	
	@Test public void twoObjectsNotEqualUpperTest() {
		Range r1 = new Range(0, 1.1);
		Range r2 = new Range(0, 1.2);
		boolean expected = false;
		boolean actual = r1.equals(r2);
		assertEquals("Two Equals ranges given", expected, actual);		
	} 
	
	@Test public void twoObjectsNotEqualLowerTest() {
		Range r1 = new Range(0, 1.1);
		Range r2 = new Range(1, 1.1);
		boolean expected = false;
		boolean actual = r1.equals(r2);
		assertEquals("Two Equals ranges given", expected, actual);		
	} 
	
	@Test
	public void oneObjectNotRangeTest() {
		Range r1 = new Range(0,1.1);
		KeyedObjects2D u1 = new KeyedObjects2D();
		boolean expected = false;
		boolean actual = r1.equals(u1);
		assertEquals("One object is not of type Range", expected, actual);
	}
	
	//Tests for getCentralValue
	
	@Test
	public void preventLowerIteration() {
		Range r = new Range(1,2);
		r.getCentralValue();
		assertTrue("Test Iterative Mutants", (r.getLowerBound() == 1));
	}
	
	@Test
	public void preventUpperIteration() {
		Range r = new Range(1,2);
		r.getCentralValue();
		assertTrue("Test Iterative Mutants", (r.getUpperBound() == 2));
	}
	
	@Test
	public void getCentralValueTest() {
		Range r = new Range(1,2);
		double expected = 1.5;
		double actual = r.getCentralValue();
		assertEquals("Testing proper central value.", expected, actual, 0.000001d);
	}
	
	//Tests for getLength
	@Test
	public void getLengthValueTest() {
		Range r = new Range(1,2);
		double expected = 1.0;
		double actual = r.getLength();
		assertEquals("Testing proper length value.", expected, actual, 0.000001d);
	}	
	
	//Tests for constrain
	
	@Test
	public void valueBelowBoundsTest() {
		double val = 1.2;
		Range r = new Range(2, 3);
		double actual = r.constrain(val);
		double expected = 2.0;
		assertEquals("Value was below lower bounds", expected, actual, 0.000001d);
	}

	@Test
	public void valueAboveBoundsTest() {
		double val = 3.2;
		Range r = new Range(2, 3);
		double actual = r.constrain(val);
		double expected = 3.0;
		assertEquals("Value was below lower bounds", expected, actual, 0.000001d);
	}
	
	@Test
	public void valueInsideBoundsTest() {
		double val = 2.5;
		Range r = new Range(2, 3);
		double actual = r.constrain(val);
		double expected = 2.5;
		assertEquals("Value was below lower bounds", expected, actual, 0.000001d);
	}
	
	//Tests for expand to include
	
	@Test
	public void nullRangeGivenTest() {
		double val = 2.5;
		Range r = null;
		Range actual = Range.expandToInclude(r, val);
		Range expected = new Range(2.5, 2.5);
		assertEquals("Null range given range return to just be value given", expected, actual);
	}
	
	@Test
	public void valueLessThanLowerTest() {
		double val = 2.5;
		Range r = new Range(4, 5);
		Range actual = Range.expandToInclude(r, val);
		Range expected = new Range(2.5, 5);
		assertEquals("Value was less than lower bound of range", expected, actual);
	}
	
	@Test
	public void valueGreaterThanUpperTest() {
		double val = 7.5;
		Range r = new Range(4, 5);
		Range actual = Range.expandToInclude(r, val);
		Range expected = new Range(4, 7.5);
		assertEquals("Value was greater than upper bound of range", expected, actual);
	}
	
	@Test
	public void valueInBetweenTest() {
		double val = 4.5;
		Range r = new Range(4, 5);
		Range actual = Range.expandToInclude(r, val);
		Range expected = new Range(4, 5);
		assertEquals("Value was in bewteen the range bounds", expected, actual);
	}
	
	//Tests for Scale
	
	@Test(expected = IllegalArgumentException.class)
	public void factorIsNegativeTest() {
		double factor = -1;
		Range r = new Range(1, 2);
		Range actual = Range.scale(r, factor);
	}
	
	@Test
	public void validFactorTest() {
		double factor = 2;
		Range r = new Range(1,2);
		Range actual = Range.scale(r, factor);
		Range expected = new Range(2, 4);
		assertEquals("Should Properly scale the factor", actual, expected);
	}
	
		@Test
	public void zeroCrossingShiftTest() {
		double delta = 2.5;
		Range r = new Range(-2, 7.5);
		Range actual = Range.shift(r, delta, true);
		Range expected = new Range(0.5, 10.0);
		assertEquals("Shift while crossing zero", expected, actual);
	}
	
	@Test
	public void noZeroCrossing() {
		Range r = new Range(-1.0, 2.0);
		double delta = 0.5;
		Range actual = Range.shift(r, delta);
		Range expected = new Range(-0.5, 2.5);
		assertEquals("Range shifted with no zero crossing", expected, actual);
	}
	
	@Test
	public void noZeroCrossing2() {
		Range r = new Range(-1, 0);
		double delta = 2;
		Range actual = Range.shift(r, delta);
		Range expected = new Range(0, 2);
		assertEquals("Range shifted with zero crossing but not permitted", expected, actual);
	}
	
	//Tests for hashcode
	
	@Test
	public void hashcodeTest() {
		Range r = new Range(1,2);
		int actual = r.hashCode();
        int expected;
        long temp;
        temp = Double.doubleToLongBits(1);
        expected = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(2);
        expected = 29 * expected + (int) (temp ^ (temp >>> 32));
        assertEquals("Hashcode being generated", expected, actual);
	}
	
	@Test
    public void OneNaNRange() {
    	Range isNaN = new Range(Double.NaN,Double.NaN);
    	Range actual = Range.combineIgnoringNaN(demoRange,isNaN);
    	assertEquals("Result range should be demoRange.", demoRange, actual);
    }
	
	@Test
	public void preventLowerNaNIteration() {
		Range r = new Range(1,2);
		r.isNaNRange();
		assertTrue("Test Iterative Mutants", (r.getLowerBound() == 1));
	}
	
	@Test
	public void preventUpperNaNIteration() {
		Range r = new Range(1,2);
		r.isNaNRange();
		assertTrue("Test Iterative Mutants", (r.getUpperBound() == 2));
	}
	
	//tests for expand with margins
	
	@Test
	public void expandPercentile() {
		Range evenTen = new Range (-10,10);
		Range actual = Range.expand(evenTen, 0.1, 0.1);
		Range expected = new Range(-12,12);
		assertEquals("New Range should be (-12,12) after adding 10% margins.",expected,actual);
	}
	
	@Test
	public void expandPercentileBackwards() {
		Range evenTen = new Range (-10,10);
		Range actual = Range.expand(evenTen, -1.1, 0);
		Range expected = new Range(11,11);
		assertEquals("New Range should be (11,11) after adding margins.",expected,actual);
	}	


    @After
    public void tearDown() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }
}
