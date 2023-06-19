/**
 * @author - Alex Cox
 * CIS175 2023
 * 
 */

package movies;

// Importing necessary libraries for our Movie class
import java.io.Serializable; // This allows our Movie objects to be converted into a format that can be stored and retrieved easily.
import javax.persistence.Entity; // This marks our Movie class as an entity which can be mapped to a database.
import javax.persistence.GeneratedValue; // This is for our ID field which is auto-incremented.
import javax.persistence.GenerationType; // This is needed to specify the type of auto-increment.
import javax.persistence.Id; // This marks our id field as the primary key of our table.
import javax.persistence.Table; // This is needed to specify the name of the table that this entity is mapped to.

// Defining entity class Movie which will represent a table named 'movies' in the database.
@Entity
@Table(name="movies")
public class Movies implements Serializable {
    
    // A unique identifier for versions of the class to verify during deserialization
    private static final long serialVersionUID = 1L;

    // Persistent Fields:
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Generate id automatically with identity strategy (auto incremented field)
    private int id; // Unique identifier for each movie
    
    private String movieTitle; // Title of the movie
    
    private String movieDirector; // Director of the movie

    // Constructors:
    // Default constructor for Movie class
    public Movies() {
    }

    // Overloaded constructor to initialize a movie with title and director
    public Movies(String movieTitle, String movieDirector) {
        this.movieTitle = movieTitle;
        this.movieDirector = movieDirector;
    }

    // String Representation:
    // Overriding the toString method to represent our movie
    @Override
    public String toString() {
        return this.movieTitle + " directed by " + this.movieDirector;
    }

    // Getter methods
    // Method to get id of the movie
    public int getId() {
        return id;
    }

    // Method to get the title of the movie
    public String getMovieTitle() {
        return movieTitle;
    }

    // Method to get the director of the movie
    public String getMovieDirector() {
        return movieDirector;
    }

    // Setter methods
    // Method to set the id of the movie
    public void setId(int id) {
        this.id = id;
    }

    // Method to set the title of the movie
    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    // Method to set the director of the movie
    public void setMovieDirector(String movieDirector) {
        this.movieDirector = movieDirector;
    }
}
