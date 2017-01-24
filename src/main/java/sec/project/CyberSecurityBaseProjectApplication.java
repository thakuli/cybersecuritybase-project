package sec.project;

import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import org.h2.tools.RunScript;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CyberSecurityBaseProjectApplication {
    private static void loadSqlFile(Connection con, String file) {
        try {
            System.out.println("Executing sql file {}".format(file));
            RunScript.execute(con, 
                    new InputStreamReader(CyberSecurityBaseProjectApplication.class.getResourceAsStream(file)));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }    

    public static void main(String[] args) throws Throwable {
        String databaseAddress = "jdbc:h2:file:./database";
        Connection connection = DriverManager.getConnection(databaseAddress, "sa", "");

        // If database has not yet been created, insert content
        System.out.println("Database initialization");
        loadSqlFile(connection, "/sql/cybersec-db-schema.sql");
        loadSqlFile(connection, "/sql/cybersec-db-default-data.sql");
        System.out.println("Database initialized");           
        
        SpringApplication.run(CyberSecurityBaseProjectApplication.class);
    }
}
