package com.ftn.security.smarthomebackend.repository.mongo;

import com.ftn.security.smarthomebackend.model.Log;
import org.springframework.data.mongodb.repository.MongoRepository;
public interface LogRepository extends MongoRepository<Log, Long>{
}
