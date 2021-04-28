import java.io.FileInputStream;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;


public class App {
    public static void main(String[] args) throws Exception {
        Model schema = ModelFactory.createDefaultModel();
        String base = "https://shmishtopher.github.io";
        FileInputStream file = new FileInputStream("university.ttl");

        // Parse Graph
        schema.read(file, base, "TTL");

        // Run All Queries
        try (QueryExecution exec = QueryExecutionFactory.create(SELECT_00, schema)) {
            ResultSetFormatter.out(System.out, exec.execSelect(), SELECT_00);
        }

        try (QueryExecution exec = QueryExecutionFactory.create(SELECT_01, schema)) {
            ResultSetFormatter.out(System.out, exec.execSelect(), SELECT_01);
        }

        try (QueryExecution exec = QueryExecutionFactory.create(SELECT_02, schema)) {
            ResultSetFormatter.out(System.out, exec.execSelect(), SELECT_02);
        }

        try (QueryExecution exec = QueryExecutionFactory.create(SELECT_03, schema)) {
            ResultSetFormatter.out(System.out, exec.execSelect(), SELECT_03);
        }

        try (QueryExecution exec = QueryExecutionFactory.create(SELECT_04, schema)) {
            ResultSetFormatter.out(System.out, exec.execSelect(), SELECT_04);
        }

        try (QueryExecution exec = QueryExecutionFactory.create(CONSTRUCT_00, schema)) {
            exec.execConstruct().write(System.out);
        }

        try (QueryExecution exec = QueryExecutionFactory.create(CONSTRUCT_01, schema)) {
            exec.execConstruct().write(System.out);
        }

        try (QueryExecution exec = QueryExecutionFactory.create(ASK_00, schema)) {
            System.out.println(exec.execAsk());
        }

        try (QueryExecution exec = QueryExecutionFactory.create(DESCRIBE_00, schema)) {
            exec.execDescribe().write(System.out);
        }
    }

    private static final String QUERY_BASE = (
        "PREFIX :       <https://shmishtopher.github.io#>" +
        "PREFIX rdf:    <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
        "PREFIX rdfs:   <http://www.w3.org/2000/01/rdf-schema#>" +
        "PREFIX foaf:   <http://xmlns.com/foaf/0.1/>" +
        "PREFIX univ:   <http://www.cs.ccsu.edu/~neli/university.owl#>"
    );

    // Collect the department's mailing list
    private static final Query SELECT_00 = QueryFactory.create(QUERY_BASE +
        "SELECT ?name ?mbox WHERE {" +
        "   ?x a univ:Professor." +
        "   ?x foaf:name ?name." +
        "   ?x foaf:mbox ?mbox." +
        "}"
    );

    // Collect the professors who are teaching CS-407 (Advanced Topics)
    private static final Query SELECT_01 = QueryFactory.create(QUERY_BASE +
        "SELECT ?name WHERE {" +
        "   ?x a univ:Professor." +
        "   ?x univ:teaches :CS-407." +
        "   ?x foaf:name ?name." +
        "}"
    );

    // Collect the departments phone numbers
    private static final Query SELECT_02 = QueryFactory.create(QUERY_BASE +
        "SELECT ?name ?tel WHERE {" +
        "   ?x a univ:Professor." +
        "   ?x foaf:name ?name." +
        "   ?x foaf:tel ?tel." +
        "}"
    );

    // Collect the student IDs of every student taking CS-355
    private static final Query SELECT_03 = QueryFactory.create(QUERY_BASE +
        "SELECT ?id WHERE {" +
        "   ?x a univ:Student." +
        "   ?x univ:studies :CS-355." +
        "   ?x univ:studentID ?id." +
        "}"
    );

    // Collect the names of every student taking a class with Dr. Zlatareva
    private static final Query SELECT_04 = QueryFactory.create(QUERY_BASE +
        "SELECT DISTINCT ?name ?class WHERE {" +
        "   ?x a univ:Student." +
        "   ?x foaf:name ?name." +
        "   ?x univ:studies ?class." +
        "   :neli-zlatareva univ:teaches ?class." +
        "}"
    );

    // Construct a new graph with all of the courses offered by the department
    private static final Query CONSTRUCT_00 = QueryFactory.create(QUERY_BASE +
        "CONSTRUCT { univ:CSDepartment univ:offers ?x }" +
        "WHERE { ?x a univ:Course }"
    );

    // Construct a new graph with all of the students that study together
    private static final Query CONSTRUCT_01 = QueryFactory.create(QUERY_BASE +
        "CONSTRUCT { ?x foaf:knows ?y }" +
        "WHERE {" +
        "   ?x univ:studies ?class." +
        "   ?y univ:studies ?class." +
        "}"
    );

    // Query weather Dr. Zlatareva is teaching CS-407 this semester
    private static final Query ASK_00 = QueryFactory.create(QUERY_BASE +
        "ASK {" +
        "   ?x foaf:name \"Neli Zlatareva\"." +
        "   ?x univ:teaches :CS-407." +
        "}"
    );

    // Describe Dr. Zlatareva
    private static final Query DESCRIBE_00 = QueryFactory.create(QUERY_BASE +
        "DESCRIBE ?x WHERE { ?x foaf:name \"Neli Zlatareva\". }"
    );
}
