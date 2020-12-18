#!/bin/sh
##
CWD=$(pwd)
echo "CWD: $CWD"

#--------------------------------------------------------------------------
# Build SOLR-prjects
#--------------------------------------------------------------------------
cd ../solr-projects/solr-model
mvn clean

cd ../solr-indexing
mvn clean
cd "$CWD"

#--------------------------------------------------------------------------
# data model projects
#--------------------------------------------------------------------------
cd ../common-projects/data-model
mvn clean

cd ./asset-data-model
mvn clean

cd ../indexing-data-model
mvn clean

cd ../semantic-lookup-data-model # build semantic lookup data model
mvn clean

cd ../ubl-data-model
mvn clean
cd "$CWD"

#--------------------------------------------------------------------------
# common projects
#--------------------------------------------------------------------------
cd ../common-projects
mvn clean

cd ./asset-registry-connector
mvn clean

cd ../utility
mvn clean

cd ../nimble-rest-client
mvn clean

cd ../serivce-discovery
mvn clean
cd "$CWD"
