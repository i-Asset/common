#!/bin/sh
##
CWD=$(pwd)
echo "CWD: $CWD"

#--------------------------------------------------------------------------
# common projects
#--------------------------------------------------------------------------
cd ../common-projects
mvn clean install -Dmaven.test.skip=true # build base pom directory

cd ./asset-registry-connector
mvn clean install -Dmaven.test.skip=true # build asset registry connector

cd ../nimble-rest-client
mvn clean install -Dmaven.test.skip=true # Nimble compatible rest client

cd ../serivce-discovery
mvn clean install -Dmaven.test.skip=true # build service discovery

cd ../utility
mvn clean install -Dmaven.test.skip=true # build utility
cd "$CWD"