/**
 * @author - Alex Cox
 * CIS175 2023
 * 
 */



package books;

// Importing necessary libraries for our Books class
import java.io.Serializable; // This allows our Books objects to be converted into a format that can be stored and retrieved easily.
import javax.persistence.Entity; // This marks our Books class as an entity which can be mapped to a database.
import javax.persistence.GeneratedValue; // This is for our ID field which is auto-incremented.
import javax.persistence.GenerationType; // This is needed to specify the type of auto-increment.
import javax.persistence.Id; // This marks our id field as the primary key of our table.
import javax.persistence.Table; // This is needed to specify the name of the table that this entity is mapped to.

// Defining entity class Books which will represent a table named 'items' in the database.
@Entity
@Table(name="items")
public class Books implements Serializable {
    
    // A unique identifier for versions of the class to verify during deserialization
    private static final long serialVersionUID = 1L;

    // Persistent Fields:
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Generate id automatically with identity strategy (auto incremented field)
    private int id; // Unique identifier for each book
    
    private String bookTitle; // Title of the book
    
    private String bookAuthor; // Author of the book

    // Constructors:
    // Default constructor for Books class
    public Books() {
    }

    // Overloaded constructor to initialize a book with title and author
    public Books(String bookTitle, String bookAuthor) {
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
    }

    // String Representation:
    // Overriding the toString method to represent our book
    @Override
    public String toString() {
        return this.bookTitle + " by " + this.bookAuthor;
    }

    // Getter methods
    // Method to get id of the book
    public int getId() {
        return id;
    }

    // Method to get the title of the book
    public String getBookTitle() {
        return bookTitle;
    }

    // Method to get the author of the book
    public String getBookAuthor() {
        return bookAuthor;
    }

    // Setter methods
    // Method to set the id of the book
    public void setId(int id) {
        this.id = id;
    }

    // Method to set the title of the book
    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    // Method to set the author of the book
    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }
}
