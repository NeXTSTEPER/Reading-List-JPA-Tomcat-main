package movies;

// Importing necessary libraries
import java.io.IOException; // Handling I/O exceptions
import java.util.List; // Using List for handling collections of objects
import javax.servlet.ServletException; // Handling servlet exceptions
import javax.servlet.http.*; // Using HttpServlet, HttpServletRequest, HttpServletResponse
import javax.persistence.*; // Importing classes related to database operations

// Class extending HttpServlet to handle HTTP requests related to movies
public class MoviesServlet extends HttpServlet {
    
    // Unique identifier for versions of the class to verify during deserialization
    private static final long serialVersionUID = 1L;

    // Method handling HTTP GET requests
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Create EntityManager for database operations
        EntityManagerFactory emf = (EntityManagerFactory)getServletContext().getAttribute("emf");
        EntityManager em = emf.createEntityManager();

        try {
            // Extract movie title and director from request
            String movieTitle = request.getParameter("movieTitle");
            String movieDirector = request.getParameter("movieDirector");
            
            // If the parameters are not null or empty, create a new movie
            if (movieTitle != null && !movieTitle.trim().isEmpty() && movieDirector != null && !movieDirector.trim().isEmpty()) {
                em.getTransaction().begin();
                em.persist(new Movies(movieTitle, movieDirector));
                em.getTransaction().commit();
            } else {
                // If the parameters are null or empty, set an error message in the request
                request.setAttribute("error", "Please enter a title and director.");
            }

            // Retrieve a list of all movies and set it in the request
            List<Movies> movieList = em.createQuery("SELECT m FROM Movies m", Movies.class).getResultList();
            request.setAttribute("movies", movieList);
            
            // Forward request to the JSP page
            request.getRequestDispatcher("/movies.jsp").forward(request, response);
        } finally {
            // Ensure any active transaction is rolled back and EntityManager is closed
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
        }
    }

    // Method handling HTTP POST requests
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Extract movie title, director, operation type, and id from request
        String movieTitle = request.getParameter("movieTitle");
        String movieDirector = request.getParameter("movieDirector");
        String operation = request.getParameter("operation");
        String id = request.getParameter("id");

        // Create EntityManager for database operations
        EntityManagerFactory emf = (EntityManagerFactory)getServletContext().getAttribute("emf");
        EntityManager em = emf.createEntityManager();

        try {
            // If operation is delete, find the movie by id and remove it
            if (operation != null && operation.equals("delete") && id != null) {
                em.getTransaction().begin();
                Movies movie = em.find(Movies.class, Integer.parseInt(id));
                if (movie != null) {
                    em.remove(movie);
                }
                em.getTransaction().commit();
            } 
            // If operation is update, find the movie by id and update its title and director
            else if (operation != null && operation.equals("update") && movieTitle != null && !movieTitle.trim().isEmpty() && movieDirector != null && !movieDirector.trim().isEmpty() && id != null) {
                em.getTransaction().begin();
                Movies movie = em.find(Movies.class, Integer.parseInt(id));
                if (movie != null) {
                    movie.setMovieTitle(movieTitle);
                    movie.setMovieDirector(movieDirector);
                }
                em.getTransaction().commit();
            } 
            // If operation is update but title and director are missing, set an error message in the request
            else if (operation != null && operation.equals("update")) {
                request.setAttribute("error", "Please enter a title and director for update.");
            } 
            // If no operation is specified, create a new movie
            else if (movieTitle != null && !movieTitle.trim().isEmpty() && movieDirector != null && !movieDirector.trim().isEmpty()) {
                em.getTransaction().begin();
                em.persist(new Movies(movieTitle, movieDirector));
                em.getTransaction().commit();
            } else {
                // If title and director are missing, set an error message in the request
                request.setAttribute("error", "Please enter a title and director.");
            }

            // Retrieve a list of all movies and set it in the request
            List<Movies> movieList = em.createQuery("SELECT m FROM Movies m", Movies.class).getResultList();
            request.setAttribute("movies", movieList);
            
            // Forward request to the JSP page
            request.getRequestDispatcher("/movies.jsp").forward(request, response);
        } finally {
            // Ensure any active transaction is rolled back and EntityManager is closed
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
        }
    }
}
