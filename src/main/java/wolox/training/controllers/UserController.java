package wolox.training.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import wolox.training.exceptions.BookNotFoundException;
import wolox.training.exceptions.UserIdMismatchException;
import wolox.training.exceptions.UserNotFoundException;
import wolox.training.models.Book;
import wolox.training.models.User;
import wolox.training.repositories.BookRepository;
import wolox.training.repositories.UserRepository;

@RestController
@RequestMapping("/api/user")
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BookRepository bookRepository;
	
	@GetMapping
	public Iterable<User> findAll() {
		return userRepository.findAll(); 
	}
	
	@GetMapping("/{id}")	
	public User findOne(@PathVariable(required = true) Long id) {
		return userRepository.findById(id)
		      .orElseThrow(() -> new UserNotFoundException("No se encontro el id del usuario"));
	}
	
	@GetMapping
	@RequestMapping(params = "user")
	public User findByUserName(@RequestParam(required = true) String userName) {
		return userRepository.findFirstByUserName(userName)
				.orElseThrow(() -> new UserNotFoundException("No se encontro el usuario"));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public User createuser(@RequestBody User user) {
		return userRepository.save(user);
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public User updateUser(@RequestBody User user, @PathVariable Long id) {
		if (!id.equals(user.getId())) {
			throw new UserIdMismatchException("Id invalido");
		}
		userRepository.findById(id)
		.orElseThrow(() -> new UserNotFoundException("No existe el usuario de id ingresado"));
		return userRepository.save(user);
	}	
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void deleteUser(@PathVariable(required = true) Long id) {
		userRepository.findById(id)
			.orElseThrow(() -> new UserNotFoundException("No existe el id del usuario ingresado"));
		userRepository.deleteById(id);
	}
	
	@PatchMapping("/{userid}/book/{bookid}")
	@ResponseStatus(HttpStatus.OK)
	public User agregarLibro(@PathVariable(required = true) Long userid, @PathVariable(required = true) Long bookid) {
		User user = userRepository.findById(userid)
		        .orElseThrow(() -> new UserNotFoundException("No existe el Usuario ingresado"));
		Book book = bookRepository.findById(bookid)
		        .orElseThrow(() -> new BookNotFoundException("No existe el Libro ingresado"));
		user.addBook(book);
		return userRepository.save(user);
	}

	@DeleteMapping("/{userid}/book/{bookid}")
	@ResponseStatus(HttpStatus.OK)
	public User borrarLibro(@PathVariable(required = true) Long userid, @PathVariable(required = true) Long bookid) {
		User user = userRepository.findById(userid)
		        .orElseThrow(() -> new UserNotFoundException("No existe el Usuario ingresado"));
		Book book = bookRepository.findById(bookid)
		        .orElseThrow(() -> new BookNotFoundException("No existe el Libro ingresado"));
		user.removeBook(book);
		return userRepository.save(user);
	}

}