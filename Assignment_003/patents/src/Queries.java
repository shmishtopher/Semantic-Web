import org.apache.jena.query.Query;
import org.apache.jena.query.QueryFactory;

public class Queries {
    private static final String QUERY_BASE = (
        "PREFIX rdf:        <http://www.w3.org/1999/02/22-rdf-syntax-ns#>                        " +
        "PREFIX rdfs:       <http://www.w3.org/2000/01/rdf-schema#>                              " +
        "PREFIX socrata:    <http://www.socrata.com/rdf/terms#>                                  " +
        "PREFIX dcat:       <http://www.w3.org/ns/dcat#>                                         " +
        "PREFIX ods:        <http://open-data-standards.github.com/2012/01/open-data-standards#> " +
        "PREFIX dcterm:     <http://purl.org/dc/terms/>                                          " +
        "PREFIX geo:        <http://www.w3.org/2003/01/geo/wgs84_pos#>                           " +
        "PREFIX skos:       <http://www.w3.org/2004/02/skos/core#>                               " +
        "PREFIX foaf:       <http://xmlns.com/foaf/0.1/>                                         " +
        "PREFIX dbase:      <https://data.nasa.gov/resource/>                                    " +
        "PREFIX ds:         <https://data.nasa.gov/resource/nasa-patents/>                       "
    );

    public static final Query patentNumber(String patent) {
        return QueryFactory.create(String.format(QUERY_BASE +
            "SELECT DISTINCT ?number ?title ?center ?status WHERE { " +
            "   ?s ds:patent_number ?number.                        " +
            "   ?s ds:title ?title.                                 " +
            "   ?s ds:center ?center.                               " +
            "   ?s ds:status ?status.                               " +
            "   FILTER contains(?number, '%S')                      " +
            "}                                                      ", patent
        ));
    }

    public static final Query titleQuery(String title) {
        return QueryFactory.create(String.format(QUERY_BASE +
            "SELECT DISTINCT ?number ?title ?center ?status WHERE { " +
            "   ?s ds:patent_number ?number.                        " +
            "   ?s ds:title ?title.                                 " +
            "   ?s ds:center ?center.                               " +
            "   ?s ds:status ?status.                               " +
            "   FILTER contains(?title, '%S')                       " +
            "}                                                      ", title
        ), title);
    }
}
