package wolox.training.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name="Books")
public class Book {
  
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;	
    
    @ManyToMany(mappedBy = "books")
    @JsonIgnore
    private List<User> User;
    
    private String genre;
    
    @NotNull
    private String author;
    
    @NotNull
    private String image;
    
    @NotNull
    private String title;
    
    @NotNull
    private String subtitle;
    
    @NotNull
    private String publisher;
    
    @NotNull
    private String year;
    
    @NotNull
    private int pages;
    
    @NotNull
    private String isbn;
    
    public Book() {

    }
    
    public Book(long id, String author, String image, String title, String subtitle, String publisher, String year,
    int pages, String isbn) {
    	super();
    	this.id = id;
    	this.author = author;
    	this.image = image;
    	this.title = title;
    	this.subtitle = subtitle;
    	this.publisher = publisher;
    	this.year = year;
    	this.pages = pages;
    	this.isbn = isbn;
    }

	public long getId() {
		return id;
	}

	public String getGenre() {
		return genre;
	}
	
	public void setGenre(String genre) {
		this.genre = genre;
	}
	
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}



	
}