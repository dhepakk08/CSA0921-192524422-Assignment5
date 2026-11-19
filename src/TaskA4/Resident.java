package TaskA4;
import java.io.Serializable;import java.sql.Connection;
public final class Resident implements Serializable{private static final long serialVersionUID=1L;private final String name,regNo,phoneNumber;private transient String password;private transient Connection connection;public Resident(String n,String id,String phone){name=n;regNo=id;phoneNumber=phone;}}
