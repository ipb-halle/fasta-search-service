#
# Build web project with Maven
#
# Dockerfile: https://github.com/carlossg/docker-maven/blob/ac292f26884bf2be9fe69f6e397da3b124c1e35c/eclipse-temurin-17/Dockerfile
FROM maven:3.8.5-openjdk-17 AS mavenBuild
LABEL stage=fasta-search-service-builder

WORKDIR /usr/src/app

COPY ./ .

RUN mvn -B -e -C -pl '!integration-tests' clean package

#
# Build fasta36
#
# Note:
# The build and runtime environments need to be the same, because different Debian 
# versions come with different versions of glibc.
#
# Dockerfile: https://github.com/tomitribe/docker-tomee/blob/14d51e67fad7eb358b39fcc09fa9cd0e7e205968/TomEE-9.0/jre17/Temurin/ubuntu/webprofile/Dockerfile
# inherits from https://github.com/adoptium/containers/blob/0a0eef5b0673a25403d4b0fe87e4f4e07a4297ab/17/jre/ubuntu/jammy/Dockerfile.releases.full
FROM tomee:9.0.0-jre17-webprofile AS fasta36Build
LABEL stage=fasta-search-service-builder

RUN set -ex \
	&& apt-get update \
	&& apt-get -y install gcc make git libpq-dev libmariadb-dev \
	&& mkdir -p /build \
	&& cd /build \
	&& git clone https://github.com/wrpearson/fasta36.git \
	&& cp -r fasta36 fasta36_postgresql \
	&& mv fasta36 fasta36_mariadb \
	&& cd /build/fasta36_postgresql \
	&& git checkout 8036daa401eb5420202bd0d216d047a0cd2a61fe \
	&& cd src \
	&& make -f ../make/Makefile.linux_pgsql all \
	&& cd /build/fasta36_mariadb \
	&& git checkout 8036daa401eb5420202bd0d216d047a0cd2a61fe \
	&& cd src \
	&& make -f ../make/Makefile.linux_mariadb all

#
# Prepare a TomEE container with empty webapps directory and start TomEE as unprivileged user
#
# Dockerfile: https://github.com/tomitribe/docker-tomee/blob/14d51e67fad7eb358b39fcc09fa9cd0e7e205968/TomEE-9.0/jre17/Temurin/ubuntu/webprofile/Dockerfile
# inherits from https://github.com/adoptium/containers/blob/0a0eef5b0673a25403d4b0fe87e4f4e07a4297ab/17/jre/ubuntu/jammy/Dockerfile.releases.full
FROM tomee:9.0.0-jre17-webprofile

ENV PATH /usr/local/tomee/bin:$PATH

RUN set -ex \
	&& apt-get update \
	&& apt-get -y install libpq-dev libmariadb-dev \
	&& rm -r /usr/local/tomee/webapps/* \
	&& groupadd tomee \
	&& useradd -g tomee tomee \
	&& chown -R tomee:tomee /usr/local/tomee

COPY --from=mavenBuild /usr/src/app/service/target/fasta-search-service.war /usr/local/tomee/webapps/
COPY --from=fasta36Build /build /usr/local/

USER tomee
EXPOSE 8080
CMD ["catalina.sh", "run"]
