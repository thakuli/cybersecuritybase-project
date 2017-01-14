package sec.project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import org.h2.tools.RunScript;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CyberSecurityBaseProjectApplication {
    private static void printWorkDirFiles() {
        File dir = new File(".");
        File[] filesList = dir.listFiles();
        for (File file : filesList) {
            if (file.isFile()) {
                System.out.println(file.getName());
            }
        }
    }    

    public static void main(String[] args) throws Throwable {
        String databaseAddress = "jdbc:h2:file:./database";
        Connection connection = DriverManager.getConnection(databaseAddress, "sa", "");

        try {
            printWorkDirFiles();
            // If database has not yet been created, insert content
            //RunScript.execute(connection, new FileReader("sql/cybersec-db-schema.sql"));
            //RunScript.execute(connection, new FileReader("sql/cybersec-db-default-data.sql"));
            
            System.out.println("Database initialization");
            //RunScript.execute(connection, new InputStreamReader(CyberSecurityBaseProjectApplication.class.getResourceAsStream("/sql/cybersec-db-schema.sql")));
            RunScript.execute(connection, new InputStreamReader(CyberSecurityBaseProjectApplication.class.getResourceAsStream("/sql/cybersec-db-default-data.sql")));
        } catch (Throwable t) {
            System.out.println("oho");
            System.err.println(t.getMessage());
        }        
        
        SpringApplication.run(CyberSecurityBaseProjectApplication.class);
    }
}
