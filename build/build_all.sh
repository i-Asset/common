#!/bin/sh
##
CWD=$(pwd)
echo "CWD: $CWD"

#--------------------------------------------------------------------------
# Build SOLR-prjects
#--------------------------------------------------------------------------
cd ../solr-projects/solr-model
mvn clean install -Dmaven.test.skip=true # build solr model

cd ../solr-indexing
mvn clean install -Dmaven.test.skip=true # build solr indexing
cd "$CWD"

#--------------------------------------------------------------------------
# data model projects
#--------------------------------------------------------------------------
cd ../common-projects/data-model
mvn clean install -Dmaven.test.skip=true # build data model - base

cd ./asset-data-model
mvn clean install -Dmaven.test.skip=true # build asset data model

cd ../indexing-data-model
mvn clean install -Dmaven.test.skip=true # build indexing data model

cd ../semantic-lookup-data-model # build semantic lookup data model
mvn clean install -Dmaven.test.skip=true

cd ../ubl-data-model
mvn clean install -Dmaven.test.skip=true # build ubl data model
cd "$CWD"

#--------------------------------------------------------------------------
# common projects
#--------------------------------------------------------------------------
cd ../common-projects
mvn clean install -Dmaven.test.skip=true # build base pom directory

cd ./asset-registry-connector
mvn clean install -Dmaven.test.skip=true # build asset registry connector

cd ../utility
mvn clean install -Dmaven.test.skip=true # build utility

cd ../nimble-rest-client
mvn clean install -Dmaven.test.skip=true # Nimble compatible rest client

cd ../serivce-discovery
mvn clean install -Dmaven.test.skip=true # build service discovery
cd "$CWD"
