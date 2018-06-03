package com.renjith.ki.init;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.renjith.ki.data.EventDetailsRepository;
import com.renjith.ki.domain.AppServerLogEntryObject;
import com.renjith.ki.domain.EventDetailsEnity;
import com.renjith.ki.utils.EventProcessorUtils;
/**
 * @author Renjith Kachappilly Ittoop
 *
 */
@Component
public class EventProcessorInitDataUpload {
	private static Logger logger = Logger.getLogger(EventProcessorInitDataUpload.class);
	
	final String textFilePath;

	final EventDetailsRepository eventDetailsRepository;

	@Autowired
	public EventProcessorInitDataUpload(@Value( "${textFile.path}" ) final String textFilePath, 
			EventDetailsRepository eventDetailsRepository) throws IOException{
		
		this.textFilePath = textFilePath;	
		this.eventDetailsRepository = eventDetailsRepository;

		//long start = System.nanoTime();

		List<String>  fullLines =  EventProcessorUtils.produceValidListFilesStream(textFilePath);
		List<AppServerLogEntryObject> logEntryList = EventProcessorUtils.parseJsonObjFromString(fullLines);
		List<EventDetailsEnity> detailEnitiesList  = EventProcessorUtils.getEventDetailListParallel(logEntryList);	

		//logger.debug("here is the answer is >>>" + detailEnitiesList);
		//EventProcessorUtils.printTimeused(start, " total");

		/*persist the Enity list in Database using Java Persistance */
		eventDetailsRepository.saveAll(detailEnitiesList);
		logger.info("<<<<<<<<<<Data is saved in the Database>>>>>>>>>");
		logger.debug("Number of Rows in Table is: "+ detailEnitiesList.size());

	}
	
}