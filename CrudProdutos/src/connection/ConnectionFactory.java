
package connection;

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;

/**
 *
 * @author elcineide
 */
public class ConnectionFactory {
    
    //Atributos constantes
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/serra";
    private static final String USER = "root";
    private static final String PASS = "";
    
    
    //Construtor da classe
    public ConnectionFactory(){
        
    }
    
    //Método responsável por abrir a conexão com o banco de dados
    public static Connection getConnection(){
        try {
            Class.forName(DRIVER);
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (ClassNotFoundException | SQLException ex) {
            throw new RuntimeException("Erro na conexão "+ex);
        }   
    }
    
    //Métodos responsáveis por fechar a conexão com o banco
    public static void closeConnection(Connection con){
        
        if (con != null) {
            try {
                con.close();
            } catch (SQLException ex) {
                System.err.println("Erro ao tentar fechar conexão "+ex);
            }
        }
    }
    
    public static void closeConnection(Connection con, PreparedStatement st){
        
        if (st != null) {
            try {
                st.close();
            } catch (SQLException ex) {
                System.err.println("Erro ao tentar fechar conexão "+ex);
            }
            ConnectionFactory.closeConnection(con);
        }
    }
    
    public static void closeConnection(Connection con, PreparedStatement st, ResultSet rs){
        
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ex) {
                System.err.println("Erro ao tentar fechar conexão "+ex);
            }
            ConnectionFactory.closeConnection(con, st);
        }
    }
    
    
}
