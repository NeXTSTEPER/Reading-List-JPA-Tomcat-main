package books;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.persistence.*;

public class BookServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        EntityManagerFactory emf = (EntityManagerFactory)getServletContext().getAttribute("emf");
        EntityManager em = emf.createEntityManager();

        try {
            String bookTitle = request.getParameter("bookTitle");
            String bookAuthor = request.getParameter("bookAuthor");
            if (bookTitle != null && !bookTitle.trim().isEmpty() && bookAuthor != null && !bookAuthor.trim().isEmpty()) {
                em.getTransaction().begin();
                em.persist(new Books(bookTitle, bookAuthor));
                em.getTransaction().commit();
            } else {
                request.setAttribute("error", "Please enter a title and author.");
            }

            List<Books> bookList = em.createQuery("SELECT b FROM Books b", Books.class).getResultList();
            request.setAttribute("books", bookList);
            request.getRequestDispatcher("/book.jsp").forward(request, response);
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
        String bookTitle = request.getParameter("bookTitle");
        String bookAuthor = request.getParameter("bookAuthor");
        String operation = request.getParameter("operation");
        String id = request.getParameter("id");

        EntityManagerFactory emf = (EntityManagerFactory)getServletContext().getAttribute("emf");
        EntityManager em = emf.createEntityManager();

        try {
            if (operation != null && operation.equals("delete") && id != null) {
                em.getTransaction().begin();
                Books book = em.find(Books.class, Integer.parseInt(id));
                if (book != null) {
                    em.remove(book);
                }
                em.getTransaction().commit();
            } else if (operation != null && operation.equals("update") && bookTitle != null && !bookTitle.trim().isEmpty() && bookAuthor != null && !bookAuthor.trim().isEmpty() && id != null) {
                em.getTransaction().begin();
                Books book = em.find(Books.class, Integer.parseInt(id));
                if (book != null) {
                    book.setBookTitle(bookTitle);
                    book.setBookAuthor(bookAuthor);
                }
                em.getTransaction().commit();
            } else if (operation != null && operation.equals("update")) {
                request.setAttribute("error", "Please enter a title and author for update.");
            } else if (bookTitle != null && !bookTitle.trim().isEmpty() && bookAuthor != null && !bookAuthor.trim().isEmpty()) {
                em.getTransaction().begin();
                em.persist(new Books(bookTitle, bookAuthor));
                em.getTransaction().commit();
            } else {
                request.setAttribute("error", "Please enter a title and author.");
            }

            List<Books> bookList = em.createQuery("SELECT b FROM Books b", Books.class).getResultList();
            request.setAttribute("books", bookList);
            request.getRequestDispatcher("/book.jsp").forward(request, response);
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
        }
    }
}
