package wolox.training.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import wolox.training.models.User;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {
	
	@Autowired
	UserRepository userRepository;

	@Before
    public void init() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");		
		
		User user1 = new User("Ironman", "Tony Stark", LocalDate.parse("1970-05-29", formatter));
		userRepository.save(user1);
		User user2 = new User("CapiAmer", "Steve Rogers", LocalDate.parse("1918-06-04", formatter));
		userRepository.save(user2);
		User user3 = new User("Spiderman", "Peter Parker", LocalDate.parse("2001-05-10", formatter));
		userRepository.save(user3);
		User user4 = new User("Hawkeye", "Clint Barton", LocalDate.parse("0001-12-31", formatter));
		userRepository.save(user4);
		
	}

	@Test
	public void GivenLoadedUsers_WhenSearchAllUsers_ThenFind4Users() {
		Iterable<User> users = userRepository.findAll();	
		int cantidad = 4;
		assertThat(users).hasSize(cantidad);
	}
	
	@Test		
	public void GivenLoadedUsers_WhenSearchByUser_ThenFindUser() {
		assertThat(userRepository.findFirstByUserName("Hawkeye").isPresent());
	}

	@Test		
	public void GivenLoadedUsers_WhenSearchByNonExistentUser_ThenNotFindUser() {
		assertThat(!userRepository.findFirstByUserName("Antman").isPresent());
	}

}