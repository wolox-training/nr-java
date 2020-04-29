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
public class Users{
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    
    @NotNull
    private String username;
    
    @NotNull
    private String name;
    
    @NotNull
    private Date birthDate;
    
    @ManyToMany(cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    @JoinTable(name = "user_book", 
      joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), 
      inverseJoinColumns = @JoinColumn(name = "book_id", 
      referencedColumnName = "id"))
    private List<Book> books = new ArrayList<Book>();

    
    public Users() {
    	
    }

    public long getId() {
    	return id;
    }

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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
    
    public List<Book> AgregarLibro(Book userBook) throws BookAlreadyOwnedException{
    	if (books.contains(userBook)) {
    		throw new BookAlreadyOwnedException();
		} else {
			this.books.add(userBook);
		}
    	return (List<Book>) Collections.unmodifiableList(books);    	
    }

}
