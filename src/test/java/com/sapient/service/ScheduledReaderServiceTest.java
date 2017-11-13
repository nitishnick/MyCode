package com.sapient.service;

public class ScheduledReaderServiceTest {
	
	private String path=null;
	IScheduledReaderService readerService = null;
   /* @Before
    public void initData() {
    	path="C:\\readFolder\\FilePaths.txt";
    	
    	ConfigurableApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath:application-main-config.xml");
		readerService = (ScheduledReaderService) context.getBean("scheduledReaderService");
		
    }
    
    @Test
    public void shouldReturnEmptyList_IfSchedulerNotWorks() throws SAPException {
    	List<TradeData> l = readerService.getTradeData();
    	Assert.assertEquals(l.isEmpty(), true);
    	
    }
    
    
//    @Test(expected = SAPException.class)
    public void givenInvalidPath_ThrowsException() {
    	Method m = null;
    	Class c = null;
		c = ScheduledReaderService.class;
        try {
			 m = c.getDeclaredMethod("readAllFilePathsFromConsolidated", new Class[] { String.class });
		} catch (NoSuchMethodException e1) {
			e1.printStackTrace();
		} catch (SecurityException e1) {
			e1.printStackTrace();
		}
        m.setAccessible(true);
        Object i = null;
		try {
			i = c.newInstance();
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		}
        try {
        	
			Object r = m.invoke(i, new Object[] { "Hello"});
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		} catch (IllegalArgumentException e1) {
			e1.printStackTrace();
		} catch (InvocationTargetException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    }*/

}
