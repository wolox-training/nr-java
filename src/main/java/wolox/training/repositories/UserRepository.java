package wolox.training.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import wolox.training.models.User;

public interface UserRepository extends CrudRepository<User, Long> {

	public Optional<User> findFirstByUserName(String userName);

	public List<User> findByBirthDateBetweenAndNameContainingIgnoreCase(LocalDate dateSince, LocalDate dateUntil,
	        String name);
}
