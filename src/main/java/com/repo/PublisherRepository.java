package com.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.entity.Publisher;

public interface PublisherRepository extends JpaRepository<Publisher, Long> {
	
}
