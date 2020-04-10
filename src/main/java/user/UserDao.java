package user;

import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.time.LocalDate;
import java.util.Optional;
import java.util.List;

@RegisterBeanMapper(User.class)
public interface UserDao {


    @SqlUpdate("""
        CREATE TABLE usertable (
            id IDENTITY,
            username VARCHAR UNIQUE NOT NULL,
            password VARCHAR NOT NULL,
            name VARCHAR NOT NULL,
            email VARCHAR NOT NULL,
            gender VARCHAR NOT NULL,
            dob DATE NOT NULL,
            enabled BIT 
        )
        """
    )
    void createTable();

    @SqlUpdate("INSERT INTO usertable VALUES(:id, :username, :password, :name, :email, :gender, :dob, :enabled)")
    @GetGeneratedKeys("id")
    Long insertUser(@Bind("username") String username, @Bind("password") String password, @Bind("name") String name, @Bind("email") String email, @Bind("gender") User.Gender gender, @Bind("dob") LocalDate dob,@Bind("enabled") boolean enabled);

    //most ezt fogjuk csak használni a 2 insert közül
    @SqlUpdate("INSERT INTO usertable VALUES(:id, :username, :password, :name, :email, :gender, :dob, :enabled)")
    @GetGeneratedKeys("id")
    Long insertUser(@BindBean User user);

    @SqlQuery("SELECT * FROM usertable WHERE id =:id")
    Optional<User> findById(@Bind("id") long id);


    @SqlQuery("SELECT * FROM usertable WHERE username = :username")
    Optional<User> findByUsername(@Bind("username") String username);

    @SqlUpdate("DELETE FROM usertable where username = :username")
    void delete(@BindBean User user); //username will be unique because of constraint

    @SqlQuery("SELECT * FROM usertable ORDER BY id")
    List<User> listUsers();
}
