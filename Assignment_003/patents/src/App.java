import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;

public class App {
    public static void main(String[] args) throws Exception {
        // Define an empty RDF model and open a stream to
        // the data source.
        Model schema = ModelFactory.createDefaultModel();
        InputStream stream = new URL("https", "data.nasa.gov", "/api/views/gquh-watm/rows.rdf").openStream();

        // Stream the remote data source into the default
        // model and clean up the stream.
        schema.read(stream, null);
        stream.close();

        // Set up the scanner and application state
        boolean titleMode = true;
        Scanner scanner = new Scanner(System.in);

        // Present the application UI
        System.out.println(UI.WELCOME);

        // Main application loop
        while (true) {
            // Get user input
            System.out.print(titleMode ? UI.TITLE_CURSOR : UI.PATENT_CURSOR);
            String input = scanner.nextLine();

            // Parse control codes
            if (input.contains("@q")) {
                scanner.close();
                break;
            } else if (input.contains("@t")) {
                titleMode = true;
            } else if (input.contains("@p")) {
                titleMode = false;
            } else if (titleMode) {
                // Perform a search for keywords in patent titles
                Query query = Queries.titleQuery(input);
                try (QueryExecution exec = QueryExecutionFactory.create(query, schema)) {
                    ResultSetFormatter.out(System.out, exec.execSelect(), query);
                }
            } else {
                // Perform a search for patent numbers
                Query query = Queries.patentNumber(input);
                try (QueryExecution exec = QueryExecutionFactory.create(query, schema)) {
                    ResultSetFormatter.out(System.out, exec.execSelect(), query);
                }
            }
        }
    }
}
