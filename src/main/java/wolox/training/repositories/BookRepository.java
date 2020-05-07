package wolox.training.repositories;

import wolox.training.models.Book;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BookRepository extends JpaRepository<Book, Long> {
	
	public Optional<Book> findFirstByAuthor(String author);

	public Optional<Book> findFirstByAuthorOrderByYear(String author);
	
	public Optional<Book> findFirstByTitle(String title);

	public List<Book> findByAuthor(String author);

	public List<Book> findByTitle(String title);

	public List<Book> findByTitleOrderByYear(String title);

}

