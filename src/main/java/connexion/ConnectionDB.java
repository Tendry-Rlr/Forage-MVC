package connexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

@Component
public class ConnectionDB {

    // URL de connexion MySQL : jdbc:mysql://<hôte>:<port>/<nom_base>
    @Value("jdbc:mysql://localhost:3306/forage?useSSL=false")
    private String URL;

    @Value("root")
    private String USER;

    @Value("")
    private String PASSWORD;

    public Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Driver MySQL non trouvé !");
            e.printStackTrace();
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String uRL) {
        URL = uRL;
    }

    public String getUSER() {
        return USER;
    }

    public void setUSER(String uSER) {
        USER = uSER;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }

    public void setPASSWORD(String pASSWORD) {
        PASSWORD = pASSWORD;
    }
}