package wolox.training.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import wolox.training.exceptions.BookAlreadyOwnedException;

@Entity
@Table(name="Users")
public class User{
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    
    @NotNull
    private String userName;
    
    @NotNull
    private String name;
    
    @NotNull
    private Date birthDate;
    
    @ManyToMany(cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    private List<Book> books = new ArrayList<Book>();

    
    public User() {
    	
    }

    public long getId() {
    	return id;
    }

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public List<Book> getBooks() {
		return (List<Book>) Collections.unmodifiableList(books);
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}
    
    public List<Book> addBook(Book userBook) throws BookAlreadyOwnedException{
    	if (books.contains(userBook)) {
    		throw new BookAlreadyOwnedException("El libro ya existe");
		} else {
			this.books.add(userBook);
		}
    	return (List<Book>) Collections.unmodifiableList(books);    	
    }
    
    public void removeBook(Book userBook) {
    	this.books.remove(userBook);	
    }	

}
