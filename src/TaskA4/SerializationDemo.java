package TaskA4;

import java.io.*;
import java.nio.file.*;

public final class SerializationDemo {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Path path=Path.of(args.length==0?"hostel_snapshot.bin":args[0]);
        Hostel hostel=new Hostel(); Room room=new Room("A-101");
        room.add(new Resident("Sample Resident","TEST-001","0000000000")); hostel.add(room);
        try(ObjectOutputStream out=new ObjectOutputStream(Files.newOutputStream(path))){out.writeObject(hostel);}
        try(ObjectInputStream in=new ObjectInputStream(Files.newInputStream(path))){in.readObject();}
        System.out.println("sample hostel graph serialized and restored: "+path);
    }
}
