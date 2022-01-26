# SocialMedia

## Backend Setup:
### Setup Environment Variables
1. On Windows go to Search and type in "env"  
2. Click edit the system environment variable  
3. Make sure under System variables you have the following:  
   AWS_DB_ENDPOINT: this is your RDS url endpoint  
   AWS_USERNAME: your AWS db username  
   AWS_PASSWORD: your AWS db password   
   **YOUR PASSWORDS SHOULD NOT BE IN ANY WAY SAVED TO THE PROJECT DIRECTORY**  
   **DO NOT CHANGE THE GETDATASOURCE BEAN**  
4. Open the SQL script in the databases folder inside DBeaver  
5. Make sure you have a database with the name socialmedia and that the script is pointing to it
6. Execute the script and skip the errors on the DROP statements  
7. At this point you should be able to run the Spring Boot Application


### Deploy Spring Boot Application
1. Make sure you have pulled the latest build from dev  
2. Open the backend project in STS  
3. Open the ServletInitializer.java file and click Run -> as Java Application  
4. Spring Boot will deploy the application on port 8080 by default  
5. Open Postman and try to access one of the mappings http://localhost:8080/{mapping}  
6. You should get a response that isn't an error as long as it's mapped to a controller  
Note: Spring Boot will hot restart for you whenever you save changes

##
