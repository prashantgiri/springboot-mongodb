package com.technoviral.springmongodb.service;

import java.util.List;

import javax.validation.ConstraintViolationException;

import com.technoviral.springmongodb.exception.todoCollectionException;
import com.technoviral.springmongodb.model.todoDto;

public interface TodoService {

	public void createTodo(todoDto todo) throws ConstraintViolationException, todoCollectionException;
	
	public List<todoDto> getAllTodos();

	public todoDto getTodoById(String id) throws todoCollectionException;
	
	public void updateTodo(String id, todoDto todo) throws todoCollectionException; 
	
	public void deleteToDoById(String id) throws todoCollectionException;
}
