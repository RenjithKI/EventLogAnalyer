package com.renjith.ki;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.Model;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EventLogAnalyerAppControllerTest {
	
	@Mock
	Model model;
	
	@Autowired
	EventLogAnalyerAppController eventLogAnalyerAppController;

	@Test
	public void controllerNotNull() {
		assertNotNull(eventLogAnalyerAppController);		
	}	

	@Test
	public void showEventDetailsTest() {
		String actualResult = eventLogAnalyerAppController.showEventDetails(model);
		assertEquals("Event-Details-List", actualResult);		
	}
	
	@Test
	public void homeTest() {
		String actualResult = eventLogAnalyerAppController.home();
		assertEquals("redirect:/home", actualResult);		
	}
	
	@Test
	public void indexTest() {
		String actualResult = eventLogAnalyerAppController.index();
		assertEquals("home", actualResult);		
	}
	

}
