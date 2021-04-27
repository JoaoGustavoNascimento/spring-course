package com.springcourse.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springcourse.domain.RequestStage;

@Repository
public interface RequestStageRepositiry extends JpaRepository<RequestRepository, Long> {

	public List<RequestStage> findAllByRequestId(Long id);
}
