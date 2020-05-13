package wolox.training.controllers;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import wolox.training.exceptions.BookIdMismatchException;
import wolox.training.exceptions.BookNotFoundException;
import wolox.training.models.Book;
import wolox.training.repositories.BookRepository;
import wolox.training.services.OpenLibraryService;

@RestController
@RequestMapping("/api/books")
public class BookController {

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private OpenLibraryService openLibraryService;

	@GetMapping
	public Iterable<Book> findAll() {
		return bookRepository.findAll();
	}

	@GetMapping("/{id}")
	public Book findOne(@PathVariable(required = true) Long id) {
		return bookRepository.findById(id)
		        .orElseThrow(() -> new BookNotFoundException("No se encontro el id del libro"));
	}

	@GetMapping
	@RequestMapping(params = "author")
	public Book findByAuthor(@RequestParam(required = true) String author) {
		return bookRepository.findFirstByAuthor(author)
		        .orElseThrow(() -> new BookNotFoundException("No se encontro el libro del autor "));
	}

	@GetMapping
	@RequestMapping(params = "title")
	public Book findFirstByTitle(@RequestParam(required = true) String title) {
		return bookRepository.findFirstByTitle(title)
		        .orElseThrow(() -> new BookNotFoundException("No se encontro el titulo del libro"));
	}

	@GetMapping
	@RequestMapping(params = "subtitle")
	public Book findBySubtitle(@RequestParam(required = true) String subtitle) {
		return bookRepository.findBySubtitle(subtitle)
		        .orElseThrow(() -> new BookNotFoundException("No se encontro el subtitulo del libro"));
	}

	@GetMapping
	@RequestMapping(params = "isbn")
	public ResponseEntity<Book> findByIsbn(@RequestParam(name = "isbn", required = true) String isbn) {
		Optional<Book> optionalBook = bookRepository.findByIsbn(isbn);
		if (optionalBook.isPresent()) {
			return new ResponseEntity<Book>(optionalBook.get(), HttpStatus.OK);
		} else {

			Book book = openLibraryService.bookInfo(isbn)
			        .orElseThrow(() -> new BookNotFoundException("No se encontro libro con isbn=" + isbn));

			return new ResponseEntity<Book>(bookRepository.save(book), HttpStatus.CREATED);

//			Book foundBook = new Book(book.getAuthors().get(0).getName(), "", book.getTitle(), book.getSubtitle(),
//			        book.getPublishers().get(0).getName(), book.getPublishDate(), book.getNumberOfPages(), isbn);
//			return new ResponseEntity<Book>(bookRepository.save(foundBook), HttpStatus.CREATED);
		}
	}

// Cada ResponseStatus se configura para devolver una respuesta estandarizada a la web
// La anotacion @Valid ejecuta las validaciones en el atributo de la entidad antes de avanzar con el impacto.
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Book createBook(@RequestBody @Valid Book book) {
		return bookRepository.save(book);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void deleteBook(@PathVariable(required = true) Long id) {
		bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException("No existe el id del libro ingresado"));
		bookRepository.deleteById(id);
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Book updateBook(@RequestBody Book book, @PathVariable Long id) {
		if (!id.equals(book.getId())) {
			throw new BookIdMismatchException("Id invalido");
		}
		bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException("No existe el libro de id ingresado"));
		return bookRepository.save(book);
	}

}
