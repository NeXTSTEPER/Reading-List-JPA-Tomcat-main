package books;

import javax.persistence.*;
import javax.servlet.*;

public class BookListener implements ServletContextListener {

    // Prepare the EntityManagerFactory:
    @Override
    public void contextInitialized(ServletContextEvent e) {
        EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("RL");
        e.getServletContext().setAttribute("emf", emf);
    }

    // Release the EntityManagerFactory:
    @Override
    public void contextDestroyed(ServletContextEvent e) {
        EntityManagerFactory emf =
            (EntityManagerFactory)e.getServletContext().getAttribute("emf");
        emf.close();
    }
}
