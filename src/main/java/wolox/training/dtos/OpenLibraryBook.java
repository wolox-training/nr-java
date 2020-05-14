package wolox.training.dtos;

import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class OpenLibraryBook {

	private String title;
	private String subtitle;
	private List<PublisherDTO> publishers;
	private String publishDate;
	private int numberOfPages;
	private List<AuthorDTO> authors;
	private CoverDTO cover;
	private List<SubjectDTO> subjects;

	public OpenLibraryBook(String title, String subtitle, List<PublisherDTO> publishers, String publishDate,
	        int numberOfPages, List<AuthorDTO> authors, CoverDTO cover, List<SubjectDTO> subjects) {
		this.title = title;
		this.subtitle = subtitle;
		this.publishers = publishers;
		this.publishDate = publishDate;
		this.numberOfPages = numberOfPages;
		this.authors = authors;
		this.cover = cover;
		this.subjects = subjects;
	}

	public OpenLibraryBook() {

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

	public List<PublisherDTO> getPublishers() {
		return publishers;
	}

	public void setPublishers(List<PublisherDTO> publishers) {
		this.publishers = publishers;
	}

	public String getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}

	public int getNumberOfPages() {
		return numberOfPages;
	}

	public void setNumberOfPages(int numberOfPages) {
		this.numberOfPages = numberOfPages;
	}

	public List<AuthorDTO> getAuthors() {
		return authors;
	}

	public void setAuthors(List<AuthorDTO> authors) {
		this.authors = authors;
	}

	public CoverDTO getCover() {
		return cover;
	}

	public void setCover(CoverDTO cover) {
		this.cover = cover;
	}

	public List<SubjectDTO> getSubjects() {
		return subjects;
	}

	public void setSubjects(List<SubjectDTO> subjects) {
		this.subjects = subjects;
	}

}
