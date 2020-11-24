package de.quoss.example.getpost;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

public class GetPostExample {

    private String connectionUrl;

    private String accept;

    private String payLoad;

    private String user;

    private String password;

    public GetPostExample() {
        super();
    }

    public GetPostExample(final String connectionUrl, final String accept, final String payLoad, final String user, final String password) {
        super();
        this.connectionUrl = connectionUrl;
        this.accept = accept;
        this.payLoad = payLoad;
        this.user = user;
        this.password = password;
    }

    private String run(final String[] args) throws Exception {
        checkCommandLineArguments(args);
        return run();
    }

    public String run() throws Exception {
        if (connectionUrl.startsWith("http")) {
            return runHttp();
        } else if (connectionUrl.startsWith("https")) {
            return runHttps();
        } else {
            throw new RuntimeException(usage());
        }
    } 

    private String runHttps() {
        throw new RuntimeException("Not implemented.");
    }

    private String runHttp() throws Exception {
        // open connection
        URL url = new URL(connectionUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        // accept header
        connection.setRequestProperty("Accept", accept);
        // set basic auth, if user and password are provided
        if (user != null) {
            if (password == null) {
                throw new RuntimeException("User without password.");
            }
            String basicAuth = String.format("Basic %s", Base64.getEncoder().encodeToString(String.format("%s:%s", user, password).getBytes()));
            connection.setRequestProperty("Authentication", basicAuth);
        }
        // set request method and write payLoad, depending on payload
        if (payLoad == null) {
            connection.setRequestMethod("GET");
        } else {
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            writePayLoad(connection.getOutputStream());
        }
        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            System.out.format("Http request successful: [%s] %s%n", connection.getResponseCode(), connection.getResponseMessage());
            String contentType = connection.getContentType();
            System.out.format("Content type: %s%n", contentType);
            return readResponse(connection.getInputStream());
        } else {
            System.err.format("Error running http request: [%s] %s%n", connection.getResponseCode(), connection.getResponseMessage());
        }
        return null;
    }

    private String readResponse(final InputStream inputStream) throws Exception {
        StringBuilder builder = new StringBuilder();
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            builder.append(new String(buffer).substring(0, bytesRead));
        }
        return builder.toString();
    }

    private void writePayLoad(final OutputStream outputStream) throws Exception {
        outputStream.write(payLoad.getBytes());
    }

    private void checkCommandLineArguments(final String[] args) {
        if (args.length != 2 && args.length != 3 && args.length != 5) {
            throw new RuntimeException(usage());
        }
        switch (args.length) {
            case 2:
                connectionUrl = args[0];
                accept = args[1];
                payLoad = null;
                user = null;
                password = null;
                break;
            case 3:
                connectionUrl = args[0];
                accept = args[1];
                payLoad = args[2];
                user = null;
                password = null;
                break;
            case 5:
                connectionUrl = args[0];
                accept = args[1];
                payLoad = args[2];
                user = args[3];
                password = args[4];
                break;
            default:
                throw new RuntimeException(usage());
        }
    }

    private String usage() {
        return String.format("USAGE: java %s connectionUrl accept [payLoad] [user password]", getClass().getName());
    }

    public static void main(final String[] args) throws Exception {
        System.out.format("Response: %s%n", new GetPostExample().run(args));
    }
    
}