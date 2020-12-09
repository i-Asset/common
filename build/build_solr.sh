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