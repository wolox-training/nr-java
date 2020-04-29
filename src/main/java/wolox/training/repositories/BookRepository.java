package wolox.training.repositories;

import wolox.training.models.Book;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BookRepository extends JpaRepository<Book, Long> {
	
	public Optional<Book> findFirstByAuthor(String author);

}

