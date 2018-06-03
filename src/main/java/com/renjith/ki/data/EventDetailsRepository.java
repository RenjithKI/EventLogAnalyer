package com.renjith.ki.data;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.renjith.ki.domain.EventDetailsEnity;

public interface EventDetailsRepository extends JpaRepository<EventDetailsEnity, String> {
	
	List<EventDetailsEnity> findByEventid(String id);
	
	List<EventDetailsEnity> findByEventDuration(String eventDuration);
	
	List<EventDetailsEnity> findAll();

}