# common

common projects to be re-used by other micro-services, packages, projects...

the project consists of the "SOLR-model", "SOLR-indexing", multiple "data-model"-projects and "common"-projects

## How To build

simply run the build scripts to generate all required maven-packages

Build Order is:
1) SOLR-model
2) SOLR-indexing
3) asset-data-model
4) indexing-data-model
5) semantic-lookup-data-model
6) ubl-data-model
7) "asset registry connector" - project
8) "utility" - project
9) "nimble-rest-client" - project
10) "service-discovery" - project

maven-group-ID is "at/srfg/iot/common"
