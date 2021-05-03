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
        InputStream stream = new URL("https", "raw.githubusercontent.com", "/shmishtopher/Semantic-Web/master/Final_Project/cars.owl").openStream();

        // Stream the remote data source into the default
        // model and clean up the stream.
        schema.read(stream, null, "TURTLE");
        stream.close();

        // Create a scaner and start the program
        Scanner in = new Scanner(System.in);
        System.out.println(UI.WELCOME);

        System.out.print("Looking for a specific make (e.g. Kia): ");
        String make = in.nextLine();

        System.out.print("Minimim city MPG (e.g. 25): ");
        String cityMPG = in.nextLine();

        System.out.print("Minimum highway MPG: ");
        String highwayMPG = in.nextLine();

        System.out.print("Looking for a hybrid (True/False): ");
        String hybrid = in.nextLine();

        System.out.print("Minimum HP (e.g. 100): ");
        String hp = in.nextLine();

        in.close();

        // Compute results
        Query query = Queries.autoQuery(make, cityMPG, highwayMPG, hybrid, hp);
        try (QueryExecution exec = QueryExecutionFactory.create(query, schema)) {
            ResultSetFormatter.out(System.out, exec.execSelect(), query);
        }
    }
}
