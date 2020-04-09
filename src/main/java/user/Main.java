package user;

import java.time.LocalDate;

import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

public class Main {

    public static void main(String[] args) {
        Jdbi jdbi = Jdbi.create("jdbc:h2:mem:usertest");
        jdbi.installPlugin(new SqlObjectPlugin());
        try (Handle handle = jdbi.open()) {
            UserDao dao = handle.attach(UserDao.class); // mmegnyitom,lezárás automata

            dao.createTable();

           User user1 = User.builder()
                            .username("iamcreative")
                            .password("secret")
                            .name("James Bond")
                            .email("valami@valahol.com")
                            .gender(User.Gender.MALE)
                            .dob(LocalDate.parse("1920-11-11"))
                            .enabled(true)
                            .build();

           dao.insertUser(user1);

            dao.listUsers().stream().forEach(System.out::println);
        }




    }
}