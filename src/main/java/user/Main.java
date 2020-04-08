package user;
import java.time.LocalDate;


public class Main {

    public static void main(String[] args) {
        User user = User.builder()
                .name("James Bond")
                .username("007")
                // ...
                .dob(LocalDate.parse("1920-11-11"))
                .build();
    }
}
