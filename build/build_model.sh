#!/bin/sh
##
CWD=$(pwd)
echo "CWD: $CWD"

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