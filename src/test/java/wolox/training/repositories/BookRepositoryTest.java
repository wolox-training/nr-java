package wolox.training.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import wolox.training.models.Book;

@RunWith(SpringRunner.class)
@DataJpaTest
public class BookRepositoryTest {
	
	@Autowired
	private BookRepository bookRepository;
	
	@Before
	public void init() {
		Book darkelf1 = new Book("R. A. Salvatore", "imagen.png", "The Dark Elf", "Homeland", 
				"TSR", "1990",  352, "0880389052");
		bookRepository.save(darkelf1);
		Book darkelf2 = new Book("R. A. Salvatore", "imagen.png", "The Dark Elf", "Exile", 
				"TSR", "1990",  320, "978-0880389204 ");
		bookRepository.save(darkelf2);
		Book darkelf3 = new Book("R. A. Salvatore", "imagen.png", "The Dark Elf", "Sojourn", 
				"TSR", "1991",  309, "1-56076-047-8");
		bookRepository.save(darkelf3);
		Book lotr = new Book("J. R. R. Tolkien", "imagen.png", "El Señor de los Anillos", "La Comunidad del Anillo", 
				"George Allen & Unwin", "1954",  423, "0048231851");
		bookRepository.save(lotr);
	}
	
	@Test
	public void GivenLoadedAuthor_whenSearchAuthor_ThenFindFirstBook () {
		String author = "R. A. Salvatore";
		Book foundBook = bookRepository.findFirstByAuthorOrderByYear(author).orElse(new Book());
		assertThat(foundBook.getSubtitle())
			.isEqualTo("Homeland");	
	}
	

	@Test
	public void GivenLoadedBooks_whenSearchAuthor_ThenFindAllBooksOfAuthor () {
		String author = "R. A. Salvatore";
		Iterable<Book> books = bookRepository.findByAuthor(author);
		assertThat(books).hasSize(3);
	}
	
	@Test
	public void GivenLoadedBooks_whenSearchNonExistentAuthor_ThenNotFindBook() {
		String author = "J. K. Rowling";
		assertThat(!bookRepository.findFirstByAuthor(author).isPresent());
	}
	
	@Test
	public void GivenLoadedBooks_whenSearchForTitle_ThenFindBookBySubtitle() {
		String title = "The Dark Elf";
		String subtitle = "Sojourn";
//		Book foundSubtitle = new Book();
		List<Book> bookList =  bookRepository.findByTitleOrderByYear(title);
		assertThat(bookList.get(2).getSubtitle().contains(subtitle));
//		while (foundBook.hasNext()) {
//			if (((Book) foundBook).getSubtitle().equals(subtitle)) {
//				foundSubtitle = (Book) foundBook;
//			}				
//		}
//		assertThat(foundSubtitle.getSubtitle().equals(subtitle));

	}

	
}