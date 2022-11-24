package com.learning.task.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learning.task.entity.Task;
import com.learning.task.repository.TaskRepository;
import com.learning.task.vo.TaskDescriptionNullException;
import com.learning.task.vo.TaskNotFoundException;

@RestController
@RequestMapping("/")
public class TaskContoller {
	
	@Autowired
	TaskRepository taskRepo;
	
	@PutMapping("/tasks/{id}")
	public ResponseEntity<Task> updateTask(@RequestBody Task task,@PathVariable Long id){
		
		if(task.getDescription() == null) {
			throw new TaskDescriptionNullException();
		}else {
			Optional<Task> resTaskOp = taskRepo.findById(id);
			if(resTaskOp.isPresent()) {
				Task t = resTaskOp.get();
				t.setDescription(task.getDescription());
				t.setPriority(task.getPriority());
				taskRepo.save(t);
			}else {
				throw new TaskNotFoundException();
			}
		}
		
		return  ResponseEntity.ok(task);
	}
	
	 @ExceptionHandler(value = TaskNotFoundException.class)
	   public ResponseEntity<Object> taskNotFound(TaskNotFoundException exception) {
	      return new ResponseEntity<>("Task Id not exists", HttpStatus.NOT_FOUND);
	   }
	 
	 @ExceptionHandler(value = TaskDescriptionNullException.class)
	   public ResponseEntity<Object> taskDesc(TaskDescriptionNullException exception) {
	      return new ResponseEntity<>("Desc not exists", HttpStatus.BAD_REQUEST);
	   }
}
