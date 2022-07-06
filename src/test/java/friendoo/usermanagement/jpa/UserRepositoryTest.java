package friendoo.usermanagement.jpa;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//In order to run the test against the real database
@Rollback(false)
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    void testAddNew() {
        User user = new User();
        user.setFirstName("yad");
        user.setLastName("ghazi");
        user.setEmail("yadghazi@gmail.com");
        user.setPassword("yad");
        User savedUser = userRepository.save(user);

        Assertions.assertThat(savedUser).isNotNull();
        Assertions.assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    void testGetAll() {
        Iterable<User> allUsers = userRepository.findAll();
        Assertions.assertThat(allUsers).hasSizeGreaterThan(0);
        allUsers.forEach(System.out::println);

    }

    @Test
    void testUpdate() {
        Long userId = 1L;
        Optional<User> byId = userRepository.findById(userId);
        User user = byId.get();
        user.setPassword("meowwww");
        userRepository.save(user);

        User updateUser = userRepository.findById(userId).get();
        Assertions.assertThat(updateUser.getPassword()).isEqualTo("meowwww");

    }

    @Test
    void testGet() {
        Optional<User> byId = userRepository.findById(1L);
        Assertions.assertThat(byId).isPresent();
        System.out.println(byId.get());

    }

    @Test
    void testDelete() {
        userRepository.deleteById(2L);
        Optional<User> byId = userRepository.findById(2L);
        Assertions.assertThat(byId).isNotPresent();


    }
}