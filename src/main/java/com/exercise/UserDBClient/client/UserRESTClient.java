package com.exercise.UserDBClient.client;

import com.exercise.UserDBClient.model.User;
import com.exercise.UserDBClient.model.UserDTO;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

import java.util.List;

public class UserRESTClient {

    private final RestClient restClient;

    public UserRESTClient() {
        restClient = RestClient.builder()
                .baseUrl("http://localhost:8086")
                .defaultHeader("Authorization","Basic YWRtaW46YWRtaW5ib3Nz")
                .build();
    }

    public List<UserDTO> getAllUsers() {
        return restClient.get()
                .uri("/users")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }

    public UserDTO getUserById(Long id) {
        return restClient.get()
                .uri("/user/{id}",id)
                .retrieve()
                .body(UserDTO.class);
    }

    public User createUser(User user) {
        return restClient.post()
                .uri("/addUser")
                .contentType(MediaType.APPLICATION_JSON)
                .body(user)
                .retrieve()
                .body(User.class);
    }

    public User updateUser(Long id, User user) {
        return restClient.put()
                .uri("user/update/{id}",id)
                .contentType(MediaType.APPLICATION_JSON)
                .body(user)
                .retrieve()
                .body(User.class);
    }

    public boolean deleteUserById(Long id) {
        restClient.delete()
                .uri("user/delete/{id}",id)
                .retrieve()
                .toBodilessEntity();
        return false;
    }

    public String getStats() {
        return restClient.get()
                .uri("/stats")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(String.class);
    }

    public String resetStats() {
        return restClient.put()
                .uri("/stats/reset")
                .contentType(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(String.class);
    }

}
