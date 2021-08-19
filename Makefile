all: setup install start

setup:
	docker volume create db-data
install:
	docker-compose -f run --rm install
start:
	docker-compose up