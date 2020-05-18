package wolox.training.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import wolox.training.models.Book;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BookRepositoryTest {

	@Autowired
	private BookRepository bookRepository;

	@BeforeAll
	public void setUp() {
		Book darkelf1 = new Book("Fantasia", "R. A. Salvatore", "imagen.png", "The Dark Elf", "Homeland", "TSR", "1990",
		        352, "0880389052");
		bookRepository.save(darkelf1);
		Book darkelf2 = new Book("Fantasia", "R. A. Salvatore", "imagen.png", "The Dark Elf", "Exile", "TSR", "1990",
		        320, "978-0880389204 ");
		bookRepository.save(darkelf2);
		Book darkelf3 = new Book("Fantasia", "R. A. Salvatore", "imagen.png", "The Dark Elf", "Sojourn", "TSR", "1991",
		        309, "1-56076-047-8");
		bookRepository.save(darkelf3);
		Book lotr = new Book("Legendarium", "J. R. R. Tolkien", "imagen.png", "El Señor de los Anillos",
		        "La Comunidad del Anillo", "George Allen & Unwin", "1954", 423, "0048231851");
		bookRepository.save(lotr);
		Book icewind1 = new Book("Fantasia", "R. A. Salvatore", "imagen.png", "The Icewind Dale", "The Crystal Shard",
		        "TSR", "1988", 336, "978-0880385350");
		bookRepository.save(icewind1);
	}

	@Test
	@Order(1)
	public void GivenLoadedAuthor_whenSearchAuthor_ThenFindFirstBook() {
		String author = "R. A. Salvatore";
		Book foundBook = bookRepository.findFirstByAuthorOrderByYear(author).orElse(new Book());
		assertThat(foundBook.getSubtitle()).isEqualTo("The Crystal Shard");
	}

	@Test
	@Order(2)
	public void GivenLoadedBooks_whenSearchAuthor_ThenFindAllBooksOfAuthor() {
		String author = "R. A. Salvatore";
		Iterable<Book> books = bookRepository.findByAuthor(author);
		assertThat(books).hasSize(4);
	}

	@Test
	@Order(3)
	public void GivenLoadedBooks_whenSearchNonExistentAuthor_ThenNotFindBook() {
		String author = "J. K. Rowling";
		assertThat(!bookRepository.findFirstByAuthor(author).isPresent()).isTrue();
	}

	@Test
	@Order(4)
	public void GivenLoadedBooks_whenSearchForTitle_ThenFindBookBySubtitle() {
		String title = "The Dark Elf";
		String subtitle = "Sojourn";
//		Book foundSubtitle = new Book();
		List<Book> bookList = bookRepository.findByTitleOrderByYear(title);
		assertThat(bookList.get(2).getSubtitle().contains(subtitle)).isTrue();
//		while (foundBook.hasNext()) {
//			if (((Book) foundBook).getSubtitle().equals(subtitle)) {
//				foundSubtitle = (Book) foundBook;
//			}				
//		}
//		assertThat(foundSubtitle.getSubtitle().equals(subtitle));
	}

	@Test
	@Order(5)
	public void GivenLoadedBooks_WhenSearchForTitleAndSubtitle_ThenFindBookByTitleAndSubtitle() {
		String title = "The Icewind Dale";
		String subtitle = "The Crystal Shard";
		Book foundBook = bookRepository.findFirstByTitleAndSubtitle(title, subtitle).orElse(new Book());
		assertThat(foundBook.getTitle()).isEqualTo("The Icewind Dale");
		assertThat(foundBook.getSubtitle()).isEqualTo("The Crystal Shard");
	}

	@Test
	@Order(6)
	public void GivenLoadedBooks_WhenSearchForPublisherAndGenreAndYear_ThenFindBook() {
		String publisher = "TSR";
		String genre = "Fantasia";
		String year = "1990";
		Iterable<Book> books = bookRepository.findByPublisherAndGenreAndYear(publisher, genre, year);
		assertThat(books).hasSize(2);
	}

	@Test
	@Order(7)
	public void GivenLoadedBooks_WhenSearchForPublisherAndGenreAndYearEqualNull_ThenFindBook() {
		String publisher = "TSR";
		String genre = "Fantasia";
		String year = null;
		Iterable<Book> books = bookRepository.findByPublisherAndGenreAndYear(publisher, genre, year);
		assertThat(books).hasSize(4);
	}
}
