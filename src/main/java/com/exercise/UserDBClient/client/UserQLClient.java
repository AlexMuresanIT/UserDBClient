package com.exercise.UserDBClient.client;

import com.exercise.UserDBClient.model.User;
import com.exercise.UserDBClient.model.UserDTO;
import org.reactivestreams.Publisher;
import org.springframework.graphql.client.GraphQlClient;
import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.graphql.client.WebSocketGraphQlClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.socket.client.ReactorNetty2WebSocketClient;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import org.springframework.web.socket.client.WebSocketClient;
import reactor.core.Disposable;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.util.List;
import java.util.function.Consumer;

@Service
public class UserQLClient {

    private final HttpGraphQlClient graphQlClient;
    private final WebSocketGraphQlClient webSocketGraphQlClient;

    public UserQLClient() {
        WebClient client = WebClient.builder()
                .baseUrl("http://localhost:8086/graphql")
                .build();

        graphQlClient=HttpGraphQlClient.builder(client)
                .headers(h->h.setBasicAuth("admin","adminboss"))
                .build();

        String url = "http://localhost:8086/subs";
        webSocketGraphQlClient = WebSocketGraphQlClient.builder(url,new ReactorNettyWebSocketClient())
                .headers(h->h.setBasicAuth("admin","adminboss"))
                .build();
    }

    public void getUsersSub() {

        String document = """
                subscription GetUsers {
                    getUsers {
                        id
                        name
                        email
                        town
                    }
                }
                """;
        Flux<List<UserDTO>> flux = webSocketGraphQlClient.document(document)
                .retrieveSubscription("getUsers")
                .toEntityList(UserDTO.class);
        flux.subscribe(System.out::println);
    }


    public void randomNumbers() {

        String document = """
                subscription RandomNumber {
                    randomNumber
                }
                """;
        Flux<String> flux =  webSocketGraphQlClient.document(document)
                .retrieveSubscription("randomNumber")
                .toEntity(String.class);
        flux.subscribe(value->System.out.println("Random value: "+value));
    }

    public Mono<List<UserDTO>> getUsers() {
        String document = """
                query FindAll {
                    findAll {
                        id
                        name
                        email
                        town
                    }
                }
               """;
        Mono<List<UserDTO>> users = graphQlClient.document(document)
                .retrieve("findAll")
                .toEntityList(UserDTO.class);
        return users;
    }

    public Mono<UserDTO> getUser(Long id) {
        String document = """
                query FindUserById {
                     findUserById(id: "1") {
                         id
                         name
                         email
                         town
                     }
                 }
                """;
        Mono<UserDTO> user = graphQlClient.document(document)
                .retrieve("findUserById")
                .toEntity(UserDTO.class);
        return user;
    }

    public void createUser() {
        String document = """
                mutation CreateUser {
                    createUser(
                        user: { name: "alex", email: "alex@gmail.com", password: "pw3", town: "piatra" }
                    ) {
                        town
                        password
                        email
                        name
                        id
                    }
                }
                """;
        Mono<User> user = graphQlClient.document(document)
                .retrieve("createUser")
                .toEntity(User.class);
        UserDTO userDTO = userToDTO(user.block());
        System.out.println(userDTO);
    }

    public void updateUser(Long id) {
        String document = """
                mutation UpdateUser {
                    updateUser(
                        id: "1"
                        user: { name: "Mihai", email: "mihai@email.com", password: "pw3", town: "cluj" }
                    ) {
                        id
                        name
                        email
                        password
                        town
                    }
                }
                """;
        Mono<UserDTO> user = graphQlClient.document(document)
                .retrieve("updateUser")
                .toEntity(UserDTO.class);
        user.subscribe(System.out::println);
    }

    public void deleteUser(Long id) {
        String document = """
                mutation DeleteUser {
                    deleteUser(id: "1") {
                        id
                        name
                        email
                        password
                        town
                    }
                }
                """;
        Mono<UserDTO> user = graphQlClient.document(document)
                .retrieve("deleteUser")
                .toEntity(UserDTO.class);
        user.subscribe(System.out::println);
    }

    private UserDTO userToDTO(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setTown(user.getTown());
        return userDTO;
    }
}
