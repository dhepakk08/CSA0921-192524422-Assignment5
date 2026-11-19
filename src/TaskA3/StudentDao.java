package TaskA3;
import javax.sql.DataSource;import java.sql.*;import java.util.*;
public final class StudentDao implements Dao<Student,String>{
 private final DataSource ds;public StudentDao(DataSource d){ds=Objects.requireNonNull(d);}
 public void insert(Student s)throws SQLException{try(Connection c=ds.getConnection();PreparedStatement p=c.prepareStatement("INSERT INTO student(reg_no,name,course_code,year) VALUES(?,?,?,?)")){p.setString(1,s.regNo());p.setString(2,s.name());p.setString(3,s.courseCode());p.setInt(4,s.year());p.executeUpdate();}}
 public Optional<Student> findById(String id)throws SQLException{try(Connection c=ds.getConnection();PreparedStatement p=c.prepareStatement("SELECT reg_no,name,course_code,year FROM student WHERE reg_no=?")){p.setString(1,id);try(ResultSet r=p.executeQuery()){return r.next()?Optional.of(map(r)):Optional.empty();}}}
 public List<Student> findAll()throws SQLException{List<Student>a=new ArrayList<>();try(Connection c=ds.getConnection();PreparedStatement p=c.prepareStatement("SELECT reg_no,name,course_code,year FROM student");ResultSet r=p.executeQuery()){while(r.next())a.add(map(r));}return a;}
 public boolean update(Student s)throws SQLException{try(Connection c=ds.getConnection();PreparedStatement p=c.prepareStatement("UPDATE student SET name=?,course_code=?,year=? WHERE reg_no=?")){p.setString(1,s.name());p.setString(2,s.courseCode());p.setInt(3,s.year());p.setString(4,s.regNo());return p.executeUpdate()==1;}}
 public boolean delete(String id)throws SQLException{try(Connection c=ds.getConnection();PreparedStatement p=c.prepareStatement("DELETE FROM student WHERE reg_no=?")){p.setString(1,id);return p.executeUpdate()==1;}}
 private static Student map(ResultSet r)throws SQLException{return new Student(r.getString(1),r.getString(2),r.getString(3),r.getInt(4));}
}
