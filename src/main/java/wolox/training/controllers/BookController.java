package wolox.training.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import wolox.training.exceptions.BookIdMismatchException;
import wolox.training.exceptions.BookNotFoundException;
import wolox.training.models.Book;
import wolox.training.repositories.BookRepository;

@RestController
@RequestMapping("/api/books")
public class BookController {

	@Autowired
	private BookRepository bookRepository;
	
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
		bookRepository.findById(id)
			.orElseThrow(() -> new BookNotFoundException("No existe el id del libro ingresado"));
		bookRepository.deleteById(id);
	}
	 
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Book updateBook(@RequestBody Book book, @PathVariable Long id) {
		if (!id.equals(book.getId())) {
			throw new BookIdMismatchException("Id invalido");
		}
		bookRepository.findById(id)
			.orElseThrow(() -> new BookNotFoundException("No existe el libro de id ingresado"));
		return bookRepository.save(book);
	}	
	
}