# solr-model

## model

Provides the entities annotated with @SolrDocument for use with [solr-indexing](https://github.com/i-Asset/solr-indexing) as well as classes for searching and search result processing.

The common classes for indexing are 

* `Concept`: abstract base class defining common attributes such as multilingual labelling, code, namespace and identifier. 
* `ClassType`: results in the SOLR collection "concept_class", allows parent/child structures and interlinks with `PropertyType`.
* `PropertyType`: results in the SOLR collection "property", holds metadata of properties for `ClassType`.
* `CodedType`: result in the SOLR collection "codes", holds coded value lists for properties.

All indexed entities inherit from `Concept` thus have multilingual preferred, alternate and hidden labels.

## search & result

The model provides helper classes for searching and result processing. By using the `Search` class, a valid SOLR search can be constructed programmatically. A search always results in a (generic) `SearchResult` providing the result as well as paging information. Faceted searches are provided with the `FacetResult`. Finally, each collection in the index can provide a list of field names (`IndexField`). The field names are enriched with names from the `PropertyType` index when applicable.

## (re)using

When using the solr-module as a dependency, ensure that the using module does not try to contact SOLR on startup. Add

```
management.health.solr.enabled=false
```
to the `application.properties` of the spring-boot application.

