# indexing-data-model

## model

Provides the entities annotated with @SolrDocument for use with [indexing-service](https://github.com/i-Asset/indexing-service). This module depends on [solr-model](https://github.com/i-Asset/solr-model).

The add-on classes for indexing are 

* `AssetType`: results in the SOLR collection "asset", stores arbitrary data from assets. Interlinks with `SubmodelType`.
* `SubmodelType`: results in the SOLR collection "asset_model", use in combination with `AssetType`.
* `PartyType`: result in the SOLR collection "party", holds basic information of persons, organizations.

All indexed entities inherit from `Concept` thus have multilingual preferred, alternate and hidden labels.

## search & result

The model provides helper classes for searching and result processing. By using the `Search` class, a valid SOLR search can be constructed programmatically. A search always results in a (generic) `SearchResult` providing the result as well as paging information. Faceted searches are provided with the `FacetResult`. Finally, each collection in the index can provide a list of field names (`IndexField`). The field names are enriched with names from the `PropertyType` index when applicable.

## (re)using

When using the indexing-data-model as a dependency, ensure that the using module does not try to contact SOLR on startup. Add

```
management.health.solr.enabled=false
```
to the `application.properties` of the spring-boot application.

