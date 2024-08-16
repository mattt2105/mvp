package com.books_service.books_service.repository;

import com.books_service.books_service.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
