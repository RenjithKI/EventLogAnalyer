package com.renjith.ki;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.renjith.ki.domain.AppServerLogEntryObject;
import com.renjith.ki.domain.EventDetailsEnity;
import com.renjith.ki.utils.EventProcessorUtils;

public class EventProcessorUtilsTest{	

	private static String textFilePath = "C:\\ECLIPSESTS\\MiscWS\\new\\serverLog.txt";
	
	private static List<String> listring = null;
	private static List<AppServerLogEntryObject> liobj;

	@BeforeClass
	public static void initi(){
		try {
			listring = EventProcessorUtils.produceValidListFilesStream(textFilePath);
		} catch (IOException e) {		
			e.printStackTrace();
		}
		liobj = EventProcessorUtils.parseJsonObjFromString(listring);		
	}

	@Test
	public void produceValidListFilesStreamTest() throws IOException {	
		List<String> result = EventProcessorUtils.produceValidListFilesStream(textFilePath);

		assertNotNull(result);		
		assertEquals(6, result.size());		
	}

	@Test
	public void parseJsonObjFromStringTest() throws IOException {	
		List<AppServerLogEntryObject>  result = EventProcessorUtils.parseJsonObjFromString(listring);

		assertNotNull(result);		
		assertEquals(6, result.size());		
	}


	@Test
	public void getEventDetailListTest() throws IOException {	
		List<EventDetailsEnity>  result = EventProcessorUtils.getEventDetailList(liobj);

		assertNotNull(result);		
		assertEquals(3, result.size());		
	}


	@Test
	public void getEventDetailListParallelTest() throws IOException {	
		List<EventDetailsEnity>  result = EventProcessorUtils.getEventDetailListParallel(liobj);

		assertNotNull(result);		
		assertEquals(3, result.size());		
	}



}
