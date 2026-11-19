package TaskB1;
import java.io.*;
import java.nio.file.*;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
/** Five-column starter format; simple comma delimiters only. */
public final class PassStore {
 private final Path file;
 public PassStore(Path file){this.file=file;}
 public void add(String id,String reg,String name,int route,double amount)throws IOException{
  try(BufferedWriter w=Files.newBufferedWriter(file,StandardOpenOption.CREATE,StandardOpenOption.APPEND)){
   w.write(String.join(",",id,reg,name,String.valueOf(route),String.valueOf(amount)));w.newLine();
  }
 }
 /** Completed TODO 1. Malformed rows are skipped so later records remain searchable. */
 public String[] find(String passId)throws IOException{
  if(!Files.exists(file))return null;
  try(BufferedReader r=Files.newBufferedReader(file)){
   String line;while((line=r.readLine())!=null){
    String[] f=line.split(",",-1);if(f.length!=5)continue;
    try{Integer.parseInt(f[3]);Double.parseDouble(f[4]);}catch(NumberFormatException e){continue;}
    if(f[0].equals(passId))return f;
   }
  }return null;
 }
 public static void main(String[] a)throws IOException{
  Path f=Files.createTempFile("passes",".csv");
  try{PassStore s=new PassStore(f);s.add("P1001","192311001","Arun",12,4500);
   Files.writeString(f,"P1002,DAMAGED LINE\n",StandardOpenOption.APPEND);
   s.add("P1003","192311003","Divya",7,3800);
   System.out.println(Arrays.toString(s.find("P1003")));System.out.println(Arrays.toString(s.find("P1002")));
  }finally{Files.deleteIfExists(f);}
 }
}
