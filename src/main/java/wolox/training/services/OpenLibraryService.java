package wolox.training.services;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
//import org.springframework.http.ResponseEntity;

import wolox.training.dtos.AuthorDTO;
import wolox.training.dtos.OpenLibraryBook;
import wolox.training.dtos.PublisherDTO;
import wolox.training.dtos.SubjectDTO;
import wolox.training.models.Book;

@Service
public class OpenLibraryService {

	public Optional<Book> bookInfo(String isbn) {
		RestTemplate restTemplate = new RestTemplate();
		String openLibraryResourceUrl = "https://openlibrary.org/api/books?bibkeys=ISBN:{isbn}&format=json&jscmd=data";
// No sirve el ResponseEntity ya que el body viene envuelto en la clave dinamica (isbn:codigoisbn).
//		ResponseEntity<OpenLibraryBook> response = restTemplate
//			.getForEntity(OpenLibraryResourceUrl, OpenLibraryBook.class);
//		OpenLibraryBook openLibraryBook = response.getBody();
//		return openLibraryBook;
		Map<String, OpenLibraryBook> response = restTemplate.exchange(openLibraryResourceUrl, HttpMethod.GET, null,
		        new ParameterizedTypeReference<Map<String, OpenLibraryBook>>() {
		        }, isbn).getBody();
		OpenLibraryBook openLibraryBook = response.get("ISBN:" + isbn);
		return Optional.ofNullable(OpenLibraryBookToBook(openLibraryBook, isbn));
	}

	public Book OpenLibraryBookToBook(OpenLibraryBook openLibraryBook, String isbn) {

		Book book = new Book();

		String vacio = "vacio";

		ArrayList<SubjectDTO> subjects = (ArrayList<SubjectDTO>) openLibraryBook.getSubjects();
		ArrayList<AuthorDTO> authors = (ArrayList<AuthorDTO>) openLibraryBook.getAuthors();
		ArrayList<PublisherDTO> publishers = (ArrayList<PublisherDTO>) openLibraryBook.getPublishers();

		book.setGenre(Optional.ofNullable(subjects.get(0).getName()).orElse(vacio));
		book.setAuthor(Optional.ofNullable(authors.get(0).getName()).orElse(vacio));
		book.setPublisher(Optional.ofNullable(publishers.get(0).getName()).orElse(vacio));

		book.setImage(Optional.ofNullable(openLibraryBook.getCover().getLarge()).orElse(vacio));
		book.setTitle(Optional.ofNullable(openLibraryBook.getTitle()).orElse(vacio));
		book.setSubtitle(Optional.ofNullable(openLibraryBook.getSubtitle()).orElse(vacio));
		book.setYear(Optional.ofNullable(openLibraryBook.getPublishDate()).orElse(vacio));
		book.setYear(Optional.ofNullable(openLibraryBook.getPublishDate()).orElse(vacio));
		book.setPages(Optional.ofNullable(openLibraryBook.getNumberOfPages()).orElse(0));
		book.setIsbn(isbn);

		return book;
	}

}
