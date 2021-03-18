package com.technoviral.springmongodb.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.technoviral.springmongodb.exception.todoCollectionException;
import com.technoviral.springmongodb.model.todoDto;
import com.technoviral.springmongodb.repository.TodoRepository;

@Service
public class TodoServiceImpl implements TodoService {
	
	@Autowired
	private TodoRepository todoRepository;

	@Override
	public void createTodo(todoDto todo) throws ConstraintViolationException, todoCollectionException {
		Optional<todoDto> tempTodoObject = todoRepository.findByTodo(todo.getTodo());
		if(tempTodoObject.isPresent()) {
			throw new todoCollectionException(todoCollectionException.AlreadyExists());
		}else {
			todo.setCreatedAt(new Date(System.currentTimeMillis()));
			todoRepository.save(todo);
		}
		
	}

	@Override
	public List<todoDto> getAllTodos() {
		List<todoDto> todos = todoRepository.findAll();
		if (todos.size() > 0) {
			return todos;
		} else {
			return new ArrayList<todoDto>();
		}
	}

	@Override
	public todoDto getTodoById(String id) throws todoCollectionException {
		Optional<todoDto> todo = todoRepository.findById(id);
		if (!todo.isPresent()) {
			throw new todoCollectionException(todoCollectionException.NotFoundException(id));
		} else {
			return todo.get();
		}
	}

	@Override
	public void updateTodo(String id, todoDto todobody) throws todoCollectionException {
		Optional<todoDto> todoWithId = todoRepository.findById(id);
		Optional<todoDto> todoWithSamename = todoRepository.findByTodo(todobody.getTodo());
		if (todoWithId.isPresent()) {
			
			if (todoWithSamename.isPresent() && !todoWithSamename.get().getId().equals(id)) {
				throw new todoCollectionException(todoCollectionException.AlreadyExists());
			}
			
			todoDto todoToSave = todoWithId.get();
			todoToSave.setComplete(todobody.getComplete() != null ? todobody.getComplete() : todoToSave.getComplete());
			todoToSave.setDescription(todobody.getDescription() != null ? todobody.getDescription() : todoToSave.getDescription());
			todoToSave.setTodo(todobody.getTodo() != null ? todobody.getTodo() : todoToSave.getTodo());
			todoToSave.setUpdatedAt(new Date(System.currentTimeMillis()));
			todoRepository.save(todoToSave);
		} else {
			throw new todoCollectionException(todoCollectionException.NotFoundException(id));
		}
	}

	@Override
	public void deleteToDoById(String id) throws todoCollectionException {
		Optional<todoDto> temptodo = todoRepository.findById(id);
		if (!temptodo.isPresent()) {
			throw new todoCollectionException(todoCollectionException.NotFoundException(id));
		} else {
			todoRepository.deleteById(id);
		}
		
	}
	

}
