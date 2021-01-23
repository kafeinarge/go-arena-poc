declare -a SERVICES=('api-gateway' 'config-server' 'eureka-server' 'uaa-server' 'user-service')

for SERVICE in "${SERVICES[@]}"
do
    cd "${SERVICE}" || exit
    ./mvnw clean install -DskipTests
    ./mvnw spring-boot:build-image -Dspring-boot.build-image.imageName="${SERVICE}" -DskipTests  # TODO: remove skipTests
    sleep 1s;
    cd ..
done

docker-compose up
