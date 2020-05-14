package wolox.training.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import wolox.training.models.User;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserRepositoryTest {

	@Autowired
	UserRepository userRepository;

	@BeforeAll
	public void setUp() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		User hawkeye = new User("Hawkeye", "Clint Barton", LocalDate.parse("0001-12-31", formatter));
		userRepository.save(hawkeye);
		User ironman = new User("Ironman", "Tony Stark", LocalDate.parse("1970-05-29", formatter));
		userRepository.save(ironman);
		User spiderman = new User("Spiderman", "Peter Parker", LocalDate.parse("2001-05-10", formatter));
		userRepository.save(spiderman);
		User capiamer = new User("CapiAmer", "Steve Rogers", LocalDate.parse("1918-06-04", formatter));
		userRepository.save(capiamer);
	}

	@Test
	@Order(1)
	public void GivenLoadedUsers_WhenSearchAllUsers_ThenFind4Users() {
		Iterable<User> users = userRepository.findAll();
		int cantidad = 4;
		assertThat(users).hasSize(cantidad);
	}

	@Test
	@Order(2)
	public void GivenLoadedUsers_WhenSearchByUser_ThenFindUser() {
		assertThat(userRepository.findFirstByUserName("Hawkeye").isPresent());
	}

	@Test
	@Order(3)
	public void GivenLoadedUsers_WhenSearchByNonExistentUser_ThenNotFindUser() {
		assertThat(!userRepository.findFirstByUserName("Antman").isPresent());
	}
}
