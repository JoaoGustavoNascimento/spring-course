package com.springcourse.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.springcourse.domain.Request;
import com.springcourse.domain.RequestStage;
import com.springcourse.domain.enums.RequestState;
import com.springcourse.exception.NotFoundException;
import com.springcourse.model.PageModel;
import com.springcourse.model.PageRequestModel;
import com.springcourse.repository.RequestRepository;
import com.springcourse.repository.RequestStageRepositiry;

@Service
public class RequestStageService {
	@Autowired
	private RequestStageRepositiry requestStageRepositiry;
	
	@Autowired
	private RequestRepository requestRepository;
	
	public RequestStage save(RequestStage stage) {
		stage.setRealizationDate(new Date());
		
		RequestStage createdStage = requestStageRepositiry.save(stage);
		
		Long requestId = stage.getRequest().getId();
		RequestState state =stage.getState();
		
		requestRepository.updateStatus(requestId, state);
		
		return createdStage;
	}
	
	public RequestStage getById(Long id) {
		Optional<RequestStage> result = requestStageRepositiry.findById(id);
		return result.orElseThrow(()-> new NotFoundException("There are not request stage with id =" + id));
	}
	
	public List<RequestStage> listAllByRequestId(Long requestId) {
		List<RequestStage> stages = requestStageRepositiry.findAllByRequestId(requestId);
		return stages;
	}
	
	public PageModel<RequestStage> listAllByRequestIdOnLazyModel (Long requestId, PageRequestModel pr) {
		Pageable pageable = PageRequest.of(pr.getPage(), pr.getSize());
		Page<RequestStage> page = requestStageRepositiry.findAllByRequestId(requestId, pageable);
		
		PageModel<RequestStage> pm = new PageModel<>((int)page.getTotalElements(), page.getSize(), page.getTotalPages(), page.getContent());
		return pm;
	}

}
