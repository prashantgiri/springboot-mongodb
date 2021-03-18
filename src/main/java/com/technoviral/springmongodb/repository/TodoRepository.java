package com.technoviral.springmongodb.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import com.technoviral.springmongodb.model.todoDto;

@Repository
public interface TodoRepository extends MongoRepository<todoDto, String> {

	@Query("{'todo':?0}")
	Optional<todoDto> findByTodo(String todo);
}
