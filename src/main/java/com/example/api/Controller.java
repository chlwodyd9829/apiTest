package com.example.api;

import com.example.api.exception.UserExist;
import com.example.api.exception.UserNotFoundException;
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
    @GetMapping("/{id}")
    public UserTable findUser(@PathVariable Long id){
        UserTable findUser = userRepository.findById(id).orElseThrow(() -> {
            throw new UserNotFoundException("존재하지 않는 사용자");
        });
        return findUser;
    }
    /**
     * 삽입
     */
    @PostMapping(value = "/insert")
    public UserTable insert(@RequestBody UserTable data){
        UserTable findUser = userRepository.findByName(data.getName()).orElse(null);
        if(findUser != null){
            throw new UserExist("이미 존재하는 사용자");
        }
        UserTable save = userRepository.save(data);
        return save;
    }
    /**
     * 삭제
     */
    @DeleteMapping("/delete/{id}")
    public HttpStatus delete(@PathVariable Long id, @RequestBody UserTable user){
        UserTable userTable = userRepository.findById(id).orElseThrow(()->{
            throw new UserNotFoundException("존재하지 않는 사용자");
        });
        userRepository.delete(userTable);
        return HttpStatus.OK;
    }
    /**
     * 갱신
     */
    @Transactional
    @PutMapping("/update/{id}")
    public UserTable update(@PathVariable Long id,@RequestBody UserTable userTable){
        UserTable findUser = userRepository.findById(id).orElseThrow(() -> {
            throw new UserNotFoundException("존재하지 않는 사용자");
        });
        findUser.setName(userTable.getName());
        findUser.setAge(userTable.getAge());
        return findUser;
    }
}
