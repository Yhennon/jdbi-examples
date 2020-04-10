package user;

import java.time.LocalDate;

import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

public class Main {

    public static void main(String[] args) {
        Jdbi jdbi = Jdbi.create("jdbc:h2:mem:usertest");
        jdbi.installPlugin(new SqlObjectPlugin());
        try (Handle handle = jdbi.open()) { // mmegnyitom,lezárás automata
            UserDao dao = handle.attach(UserDao.class);
            //létrehozom a táblát
            dao.createTable();

            //példányosítok usereket
           User user1 = User.builder()
                            .username("iamcreative")
                            .password("secret")
                            .name("James Bond")
                            .email("valami@valahol.com")
                            .gender(User.Gender.MALE)
                            .dob(LocalDate.parse("1920-11-11"))
                            .enabled(true)
                            .build();

           User user2 = User.builder()
                    .username("themoonischeese")
                    .password("topsecret")
                    .name("Lord Shaxx")
                    .email("ls@valahol.com")
                    .gender(User.Gender.MALE)
                    .dob(LocalDate.parse("1961-05-16"))
                    .enabled(false)
                    .build();

           User user3 = User.builder()
                    .username("revenge")
                    .password("liza")
                    .name("Skandar Graun")
                    .email("orderandchaos@valahol.com")
                    .gender(User.Gender.MALE)
                    .dob(LocalDate.parse("1856-11-11"))
                    .enabled(true)
                    .build();

            User user4 = User.builder()
                    .username("shieldmaiden")
                    .password("bjornironside")
                    .name("Lagertha")
                    .email("kattegat@valahol.com")
                    .gender(User.Gender.FEMALE)
                    .dob(LocalDate.parse("1710-01-01"))
                    .enabled(false)
                    .build();


            //a példányosított usereket beszúrom a táblámba,így mostmár vannak sorok amikre meghívhatom a többi fveket
           dao.insertUser(user1);
           dao.insertUser(user2);
           dao.insertUser(user3);
           dao.insertUser(user4);

            //fvek kipróbálása
           dao.delete(user1);
           dao.listUsers().stream().forEach(System.out::println);
           dao.findByUsername("shieldmaiden").stream().forEach(System.out::println);
           dao.findById(3).stream().forEach(System.out::println);
        }
    }
}