package tlswltjq.todoDiary.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tlswltjq.todoDiary.model.TodoEntity;
import tlswltjq.todoDiary.repository.TodoRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;

    public String testService(){
        TodoEntity entity = TodoEntity.builder().title("First created ITEM").build();
        todoRepository.save(entity);
        TodoEntity savedEntity  = todoRepository.findById(entity.getId()).get();
        return savedEntity.getTitle();
    }

    public List<TodoEntity> create(final TodoEntity entity){
        validate(entity);
        todoRepository.save(entity);
        log.info("Entity id : {} is saved", entity.getId());

        return todoRepository.findByUserId(entity.getUserId());
    }
    public List<TodoEntity> retrieve(final String userId){
        return todoRepository.findByUserId(userId);
    }
    public List<TodoEntity> update(final TodoEntity entity){
        validate(entity);
        final Optional<TodoEntity> original = todoRepository.findById(entity.getId());
        original.ifPresent(todo ->{
            todo.setTitle(entity.getTitle());
            todo.setDone(entity.isDone());
            todoRepository.save(todo);
        });

        return retrieve(entity.getUserId());
    }
    public List<TodoEntity> delete(final TodoEntity entity){
        validate(entity);
        try{
            todoRepository.delete(entity);
        }catch(Exception e){
            log.error("error deleting entity ", entity.getId(), e);
            throw new RuntimeException("error deleting entity "+ entity.getId());
        }
        return retrieve(entity.getUserId());
    }
    private void validate(final TodoEntity entity){
        if(entity==null){
            log.warn("Entity can not be NULL");
            throw  new RuntimeException("Entity can not be NULL");
        }

        if(entity.getUserId()== null){
            log.warn("Unknown user");
            throw new RuntimeException("Unknown user");
        }
    }
}
