package wolox.training.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.containsString;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
	
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;

import wolox.training.models.Book;
import wolox.training.repositories.BookRepository;
import wolox.training.controllers.BookController;


@WebMvcTest(BookController.class)
public class BookControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private BookRepository bookRepository;
	
	@BeforeAll
	public static void setUp() {
		
	}
	
	@Test
	@Order(1)
	public void givenBook_whenGetBookByTitle_thenReturnBook() throws Exception {
	     
		Book darkelf1 = new Book("R. A. Salvatore", "imagen.png", "The Dark Elf", "Homeland", 
				"TSR", "1990",  352, "0880389052");
		String title = "The Dark Elf";
	 
	    given(bookRepository.findFirstByTitle("The Dark Elf")).willReturn(Optional.of(darkelf1));

	    mvc.perform(get("/api/books").param("title", title))
	    		.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		        .andExpect(jsonPath("$.subtitle").value("Homeland"));
//				.andExpect(jsonPath("$?.subtitle == 'Homeland'").value("Homeland"));
	}
	
	@Test
	@Order(2)
	public void givenListOfBooks_whenGetBooksByTitle_thenFindBookBySubtitle() throws Exception {
	    
		Book darkelf1 = new Book("R. A. Salvatore", "imagen.png", "The Dark Elf", "Homeland", 
				"TSR", "1990",  352, "0880389052");
		Book darkelf2 = new Book("R. A. Salvatore", "imagen.png", "The Dark Elf", "Exile", 
				"TSR", "1990",  320, "978-0880389204 ");
		Book darkelf3 = new Book("R. A. Salvatore", "imagen.png", "The Dark Elf", "Sojourn", 
				"TSR", "1991",  309, "1-56076-047-8");
		List<Book> bookList = Arrays.asList(darkelf1, darkelf2, darkelf3);
	    
		given(bookRepository.findAll()).willReturn(bookList);

	    mvc.perform(get("/api/books/"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		        .andExpect(jsonPath("$[1].subtitle").value("Exile"))
	    		.andExpect(jsonPath("$", hasSize(3)));
	}
	
	@Test
	@Order(3)
	public void givenListOfBooks_whenGetBooksByubtitle_thenNotFindBook() throws Exception {
	    
		String subtitle = "The Crystal Shard";
		Book darkelf1 = new Book("R. A. Salvatore", "imagen.png", "The Dark Elf", "Homeland", 
				"TSR", "1990",  352, "0880389052");
		Book darkelf2 = new Book("R. A. Salvatore", "imagen.png", "The Dark Elf", "Exile", 
				"TSR", "1990",  320, "978-0880389204 ");
		Book darkelf3 = new Book("R. A. Salvatore", "imagen.png", "The Dark Elf", "Sojourn", 
				"TSR", "1991",  309, "1-56076-047-8");
		List<Book> bookList = Arrays.asList(darkelf1, darkelf2, darkelf3);
	    
		given(bookRepository.findAll()).willReturn(bookList);

	    mvc.perform(get("/api/books/").param("subtitle", subtitle))
				.andDo(print())	
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().string("No se encontro el subtitulo del libro"))
				.andExpect(status().isNotFound());
//				.andExpect(jsonPath("$.subtitle == 'The Crystal Shard").doesNotExist());
	}	
	
	@Test
	@Order(4)
	public void GivenNewBook_WhenPostBook_ThenReturnCREATED() throws Exception {
		Book icewind1 = new Book("R. A. Salvatore", "imagen.png", "The Icewind Dale", "The Crystal Shard", 
				"TSR", "1988",  336, "978-0880385350");	
		
		given(bookRepository.save(any())).willReturn(icewind1);
		
		mvc.perform(post("/api/books")
			.contentType(MediaType.APPLICATION_JSON)
			.characterEncoding("UTF-8")
			.content(asJsonString(icewind1)))
				.andDo(print())				
				.andExpect(status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.isbn").exists());
	}
	
	@Test
	@Order(5)
	public void GivenModifiedBook_WhenPutBook_ThenReturnOK() throws Exception {
		Book icewind2 = new Book("R. A. Salvatore", "imagen.png", "The Icewind Dale", "Trilogy", 
				"TSR", "1989",  342, "0-88038-672-X");	
		Book icewind2Ammend = new Book("R. A. Salvatore", "imagen.png", "The Icewind Dale", "Streams of Silver", 
				"TSR", "1989",  342, "0-88038-672-X");	

		given(bookRepository.findById(any())).willReturn(Optional.of(icewind2));
		given(bookRepository.save(any())).willReturn(icewind2Ammend);
		
		mvc.perform(put("/api/books/0")				
			.contentType(MediaType.APPLICATION_JSON)
			.characterEncoding("UTF-8")
			.content(asJsonString(icewind2Ammend)))
				.andDo(print())				
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.subtitle").value("Streams of Silver"));
	}
	
	@Test
	@Order(5)
	public void GivenBook_WhenDeleteBook_ThenReturnOK() throws Exception {
		Book icewind3 = new Book("R. A. Salvatore", "imagen.png", "The Icewind Dale", "The Halfling's Gem", 
				"TSR", "1990",  314, "978-0880389013");	

		given(bookRepository.findById(any())).willReturn(Optional.of(icewind3));
		
		mvc.perform(delete("/api/books/1"))			
				.andDo(print())				
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$").doesNotExist());
	}
	
	@Test
	@Order(6)
	public void GivenModifiedBook_WhenPutBook_ThenReturnBadRequest( ) throws Exception {
		Book icewind3 = new Book("R. A. Salvatore", "imagen.png", "The Icewind Dale", "Streams of Silver", 
				"TSR", "1989",  342, "0-88038-672-X");	
		
		mvc.perform(put("/api/books/1")
			.contentType(MediaType.APPLICATION_JSON)
			.characterEncoding("UTF-8")
			.content(asJsonString(icewind3)))
				.andDo(print())
				.andExpect(status().isBadRequest())
				.andExpect(content().string(containsString("Id invalido")));
	}
		
	@Test
	@Order(7)
	public void GivenBook_WhenDeleteBook_ThenReturnNotFound( ) throws Exception {	
		
		given(bookRepository.findById(any())).willReturn(Optional.empty());
		
		mvc.perform(delete("/api/books/1"))
				.andDo(print())
				.andExpect(status().isNotFound());
//				.andExpect(content().string("No existe el id del libro ingresado"));
	}
	
	public static String asJsonString(final Object obj) {
	    try {
	        return new ObjectMapper().writeValueAsString(obj);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}
}



	