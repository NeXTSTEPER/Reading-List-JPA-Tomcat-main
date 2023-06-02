package books;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="items")
public class Books implements Serializable {
    private static final long serialVersionUID = 1L;

    // Persistent Fields:
    @Id
	@GeneratedValue
    private int id;
    private String bookTitle;
    private String bookAuthor;

    // Constructors:
    public Books() {
    }

    public Books(String bookTitle, String bookAuthor) {
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
    }

    // String Representation:
    @Override
    public String toString() {
        return this.bookTitle + " by " + this.bookAuthor;
    }

    // Getter methods
    public int getId() {
        return id;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    // Setter methods
    public void setId(int id) {
        this.id = id;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }
}
