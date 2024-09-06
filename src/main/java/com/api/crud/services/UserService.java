package com.api.crud.services;



import com.api.crud.models.User;
import com.api.crud.repositories.UserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    
    
    
    private RestTemplate restTemplate = new RestTemplate();
    private ObjectMapper objectMapper = new ObjectMapper();

    public void populateDatabase() {
        String url = "https://jsonplaceholder.typicode.com/users";
        String response = restTemplate.getForObject(url, String.class);

        try {
            JsonNode usersJson = objectMapper.readTree(response);
            for (JsonNode userJson : usersJson) {
                User user = new User();
                user.setUsername(userJson.get("name").asText());
                user.setEmail(userJson.get("email").asText());
                userRepository.save(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    
    

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }





    public void clearDatabase() {
        userRepository.deleteAll();
    }
    
    
}
