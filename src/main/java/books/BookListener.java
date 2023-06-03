package books;

// Importing necessary libraries
import javax.persistence.*; // Importing classes related to database operations
import javax.servlet.*; // Importing classes related to servlet operations

// Implementing ServletContextListener to listen to context events
public class BookListener implements ServletContextListener {

    // This method is called when the servlet context is initialized(when the Web Application is deployed).
    // We use it to initialize our EntityManagerFactory.
    @Override
    public void contextInitialized(ServletContextEvent e) {
        // Create an EntityManagerFactory for the persistence unit "RL".
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("RL");
        // Set the EntityManagerFactory in the ServletContext, so it can be used elsewhere in the application.
        e.getServletContext().setAttribute("emf", emf);
    }

    // This method is called when the servlet Context is destroyed(when the Web Application is undeployed).
    // We use it to close our EntityManagerFactory.
    @Override
    public void contextDestroyed(ServletContextEvent e) {
        // Retrieve the EntityManagerFactory from the ServletContext.
        EntityManagerFactory emf = (EntityManagerFactory) e.getServletContext().getAttribute("emf");
        // Close the EntityManagerFactory to release all resources.
        emf.close();
    }
}
