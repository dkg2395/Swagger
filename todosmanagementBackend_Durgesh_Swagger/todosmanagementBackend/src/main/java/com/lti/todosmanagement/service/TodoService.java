package com.lti.todosmanagement.service;

import com.lti.todosmanagement.entity.Todo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TodoService {

    private static List<Todo> todos = new ArrayList<>();
    private static int idCounter = 0;

    static {
        todos.add(new Todo(++idCounter, "Durgesh", "learn java", new Date(), false));
        todos.add(new Todo(++idCounter, "SAM", "learn Hibernate", new Date(), false));
        todos.add(new Todo(++idCounter, "Prashant", "learn java and angular", new Date(), false));
        todos.add(new Todo(++idCounter, "Durgesh", "learn java", new Date(), false));
        todos.add(new Todo(++idCounter, "Ekta", "learn Hibernate", new Date(), false));
        todos.add(new Todo(++idCounter, "SBNM", "learn java and angular", new Date(), false));


    }

    public List<Todo> findAll() {
        return todos;
    }

    public  Todo Save(Todo todo){
        if(todo.getId()==-1 || todo.getId()==0){
            todo.setId(++idCounter);
            todos.add(todo);
        }else
        {
            deleteById(todo.getId());
            todos.add(todo);
        }
        return todo;
    }


    public Todo deleteById(long id) {
        Todo todo = findById(id);

        if(todo==null) return  null;

        if(todos.remove(todo)){
        return  todo;
    }
    return  null;
    }


    public  Todo findById(long id){
        for(Todo todo:todos){
            if(todo.getId()==id){
                return  todo;
            }
        }
        return  null;
    }
}

