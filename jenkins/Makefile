VERSION_FILE := ./VERSION
TAG := $(shell cat ${VERSION_FILE})
DOCKER_GID := $(shell getent group docker | awk -F: '{printf "%d",$$3}')
SERVICE := 'jenkins'

compose-run:
	export DOCKER_GID=$(DOCKER_GID); docker-compose up --build -d;

run: compose-run

compose-destroy:
	docker-compose down;
destroy: compose-destroy

log:
	docker logs -f $(SERVICE)

exec:
	docker exec -it $(SERVICE) sh -c "((clear && bash) || (clear && sh))"
