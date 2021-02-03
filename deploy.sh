declare -a SERVICES=('api-gateway' 'config-server' 'dashboard-service' 'eureka-server' 'uaa-server' 'user-service' 'wall-service')

for SERVICE in "${SERVICES[@]}"
do
    cd "${SERVICE}" || exit
    ./mvnw clean install -DskipTests
    ./mvnw spring-boot:build-image -Dspring-boot.build-image.imageName="${SERVICE}"
    sleep 1s;
    cd ..
done

docker-compose up -d
