PREFIX rdf:    <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX rdfs:   <http://www.w3.org/2000/01/rdf-schema#>
PREFIX foaf:   <http://xmlns.com/foaf/0.1/>
PREFIX univ:   <http://www.cs.ccsu.edu/~neli/university.owl#>

DELETE { ?class rdfs:label "Game Development"; }
INSERT DATA {
  <https://shmishtopher.github.io#CS-420>
    a univ:class;
    rdfs:label "Modern Game Development".
}

DELETE { ?class rdfs:label "Computer Graphics"; }
INSERT DATA {
  <https://shmishtopher.github.io#CS-420>
    a univ:class;
    rdfs:label "Computer Vision".
}