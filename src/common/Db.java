package common;
import java.sql.*;
public final class Db{private Db(){}public static Connection connect()throws SQLException{return DriverManager.getConnection(req("JDBC_URL"),req("JDBC_USER"),System.getenv().getOrDefault("JDBC_PASSWORD",""));}private static String req(String k){String v=System.getenv(k);if(v==null||v.isBlank())throw new IllegalStateException("Set "+k);return v;}}
