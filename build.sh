#!/bin/bash
#
# - refresh base images by pulling from docker hub
# - build and tag images locally
# - push built images  to docker hub
#
p=`dirname $0`
DIR=`realpath "$p"`

cleanup() {
    echo "build process has failed"
}

trap "cleanup; exit 1" ERR

cd "$DIR" 
docker build --pull -t ipbhalle/fasta-search-service .
docker push ipbhalle/fasta-search-service

