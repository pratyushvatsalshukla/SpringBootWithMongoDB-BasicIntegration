package com.example.demo;

import java.util.Objects;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class Controller {

    @Autowired
    UserRepository userRepo ;

    ModelMapper modelMapper= new ModelMapper() ;

    @PostMapping("/")
    public ResponseEntity<?> userRegistration(@RequestBody User user){
        User byEmail = userRepo.findByEmail(user.getEmail());
        if (Objects.nonNull(byEmail)){
            return new ResponseEntity<>("User Already Exists With Same Email Id in Database !", HttpStatus.CONFLICT) ;
        }
        return new ResponseEntity<>(userRepo.save(user), HttpStatus.CREATED) ;
    }

    @GetMapping("/{email}")
    public ResponseEntity<?> getUserDetails(@PathVariable String email){
        User byEmail = userRepo.findByEmail(email);
        if(Objects.nonNull(byEmail))
        return new ResponseEntity<>(userRepo.findByEmail(email), HttpStatus.FOUND) ;
        else return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND) ;

    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody User user){
        User byEmail = userRepo.findByEmail(user.getEmail());
        if(Objects.nonNull(byEmail))
        {
            modelMapper.map(user, byEmail);
            userRepo.save(byEmail);
            return new ResponseEntity<>("User updated", HttpStatus.ACCEPTED) ;
        }
        else{
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND) ;
        }

    }

    @DeleteMapping("/delete/{email}")
    public ResponseEntity<?> deleteUser(@PathVariable String email){
        User byEmail = userRepo.findByEmail(email);
        if(Objects.nonNull(byEmail))
        {
            userRepo.delete(byEmail);
            return new ResponseEntity<>("User deleted", HttpStatus.OK) ;
        }
        else{
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND) ;
        }



    }

}
