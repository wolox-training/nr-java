package wolox.training.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import wolox.training.models.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

	public Optional<Book> findFirstByAuthor(String author);

	public Optional<Book> findFirstByAuthorOrderByYear(String author);

	public Optional<Book> findFirstByTitle(String title);

	public Optional<Book> findBySubtitle(String subtitle);

	public Optional<Book> findFirstByTitleAndSubtitle(String title, String subtitle);

	public Optional<Book> findByIsbn(String isbn);

	public List<Book> findByAuthor(String author);

	public List<Book> findByTitle(String title);

	public List<Book> findByTitleOrderByYear(String title);

//	public List<Book> findByPublisherAndGenreAndYear(String publisher, String genre, String year);
	@Query("select book from Book book where (book.publisher = :publisher or null is :publisher) and "
	        + "(book.genre = :genre or null is :genre) and (book.year = :year or null is :year)")
	public List<Book> findByPublisherAndGenreAndYear(@Param("publisher") String publisher, @Param("genre") String genre,
	        @Param("year") String year);

	@Query("select book from Book book where ((:id is null or book.id = cast(:id as long)) "
	        + "and (:genre is null or book.genre = :genre)" + "and (:author is null or book.author = :author)"
	        + "and (:image is null or book.image = :image)" + "and (:title is null or book.title = :title)"
	        + "and (:subtitle is null or book.subtitle = :subtitle)"
	        + "and (:publisher is null or book.publisher = :publisher)"
	        + "and (:yearFrom is null or :yearTo is null or book.year between :yearFrom and :yearTo )"
	        + "and (:pages is null or book.pages = cast(:pages as int))" + "and (:isbn is null or book.isbn = :isbn))")
	List<Book> findBook(@Param("id") String id, @Param("genre") String genre, @Param("author") String author,
	        @Param("image") String image, @Param("title") String title, @Param("subtitle") String subtitle,
	        @Param("publisher") String publisher, @Param("yearFrom") String yearFrom, @Param("yearTo") String yearTo,
	        @Param("pages") String pages, @Param("isbn") String isbn);

}
