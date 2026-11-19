package TaskB1;
import java.io.*;
import java.nio.file.*;
public final class BufferedLineCounter {
 private static final int B=65536;
 public static long unbuffered(Path p)throws IOException{long n=0;try(FileInputStream in=new FileInputStream(p.toFile())){int x;while((x=in.read())!=-1)if(x=='\n')n++;}return n;}
 public static long buffered(Path p)throws IOException{long n=0;try(BufferedInputStream in=new BufferedInputStream(new FileInputStream(p.toFile()),B)){int x;while((x=in.read())!=-1)if(x=='\n')n++;}return n;}
 public static void main(String[] a)throws IOException{if(a.length!=1){System.err.println("Usage: BufferedLineCounter file");return;}Path p=Path.of(a[0]);long t=System.nanoTime(),x=unbuffered(p),m=System.nanoTime(),y=buffered(p),e=System.nanoTime();System.out.println("bytes="+Files.size(p)+" lines="+x+" bufferedLines="+y);System.out.println("unbufferedNs="+(m-t)+" bufferedNs="+(e-m));}
}
