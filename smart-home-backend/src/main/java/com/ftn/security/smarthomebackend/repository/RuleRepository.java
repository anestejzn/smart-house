package com.ftn.security.smarthomebackend.repository;

import com.ftn.security.smarthomebackend.model.Rule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RuleRepository extends JpaRepository<Rule, Long> {
}
