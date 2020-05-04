package wolox.training.repositories;

import wolox.training.models.User;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;


public interface UserRepository extends CrudRepository<User, Long> {
	
	public Optional<User> findFirstByUserName(String userName);

}

