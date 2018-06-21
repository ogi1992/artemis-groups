**TEST APPS FOR ARTEMIS GROUPS**

To run apps:
 * first build both spring boot apps
 * run docker-compose up -d
 
To access logs:
 * docker logs -f producer
 * docker logs -f consumer
 * docker logs -f artemis
 
**http://localhost:8081/producer/sendMessages** endpoint sends messages divided into groups to both consumers.