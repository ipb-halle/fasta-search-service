#
# Build web project with Maven
#
# Dockerfile: https://github.com/carlossg/docker-maven/blob/84e75894a94d204ab660c271e32984019ceb4070/openjdk-11/Dockerfile
FROM maven:3.8.3-openjdk-11 AS mavenBuild
LABEL stage=fasta-search-service-builder

WORKDIR /usr/src/app

COPY ./service/ .

RUN mvn -B -e -C package

#
# Build fasta36
#
# Note:
# The build and runtime environments need to be the same, because different Debian 
# versions come with different versions of glibc.
#
# Dockerfile: https://github.com/tomitribe/docker-tomee/blob/master/TomEE-9.0/jre11/webprofile/Dockerfile
# inherits from https://github.com/docker-library/openjdk/blob/3ce4449d7f01afa6001a711e17cdd379b76848f8/11/jre/buster/Dockerfile
FROM tomee:11-jre-9.0.0-M7-webprofile AS fasta36Build
LABEL stage=fasta-search-service-builder

RUN set -ex \
	&& apt-get update \
	&& apt-get -y install gcc make git libssl-dev libpq-dev \
	&& mkdir -p /build \
	&& cd /build \
	&& git clone https://github.com/wrpearson/fasta36.git \
	&& cd fasta36 \
	&& git checkout 8036daa401eb5420202bd0d216d047a0cd2a61fe \
	&& cd src \
	&& make -f ../make/Makefile.linux_pgsql all

#
# Prepare a TomEE container with empty webapps directory and start TomEE as unprivileged user
#
# Dockerfile: https://github.com/tomitribe/docker-tomee/blob/master/TomEE-9.0/jre11/webprofile/Dockerfile
# inherits from https://github.com/docker-library/openjdk/blob/3ce4449d7f01afa6001a711e17cdd379b76848f8/11/jre/buster/Dockerfile
FROM tomee:11-jre-9.0.0-M7-webprofile

ENV PATH /usr/local/tomee/bin:$PATH

RUN set -ex \
	&& rm -r /usr/local/tomee/webapps/* \
	&& groupadd tomee \
	&& useradd -g tomee tomee \
	&& chown -R tomee:tomee /usr/local/tomee

COPY --from=mavenBuild /usr/src/app/target/Fasta-Search-Service.war /usr/local/tomee/webapps/
COPY --from=fasta36Build /build /usr/local/

USER tomee
EXPOSE 8080
CMD ["catalina.sh", "run"]