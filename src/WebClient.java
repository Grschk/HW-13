import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class WebClient {
    private static final Gson GSON = new Gson();
    private static final HttpClient CLIENT = HttpClient.newHttpClient();
    private static final String HOST = "https://jsonplaceholder.typicode.com";
    private static final String ENDPOINT_USERS = "/users";
    private static final String ENDPOINT_USER_NAME = "?username=";
    private static final String ENDPOINT_POSTS = "/posts";
    private static final String ENDPOINT_COMMENTS = "/comments";
    private static final String TODOS = "/todos";

    public static User sendPost(User newUser) throws IOException, InterruptedException {
        String requestBody = GSON.toJson(newUser);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(String.format("%s%s", HOST, ENDPOINT_USERS, newUser)))
                .header("Content-type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        return GSON.fromJson(response.body(), User.class);
    }

    public static User sendPut(int id, User user) throws IOException, InterruptedException {
        String requestBody = GSON.toJson(user);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(String.format("%s%s/%d", HOST, ENDPOINT_USERS, id)))
                .header("Content-type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        return GSON.fromJson(response.body(), User.class);
    }

    public static User sendGet(int id) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(String.format("%s%s/%d", HOST, ENDPOINT_USERS, id)))
                .GET()
                .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        return GSON.fromJson(response.body(), User.class);
    }

    public static User sendGetUserById(int id) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(String.format("%s%s/%d", HOST, ENDPOINT_USERS, id)))
                .GET()
                .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        return GSON.fromJson(response.body(), User.class);
    }

    public static List<User> sendGetUsers() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(String.format("%s%s", HOST, ENDPOINT_USERS)))
                .GET()
                .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        List<User> users = GSON.fromJson(response.body(), new TypeToken<List<User>>(){}.getType());
        return users;
    }

    public static List<User> sendGetUserByName(String name) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(String.format("%s%s%s%s", HOST, ENDPOINT_USERS, ENDPOINT_USER_NAME, name)))
                .GET()
                .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        List<User> users = GSON.fromJson(response.body(), new TypeToken<List<User>>(){}.getType());
        return users;
    }

    public static int sendDelete(int id) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(String.format("%s%s/%d", HOST, ENDPOINT_USERS, id)))
                .DELETE()
                .build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        return response.statusCode();
    }

    public static String getAllCommentsOfLastPostByUser(User user) throws IOException, InterruptedException {
        Posts lastPost = getLastPost(user);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(String.format("%s%s/%d%s", HOST, ENDPOINT_POSTS, lastPost.getId(), ENDPOINT_COMMENTS)))
                .GET()
                .build();
        String fileName = "user-" + user.getId() + "-post-" + lastPost.getId() + "-comments.json";
        HttpResponse<Path> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofFile(Paths.get(fileName)));
        return "File with comments : " + response.body();
    }

    private static Posts getLastPost(User user) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(String.format(String.format("%s%s/%d%s", HOST, ENDPOINT_USERS, user.getId(), ENDPOINT_POSTS))))
                .GET()
                .build();
        HttpResponse<String> responsePosts = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        List<Posts> posts = GSON.fromJson(responsePosts.body(),new TypeToken<List<Posts>>(){}.getType());
        return Collections.max(posts, Comparator.comparingInt(Posts::getId));
    }

    public static List<Todos> getAllOpenedTasksOfUser(User user) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(String.format("%s%s/%d%s", HOST, ENDPOINT_USERS, user.getId(), TODOS)))
                .GET()
                .build();
        HttpResponse<String> response = CLIENT.send(request,HttpResponse.BodyHandlers.ofString());
        List<Todos> allTasks = GSON.fromJson(response.body(), new TypeToken<List<Todos>>(){}.getType());

        return allTasks.stream()
                .filter(todo -> !todo.isCompleted())
                .collect(Collectors.toList());
    }
}