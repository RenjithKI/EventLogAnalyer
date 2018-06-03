package com.renjith.ki.utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.renjith.ki.domain.AppServerLogEntryObject;
import com.renjith.ki.domain.EventDetailsEnity;
/**
 * @author Renjith Kachappilly Ittoop
 *I/O using Files stream
 */

public class EventProcessorUtils {

	static Predicate<String> fullLinePredicate  = (s)-> !s.isEmpty() && !s.equals("") && s.startsWith("{") && s.endsWith("}") && s.contains("id");
	static Predicate<String> startingHalfLine  = (s)-> !s.isEmpty() && !s.equals("") && s.startsWith("{") ;
	static Predicate<String> endingHalfLinepredicate  = (s)-> !s.isEmpty() && !s.equals("") && s.endsWith("}");
	

	public static List<EventDetailsEnity> getEventDetailListParallel(List<AppServerLogEntryObject> objList) {		

		ConcurrentMap<String, List<AppServerLogEntryObject>> groupedByIdMap =
				objList.parallelStream().collect(Collectors.groupingByConcurrent(AppServerLogEntryObject::getId));

		List<EventDetailsEnity> mappedDetailObjList = groupedByIdMap.entrySet()
				.stream().parallel()
				.map(p -> {
					Optional<AppServerLogEntryObject> q = 
							p.getValue().stream().findFirst();
					EventDetailsEnity detail = new EventDetailsEnity();
					if(q.isPresent() ) {					
						detail.setEventid(q.get().getId());					
						detail.setType(q.get().getType() );
						detail.setHost(	q.get().getHost());
						Long y =  p.getValue().stream().limit(2).mapToLong( m -> m.getTimestamp() ).reduce((a, b) -> Math.abs(a-b) ).orElse(0);
						detail.setEventDuration(y);
						detail.setAlert(y>4);
					}
					return detail;
				})
				.collect(Collectors.toList());

		return mappedDetailObjList;		
	}

	public static List<EventDetailsEnity> getEventDetailList(List<AppServerLogEntryObject> objList) {

		Map<String, List<AppServerLogEntryObject>> groupedByIdMap =
				objList.stream().collect(Collectors.groupingBy(AppServerLogEntryObject::getId));

		List<EventDetailsEnity> mappedDetailObjList = groupedByIdMap.entrySet()
				.stream()   
				.map(p -> {
					Optional<AppServerLogEntryObject> q = 
							p.getValue().stream().findFirst();
					EventDetailsEnity detail = new EventDetailsEnity();
					if(q.isPresent() ) {					
						detail.setEventid(q.get().getId());					
						detail.setType(q.get().getType() );
						detail.setHost(	q.get().getHost());
						Long y =  p.getValue().stream().limit(2).mapToLong( m -> m.getTimestamp() ).reduce((a, b) -> Math.abs(a-b) ).orElse(0);
						detail.setEventDuration(y);
						detail.setAlert(y>4);
					}
					return detail;
				})
				.collect(Collectors.toList());

		return mappedDetailObjList;		
	}		

	public static List<String> produceValidListFilesStream(final String textFilePath) throws IOException {

		List<String> stringList = new ArrayList<>();/*can set the capacity to a large number for bulk operation*/

		try (Stream<String> linesStream = Files.lines( Paths.get(textFilePath), StandardCharsets.UTF_8 )) {
			
			List<String> lines = linesStream.parallel().filter(Objects::nonNull).collect(Collectors.toList());
			String temp = null;
			
			for (String line : lines ) {			
				String st = line.trim();
				if (fullLinePredicate.test(st)) 
				{					
					stringList.add(st);						
					temp = "";
				}else if (startingHalfLine.test(st)) {					
					temp = st;

				}else if (endingHalfLinepredicate.test(st) && !temp.equals("")) {					
					temp = temp + st;
					stringList.add(temp);									
				}
			}			
		}		
		return stringList;
	}	
	
	public static List<AppServerLogEntryObject> parseJsonObjFromString(List<String>  lines) {
			
		ObjectMapper mapper = new ObjectMapper();
		
		List<AppServerLogEntryObject> list = lines
				.stream()
				.parallel()				
				.map(line -> {			
					AppServerLogEntryObject obj = null;				
					if (line.contains("type")){
						String [] typeSplit= line.split("type");						
						line= Stream.of(typeSplit).reduce((a, b) -> a +" \"type\" "+ b).get();

						String [] hostSplit= line.split("host");						
						line= Stream.of(hostSplit).reduce((a, b) -> a +" \"host\" "+ b).get();						
					}
					try {
						obj = mapper.readValue(line, AppServerLogEntryObject.class);
					} catch (JsonGenerationException e) {
						e.printStackTrace();
					} catch (JsonMappingException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} 

					return obj;
				}).collect(Collectors.toList());
		
		return list;
	}

	public static void printTimeused(long start, String label) {
		long timeTaken = System.nanoTime()-start;		
		long elapsed = TimeUnit.MILLISECONDS.convert((timeTaken), TimeUnit.NANOSECONDS);	
		System.out.println("  >>>>>>>>>>  "+ label + " ______________> "+ elapsed +"  Milli  SECONDS");		
	}
}