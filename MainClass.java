package gzip;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class MainClass {

	public static void main(String[] args) {
		File folder = new File("C:\\Users\\ofekr\\Downloads");
		File[] listOfFiles = folder.listFiles();
		LinkedList<Tuple<String,String>> compressed = new LinkedList<>();

		for (File file : listOfFiles) {
			System.out.println( file.getName());
		    if (file.isFile()) {
		    	String file_exe = file.getName();
		        String gzipFile = file_exe+".gz";
		        String newFile = "new"+file_exe;
		        String path = "C:\\Users\\ofekr\\Downloads\\";
		        
		        compressGzipFile(path+file_exe, path+gzipFile);
		        
		        decompressGzipFile(path+gzipFile, path+newFile);
		        compressed.add(new Tuple<String,String>(path+file_exe,path+newFile));
		    }
		}
		
		for (Tuple<String,String> pair : compressed) {
			File s =  new File(pair.x);
			File d =  new File(pair.y);
			boolean isntLoseless = getFileSizeKiloBytes(s)==getFileSizeKiloBytes(d);
			
			System.out.println(s.getName()+" "+ getFileSizeKiloBytes(s) + " "+d.getName()+" "+getFileSizeKiloBytes(d)+"Error: "+isntLoseless+"\n");
		}
               
    }
	
	private static String getFileSizeMegaBytes(File file) {
		return (double) file.length() / (1024 * 1024) + " mb";
	}
	
	private static String getFileSizeKiloBytes(File file) {
		return (double) file.length() / 1024 + "  kb";
	}

	private static String getFileSizeBytes(File file) {
		return file.length() + " bytes";
	}
	
    private static void decompressGzipFile(String gzipFile, String newFile) {
        try {
            FileInputStream fis = new FileInputStream(gzipFile);
            GZIPInputStream gis = new GZIPInputStream(fis);
            FileOutputStream fos = new FileOutputStream(newFile);
            byte[] buffer = new byte[1024];
            int len;
            while((len = gis.read(buffer)) != -1){
                fos.write(buffer, 0, len);
            }
            //close resources
            fos.close();
            gis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    private static void compressGzipFile(String file, String gzipFile) {
        try {
            FileInputStream fis = new FileInputStream(file);
            FileOutputStream fos = new FileOutputStream(gzipFile);
            GZIPOutputStream gzipOS = new GZIPOutputStream(fos);
            byte[] buffer = new byte[1024];
            int len;
            while((len=fis.read(buffer)) != -1){
                gzipOS.write(buffer, 0, len);
            }
            //close resources
            gzipOS.close();
            fos.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

}

class Tuple<X, Y> { 
	  public final X x; 
	  public final Y y; 
	  public Tuple(X x, Y y) { 
	    this.x = x; 
	    this.y = y; 
	  } 
	} 


