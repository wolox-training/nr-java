package wolox.training.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import wolox.training.models.User;

public interface UserRepository extends CrudRepository<User, Long> {

	public Optional<User> findFirstByUserName(String userName);

//	public List<User> findByBirthDateBetweenAndNameContainingIgnoreCase(LocalDate dateSince, LocalDate dateUntil,
//	        String name);

	@Query("select user from User user where ((cast(:dateFrom as date) is null or cast(:dateTo as date) is null or"
	        + " user.birthDate between :dateFrom and :dateTo) "
	        + "and (:name is null or lower(user.name) like lower(concat('%',:name,'%'))))")
	public List<User> findByBirthDateBetweenAndNameContainingIgnoreCase(@Param("dateFrom") LocalDate dateFrom,
	        @Param("dateTo") LocalDate dateTo, @Param("name") String name);
}
