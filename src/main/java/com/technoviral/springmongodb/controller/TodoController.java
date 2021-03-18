package com.technoviral.springmongodb.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.technoviral.springmongodb.exception.todoCollectionException;
import com.technoviral.springmongodb.model.todoDto;
import com.technoviral.springmongodb.repository.TodoRepository;
import com.technoviral.springmongodb.service.TodoService;

@RestController
public class TodoController {
	
	@Autowired
	private TodoService todoService;

	@GetMapping("/todos")
	public ResponseEntity<?> getAllTodos() {
		List<todoDto> todos = todoService.getAllTodos();
		return new ResponseEntity<>(todos, todos.size() > 0 ? HttpStatus.OK : HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/todos/{id}")
	public ResponseEntity<?> getSingleTodos(@PathVariable("id") String id) {
		try{
			return new ResponseEntity<>(todoService.getTodoById(id), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/todos/{id}")
	public ResponseEntity<?> deleteTodoById(@PathVariable("id") String id) {
		try {
			todoService.deleteToDoById(id);
			return new ResponseEntity<>("Successfully deleted by id "+id, HttpStatus.OK);
		} catch (todoCollectionException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("/todos/{id}")
	public ResponseEntity<?> updateTodos(@PathVariable("id") String id,@RequestBody todoDto todobody) {
		try {
			todoService.updateTodo(id,todobody);
			return new ResponseEntity<>("Successfully updated by id "+id, HttpStatus.OK);
		} catch (ConstraintViolationException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
		} catch (todoCollectionException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/todos")
	public ResponseEntity<?> createTodo(@RequestBody todoDto todo) {
		try {
			todoService.createTodo(todo);
			return new ResponseEntity<todoDto>(todo, HttpStatus.OK);
		} catch (ConstraintViolationException e) {
			// TODO: handle exception
			return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
		} catch (todoCollectionException e) {
			// TODO: handle exception
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
