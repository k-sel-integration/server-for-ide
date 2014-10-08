import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

/**
 * Created by root on 06.10.2014.
 */
public class ScriptExecutor {
    private String folder;
    private String extension;
    public ScriptExecutor(){
        folder=System.getenv("K_SEL_SUPPORT_FOLDER");
        extension=System.getenv("K_SEL_SUPPORT_EXTENSION");
        if(folder==null || extension == null) throw new RuntimeException("kde selenium integration not found!");

    }

    public boolean clear(){
      synchronized (ScriptExecutor.class) {
          try {
              Process proc = Runtime.getRuntime().exec(folder + "clearstate" + extension);
              proc.waitFor();
              return proc.exitValue() == 0;
          } catch (Exception e) {
              return false;
          }
      }
    }

    public boolean set(String way, boolean mult, int minD, int maxD){
        synchronized (ScriptExecutor.class) {
            try {
                Process proc = Runtime.getRuntime().exec(folder + "setstate" + extension + " " + way + " " +
                                (mult?1:0) + " " + minD + " " + maxD
                );
                proc.waitFor();
                return proc.exitValue() == 0;
            } catch (Exception e) {
                return false;
            }
        }
    }

    public String state(){
        synchronized (ScriptExecutor.class){
            try{
                Process proc = Runtime.getRuntime().exec(folder + "printstate" + extension);
                BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
                String ans="";
                String line;
                while ((line = in.readLine()) != null) {
                   ans+=line+"\n";
                }
                proc.waitFor();
                if(proc.exitValue()>0) throw new RuntimeException();
                return ans + new Date() + "\n";
            }catch(Exception ex){
                return "Script error";
            }
        }
    }

}
