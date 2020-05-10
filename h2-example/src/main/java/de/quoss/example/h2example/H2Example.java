package de.quoss.example.h2example;

import de.quoss.example.h2example.bean.Kunde;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class H2Example {

    private void run() throws Exception {
        // load driver class
        // Class.forName("org.h2.Driver");
        // get connection
        Connection connection = DriverManager.getConnection("jdbc:h2:~/test", "sa", "");
        // create statement
        Statement statement = connection.createStatement();
        // define sql string
        String sql = readStringFromClasspathResource("selectUks.sql");
        // execute statement
        if (statement.execute(sql)) {
            System.out.println(String.format("Result: %s", statement.getResultSet()));
        } else {
            System.out.println(String.format("Update count: %s", statement.getUpdateCount()));
        }
        // close statement
        statement.close();
        // close connection
        connection.close();
        // instantiate kunde object
        Kunde kunde = new Kunde(0);
        // insert using hibernate
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("de.quoss.example.h2example.bean.Kunde");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(kunde);
        entityManager.getTransaction().commit();
        entityManager.close();
        entityManagerFactory.close();
    }

    private String readStringFromClasspathResource(final String resourceName) throws Exception {
        StringBuilder stringBuilder = new StringBuilder();
        byte[] buffer = new byte[1024];
        InputStream inputStream = getStreamFromClasspath(resourceName);
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            stringBuilder.append(new String(buffer).substring(0, bytesRead));
        }
        return stringBuilder.toString();
    }

    private InputStream getStreamFromClasspath(final String resourceName) throws Exception {
        InputStream result = getClass().getClassLoader().getResourceAsStream(resourceName);
        if (result == null) {
            throw new IOException(String.format("Resource %s not found in classpath.", resourceName));
        }
        return result;
    }

    public static void main(String[] args) throws Exception {
        new H2Example().run();
    }
}
