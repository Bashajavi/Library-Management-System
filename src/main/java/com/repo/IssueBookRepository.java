package com.repo;

import com.entity.IssueBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IssueBookRepository extends JpaRepository<IssueBook, Long> {
    // You can add custom query methods if needed
}
