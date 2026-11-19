package TaskA4;
import java.io.Serializable;import java.util.*;
public final class Hostel implements Serializable{private static final long serialVersionUID=1L;private final List<Room> rooms=new ArrayList<>();public void add(Room r){rooms.add(r);}}
