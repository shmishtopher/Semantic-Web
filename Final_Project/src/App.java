import java.io.InputStream;
import java.net.URL;

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
    }
}
