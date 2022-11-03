
docker-install-access:
	chmod u+x install-docker.sh

docker-install:
	./install-docker.sh

docker: docker-install-access docker-install


docker-compose-install-access:
	chmod u+x install-docker-compose.sh

docker-compose-install:
	./install-docker-compose.sh

docker-compose: docker-compose-install-access docker-compose-install

run : docker docker-compose
