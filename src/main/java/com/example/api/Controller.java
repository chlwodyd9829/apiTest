package com.example.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;


/**
 * api를 이용해 조회, 삽입, 삭제, 갱신
 */
@RestController
@RequestMapping("/api")
@Slf4j
public class Controller {
    @Autowired
    private UserRepository userRepository;

    /**
     * 조회
     */
    @GetMapping
    public List<UserTable> UserList(){
        List<UserTable> all = userRepository.findAll();
        return all;
    }
    /**
     * 삽입
     */
    @PostMapping(value = "/insert")
    public HttpStatus insert(@RequestBody UserTable data){
        UserTable saveUser = userRepository.save(data);
        if(saveUser == null){
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.CREATED;
    }
    /**
     * 삭제
     */
    @DeleteMapping("/delete/{id}")
    public HttpStatus delete(@PathVariable Long id, @RequestBody UserTable user){
        UserTable userTable = userRepository.findById(id).orElseThrow(()->{
            return new IllegalArgumentException("삭제 실패");
        });
        userRepository.delete(userTable);
        return HttpStatus.OK;
    }
    /**
     * 갱신
     */
    @Transactional
    @PutMapping("/update/{id}")
    public HttpStatus update(@PathVariable Long id,@RequestBody UserTable userTable){
        UserTable findUser = userRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("수정 실패");
        });
        findUser.setName(userTable.getName());
        findUser.setAge(userTable.getAge());
        return HttpStatus.OK;
    }
}
