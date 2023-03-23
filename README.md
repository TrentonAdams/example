
# Build

## Gradle Run

```bash
./gradlew bootRun 
```

## Docker Run

```bash
./gradlew build # just to ensure it's building outside the container. 
docker build -t takehome ./ 
docker run --rm -it -p 8080:8080 takehome
curl -v 'http://localhost:8080/continent_search?country_code=CA&country_code=BR' | jq
curl -u 'user:password' -v 'http://localhost:8080/continent_search?country_code=CA&country_code=BR' | jq
```
