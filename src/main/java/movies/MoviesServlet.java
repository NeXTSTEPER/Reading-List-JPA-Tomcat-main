package movies;

// Importing necessary libraries
import java.io.IOException; // Handling I/O exceptions
import java.util.List; // Using List for handling collections of objects
import javax.servlet.ServletException; // Handling servlet exceptions
import javax.servlet.http.*; // Using HttpServlet, HttpServletRequest, HttpServletResponse

import books.Books;

import javax.persistence.*; // Importing classes related to database operations

public class MoviesServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        EntityManagerFactory emf = (EntityManagerFactory)getServletContext().getAttribute("emf");
        EntityManager em = emf.createEntityManager();

        try {
            String movieTitle = request.getParameter("movieTitle");
            String movieDirector = request.getParameter("movieDirector");

            if (movieTitle != null && !movieTitle.trim().isEmpty() && movieDirector != null && !movieDirector.trim().isEmpty()) {
                em.getTransaction().begin();
                em.persist(new Movies(movieTitle, movieDirector));
                em.getTransaction().commit();
            } else {
                request.setAttribute("error", "Please enter a title and director.");
            }

            List<Movies> movieList = em.createQuery("SELECT m FROM Movies m", Movies.class).getResultList();
            request.setAttribute("movies", movieList);

            List<Books> bookList = em.createQuery("SELECT b FROM Books b", Books.class).getResultList();
            request.setAttribute("books", bookList);

            request.getRequestDispatcher("/movies.jsp").forward(request, response);
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String movieTitle = request.getParameter("movieTitle");
        String movieDirector = request.getParameter("movieDirector");
        String operation = request.getParameter("operation");
        String id = request.getParameter("id");
        String bookId = request.getParameter("bookId");
        
        System.out.println("bookId: " + bookId); // print the bookId to the console

        EntityManagerFactory emf = (EntityManagerFactory)getServletContext().getAttribute("emf");
        EntityManager em = emf.createEntityManager();

        try {
            if (operation != null && operation.equals("delete") && id != null) {
                em.getTransaction().begin();
                Movies movie = em.find(Movies.class, Integer.parseInt(id));
                if (movie != null) {
                    em.remove(movie);
                }
                em.getTransaction().commit();
            }
            else if (operation != null && operation.equals("update") && movieTitle != null && !movieTitle.trim().isEmpty() && movieDirector != null && !movieDirector.trim().isEmpty() && id != null) {
                em.getTransaction().begin();
                Movies movie = em.find(Movies.class, Integer.parseInt(id));
                if (movie != null) {
                    movie.setMovieTitle(movieTitle);
                    movie.setMovieDirector(movieDirector);
                }
                em.getTransaction().commit();
            }
            else if (operation != null && operation.equals("update")) {
                request.setAttribute("error", "Please enter a title and director for update.");
            }
            else if (movieTitle != null && !movieTitle.trim().isEmpty() && movieDirector != null && !movieDirector.trim().isEmpty()) {
                em.getTransaction().begin();
                Movies movie = new Movies(movieTitle, movieDirector);

                if (bookId != null && !bookId.trim().isEmpty() && !bookId.trim().equals("-1")) {
                    Books book = em.find(Books.class, Integer.parseInt(bookId));
                    if (book != null) {
                        movie.setBook(book);
                    } else {
                        request.setAttribute("error", "No book found with the provided id.");
                    }
                }

                em.persist(movie);
                em.getTransaction().commit();
            } else {
                request.setAttribute("error", "Please enter a movie title and director.");
            }

            List<Movies> movieList = em.createQuery("SELECT m FROM Movies m", Movies.class).getResultList();
            request.setAttribute("movies", movieList);

            List<Books> bookList = em.createQuery("SELECT b FROM Books b", Books.class).getResultList();
            request.setAttribute("books", bookList);

            request.getRequestDispatcher("/movies.jsp").forward(request, response);
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
        }
    }
}