package com.renjith.ki;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.renjith.ki.data.EventDetailsRepository;
import com.renjith.ki.domain.EventDetailsEnity;

/**
 * @author Renjith Kachappilly Ittoop
 * http://localhost:8080/Event-Details
 * http://localhost:8080/home
 * http://localhost:8080/
 * 
 *
 */
@Controller
public class EventLogAnalyerAppController {

	private static Logger logger = Logger.getLogger(EventLogAnalyerAppController.class);
	
	private EventDetailsRepository eventDetailsRepository;	

	/**
	 * @param eventDetailsRepository
	 */
	@Autowired
	public EventLogAnalyerAppController(EventDetailsRepository eventDetailsRepository) {		
		this.eventDetailsRepository = eventDetailsRepository;
	}
	/**
	 *
	 * @return view
	 */
	@RequestMapping(value="/Event-Details", method=RequestMethod.GET)
	public String showEventDetails(Model model) {		
		List<EventDetailsEnity> alllist = eventDetailsRepository.findAll();		
		if (alllist != null) {
			logger.info("inside allDetails method size>>>>"+ alllist.size() );
			model.addAttribute("allDetailsList", alllist);
		}
		return "Event-Details-List";
	}
	
	 /**
	  *
	  * @return home view
	  */
	 @RequestMapping("/")
	 public String home() {
		 return "redirect:/home";
	 }

	 @RequestMapping("/home")
	 public String index() {
		 return "home";
	 }

	 /**
	  *
	  * @return error view
	  */
	 @ExceptionHandler(value=RuntimeException.class)
	 @ResponseStatus(value=HttpStatus.BANDWIDTH_LIMIT_EXCEEDED)
	 public String error() {
		 return "error";
	 }

}
