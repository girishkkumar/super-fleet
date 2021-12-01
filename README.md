# super-fleet
A SaaS software that enables transportation companies to manage their fleet of buses

Pre requisities for the application to run smoothly, we need to have the below softwares installed on the local system.
1. Nodejs
2. Java 8+
3. MySQL database
4. Maven

Reactjs application runs on port 3000 (http://localhost:3000)

Spring boot application runs on port 8085 (http://localhost:8085/fleet-api)

1. Clone the git hub repository on to your local system.

$git clone https://github.com/girishkkumar/super-fleet.git

2. Once cloned, we should see a folder named super-fleet

$ cd super-fleet/

3. Go to the application.properties file present in the mentioned location below.

cd /super-fleet/src/main/resources

4. Update the database connection properties (username & password of your Mysql local database)

5. Run the below command to start the supporting webservices spring boot application from the home directory super-fleet

/super-fleet$ mvn spring-boot:run

6. Once the web application is up and running, we need to start the reactjs application

7. Go to src/main/webapp/reactjs folder present inside super-fleet

8. run npm start, prior to this nodejs should be installed.

9. The above command should start the reactjs application.
