package de.quoss.example.h2example;

import de.quoss.example.h2example.bean.Kunde;

import de.quoss.sql.support.SqlSupport;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

public class H2Example {

    private static final Logger LOGGER = LoggerFactory.getLogger(H2Example.class);

    private static final String URL = "jdbc:h2:~/test";

    private void run() throws H2ExampleException {
        // create table uks
        LOGGER.info("Create table UKS: {}", SqlSupport.execute(URL, "sa", "",
                readStringFromClasspathResource("create-uks.sql")));
        // execute select
        LOGGER.info("Select table UKS (before): {}", SqlSupport.execute(URL, "sa", "",
                readStringFromClasspathResource("select-uks.sql")));
        // instantiate and format kunde object
        Kunde kunde = new Kunde(0, "Clemens Quoß", "");
        // insert using hibernate
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("de.quoss.example.h2example.bean.Kunde");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(kunde);
        entityManager.getTransaction().commit();
        entityManager.close();
        entityManagerFactory.close();
        // execute select
        LOGGER.info("Select table UKS (after): {}", SqlSupport.execute(URL, "sa", "",
                readStringFromClasspathResource("select-uks.sql")));
    }

    private static String readStringFromClasspathResource(final String resourceName) throws H2ExampleException {
        try (final InputStream stream = H2Example.class.getClassLoader().getResourceAsStream(resourceName)) {
            if (stream == null) {
                throw new H2ExampleException("Classpath resource " + resourceName + " not found.");
            }
            return new String(stream.readAllBytes());
        } catch (final IOException e) {
            throw new H2ExampleException("Error reading from classpath resource " + resourceName + ": " + e.getMessage(), e);
        }
    }

    public static void main(String[] args) throws H2ExampleException {
        new H2Example().run();
    }
}
