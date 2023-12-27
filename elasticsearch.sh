#!/bin/bash

start_service() {
    # Lógica para iniciar el servicio
    echo "Iniciando el servicio Elasticsearch..."
    docker-compose -f docker-compose-elasticsearch.yml up
}

clean_service() {
    # Lógica para detener el servicio
    echo "lLimpiando el servicio Elasticsearch..."
    docker-compose -f docker-compose-elasticsearch.yml down -v
}

case "$1" in

    memory)
        # prepara la memoria del sistema para que funcione Elasticsearch
        sudo sysctl -w vm.max_map_count=262144
        ;;
    start)
        start_service
        ;;
    clean)
        stop_service
        ;;
    *)
        echo "Uso: $0 {memory|start|clean}"
        exit 1
        ;;
esac

exit 0

