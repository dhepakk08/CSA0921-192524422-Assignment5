package TaskA4;
import java.io.Serializable;import java.util.*;
public final class Room implements Serializable{private static final long serialVersionUID=1L;private final String roomNo;private final List<Resident> residents=new ArrayList<>();public Room(String n){roomNo=n;}public void add(Resident r){residents.add(r);}}
