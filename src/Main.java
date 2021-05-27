import java.io.IOException;
import java.util.List;

public class Main {


    public static void main(String[] args) throws IOException, InterruptedException {

        User newUser = creatingNewUser();
        User creatingNewUser = WebClient.sendPost(newUser);
        System.out.println("Creating new user:\n" + creatingNewUser +"\n***********");


        User user = WebClient.sendGet(10);
        user.setName("UpdatingName");
        user.setUsername("UpdatingUserName");
        user = WebClient.sendPut(user.getId(), user);
        System.out.println("Updating user:\n" + user + "\n***********");


        int statusResponse = WebClient.sendDelete(10);
        System.out.println("Delete user:\n" + statusResponse);


        List<User> users = WebClient.sendGetUsers();
        System.out.println("List of users:\n");
        users.forEach(System.out::println);


        User specificUser = WebClient.sendGetUserById(10);
        System.out.println("Userinfo with id " + 10 + "\n" + specificUser + "\n***********");


        users = WebClient.sendGetUserByName(specificUser.getUsername());
        System.out.println("Userinfo with name " + specificUser.getUsername() + "\n" + users +"\n***********");


        String  allCommentOfLastPostOfUser = WebClient.getAllCommentsOfLastPostByUser(user);
        System.out.println("All comments of last post of specific user:\n" + allCommentOfLastPostOfUser +"\n***********" );


        List<Todos> allOpenedTaskOfUser = WebClient.getAllOpenedTasksOfUser(user);
        System.out.println("All opened tasks of specific user:");
        allOpenedTaskOfUser.forEach(System.out::println);
    }
    private static User creatingNewUser() {
        return new User.Builder()
                .name("Roman")
                .username("Lastname")
                .email("sometest@test.com")
                .address(new Address.Builder()
                        .street("Newstreer")
                        .suite("1")
                        .city("Kiev")
                        .zipcode("03057")
                        .geo(new Geo.Builder()
                                .lat(- 68.6102)
                                .lng( 32.5221)
                                .build())
                        .build())
                .phone("03030303")
                .website("test.com")
                .company(new Company.Builder()
                        .name("Somename")
                        .catchPhrase("CatchPhrase")
                        .bs("TestWeb")
                        .build())
                .build();
    }
}