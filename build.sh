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

pushImage() {
    IMAGE=$1
    TAG=$2
    DST=$IMAGE:$TAG

    docker image tag $IMAGE $DST
    docker push $DST
}

trap "cleanup; exit 1" ERR

REVISION=`mvn org.apache.maven.plugins:maven-help-plugin:evaluate -Dexpression=project.version -q -DforceStdout`
IMAGE=ipbhalle/fasta-search-service
MAJOR=`echo $REVISION | cut -d. -f1`
MINOR=`echo $REVISION | cut -d. -f2`

cd "$DIR" 
docker build --pull -t $IMAGE .
pushImage $IMAGE $MAJOR

