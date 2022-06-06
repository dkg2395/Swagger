package com.lti.todosmanagement.controller;

import java.io.IOException;
import java.net.URI;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.lowagie.text.DocumentException;
import com.lti.todosmanagement.entity.Todo;
import com.lti.todosmanagement.service.TodoService;
import com.lti.todosmanagement.util.PdfGenerator;
import com.lti.todosmanagement.util.TodoExcelExporter;

@RestController
@CrossOrigin(origins = "http://localhost:4200")

public class TodoController {
   @Autowired
    TodoService todoService;

   @GetMapping("/users/{username}/todos")
    public List<Todo> getAllTodos(@PathVariable  String username){
        return  todoService.findAll();

    }
   
   @GetMapping(value="/pdf/todo",produces= {"application/pdf"})
  	public void generatePdf(HttpServletResponse response) throws DocumentException, IOException {
  		
  		response.setContentType("application/pdf");
  		DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD:HH:MM:SS");
  		String currentDateTime = dateFormat.format(new Date());
  		String headerkey = "Content-Disposition";
  		String headervalue = "attachment; filename=pdf_" + currentDateTime + ".pdf";
  		response.setHeader(headerkey, headervalue);
  		
  		
  		List<Todo> todoList = todoService.findAll();
  		
  		PdfGenerator generator = new PdfGenerator();
  		
  		generator.setToDoList(todoList);
  		generator.generate(response);
  		
  	}
   
   @GetMapping("/todo/export/excel")
   public void exportToExcel(HttpServletResponse response) throws IOException {
       response.setContentType("application/octet-stream");
       DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
       String currentDateTime = dateFormatter.format(new Date());
        
       String headerKey = "Content-Disposition";
       String headerValue = "attachment; filename=Todos_" + currentDateTime + ".xlsx";
       response.setHeader(headerKey, headerValue);
        
       List<Todo> todoList = todoService.findAll();
        
       TodoExcelExporter excelExporter = new TodoExcelExporter(todoList);
        
       excelExporter.export(response);    
   }  

    @GetMapping("/users/{username}/todos/{id}")
    public Todo getSpecificTodos(@PathVariable  String username,@PathVariable long id){
        return  todoService.findById(id);

    }

    @DeleteMapping("/users/{username}/todos/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable String username,@PathVariable long id){
       Todo todo=todoService.deleteById(id);
       if(todo!=null){
           return  ResponseEntity.noContent().build();
       }
       return ResponseEntity.notFound().build();

    }

    @PutMapping("/users/{username}/todos/{id}")
    public ResponseEntity<Todo> updateTodo(@PathVariable String username,@PathVariable long id,@RequestBody Todo todo){
        Todo todoUpdated = todoService.Save(todo);
      //  return new ResponseEntity<Todo>(todo, HttpStatus.OK);
        return new ResponseEntity<Todo>(todoUpdated, HttpStatus.OK);
    }

    @PostMapping("/users/{username}/todos")
    public ResponseEntity<Todo> updateTodo(@PathVariable String username,@RequestBody Todo todo){
        Todo createdTodo = todoService.Save(todo);

        URI uri= ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(createdTodo.getId()).toUri();

        return  ResponseEntity.created(uri).build();

    }
}
