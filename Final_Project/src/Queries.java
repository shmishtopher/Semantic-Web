import org.apache.jena.query.Query;
import org.apache.jena.query.QueryFactory;


public class Queries {
    private static final String QUERY_BASE = (
        "PREFIX :     <http://www.semanticweb.org/shmish/ontologies/2021/4/untitled-ontology-4#> " +
        "PREFIX owl:  <http://www.w3.org/2002/07/owl#>                                           " +
        "PREFIX rdf:  <http://www.w3.org/1999/02/22-rdf-syntax-ns#>                              " +
        "PREFIX xml:  <http://www.w3.org/XML/1998/namespace>                                     " +
        "PREFIX xsd:  <http://www.w3.org/2001/XMLSchema#>                                        " +
        "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>                                    "
    );

    public static final Query autoQuery(String make, String cityMPG, String highwayMPG, String hybrid, String hp) {
        return QueryFactory.create(String.format(QUERY_BASE + 
            "SELECT DISTINCT ?model WHERE {     " +
            "   ?car rdf:type :Model .          " +
            "   ?car :Maker ?maker .            " +
            "   ?car :City_MPG ?cityMPG .       " +
            "   ?car :Highway_MPG ?highwayMPG . " +
            "   ?car :Engine_HP ?hp .           " +
            "   ?car :ID ?model .               " +

            (make.equals("") ? "" : "?maker :Name '%s' .") +
            (cityMPG.equals("") ? "" : "FILTER (?cityMPG >= %s)") +
            (highwayMPG.equals("") ? "" : "FILTER (?highwayMPG >= %s)") +
            (hybrid.equals("") ? "" : "?car :Hybrid '%s") +
            (hp.equals("") ? "" : "FILTER (?hp >= %s)") +

            "} LIMIT 3                          " , make, cityMPG, highwayMPG, hybrid, hp)
        );
    }
}
