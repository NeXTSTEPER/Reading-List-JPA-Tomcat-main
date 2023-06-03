package books;

// Importing necessary libraries
import java.io.IOException; // Handling I/O exceptions
import java.util.List; // Using List for handling collections of objects
import javax.servlet.ServletException; // Handling servlet exceptions
import javax.servlet.http.*; // Using HttpServlet, HttpServletRequest, HttpServletResponse
import javax.persistence.*; // Importing classes related to database operations

// Class extending HttpServlet to handle HTTP requests related to books
public class BookServlet extends HttpServlet {
    
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
            // Extract book title and author from request
            String bookTitle = request.getParameter("bookTitle");
            String bookAuthor = request.getParameter("bookAuthor");
            
            // If the parameters are not null or empty, create a new book
            if (bookTitle != null && !bookTitle.trim().isEmpty() && bookAuthor != null && !bookAuthor.trim().isEmpty()) {
                em.getTransaction().begin();
                em.persist(new Books(bookTitle, bookAuthor));
                em.getTransaction().commit();
            } else {
                // If the parameters are null or empty, set an error message in the request
                request.setAttribute("error", "Please enter a title and author.");
            }

            // Retrieve a list of all books and set it in the request
            List<Books> bookList = em.createQuery("SELECT b FROM Books b", Books.class).getResultList();
            request.setAttribute("books", bookList);
            
            // Forward request to the JSP page
            request.getRequestDispatcher("/book.jsp").forward(request, response);
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
        // Extract book title, author, operation type, and id from request
        String bookTitle = request.getParameter("bookTitle");
        String bookAuthor = request.getParameter("bookAuthor");
        String operation = request.getParameter("operation");
        String id = request.getParameter("id");

        // Create EntityManager for database operations
        EntityManagerFactory emf = (EntityManagerFactory)getServletContext().getAttribute("emf");
        EntityManager em = emf.createEntityManager();

        try {
            // If operation is delete, find the book by id and remove it
            if (operation != null && operation.equals("delete") && id != null) {
                em.getTransaction().begin();
                Books book = em.find(Books.class, Integer.parseInt(id));
                if (book != null) {
                    em.remove(book);
                }
                em.getTransaction().commit();
            } 
            // If operation is update, find the book by id and update its title and author
            else if (operation != null && operation.equals("update") && bookTitle != null && !bookTitle.trim().isEmpty() && bookAuthor != null && !bookAuthor.trim().isEmpty() && id != null) {
                em.getTransaction().begin();
                Books book = em.find(Books.class, Integer.parseInt(id));
                if (book != null) {
                    book.setBookTitle(bookTitle);
                    book.setBookAuthor(bookAuthor);
                }
                em.getTransaction().commit();
            } 
            // If operation is update but title and author are missing, set an error message in the request
            else if (operation != null && operation.equals("update")) {
                request.setAttribute("error", "Please enter a title and author for update.");
            } 
            // If no operation is specified, create a new book
            else if (bookTitle != null && !bookTitle.trim().isEmpty() && bookAuthor != null && !bookAuthor.trim().isEmpty()) {
                em.getTransaction().begin();
                em.persist(new Books(bookTitle, bookAuthor));
                em.getTransaction().commit();
            } else {
                // If title and author are missing, set an error message in the request
                request.setAttribute("error", "Please enter a title and author.");
            }

            // Retrieve a list of all books and set it in the request
            List<Books> bookList = em.createQuery("SELECT b FROM Books b", Books.class).getResultList();
            request.setAttribute("books", bookList);
            
            // Forward request to the JSP page
            request.getRequestDispatcher("/book.jsp").forward(request, response);
        } finally {
            // Ensure any active transaction is rolled back and EntityManager is closed
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
        }
    }
}
