import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;

/**
 * Created by root on 06.10.2014.
 */
public class IntegratorUI {
     private Frame frame;
     private TextArea activity;


     public IntegratorUI(final ScriptExecutor exec){
         if(exec == null){
             Frame f = new Frame("Driver error");
             f.addWindowListener( new WindowAdapter() {
                 @Override
                 public void windowClosing(WindowEvent we) {
                     System.exit(0);
                 }
             } );
             Label label = new Label();
             label.setText("KDE SELENIUM INTEGRATION NOT FOUND");
             f.setSize(300, 300);
             f.setAlwaysOnTop(true);
             f.add(label);
             f.show();
         }else{
             frame = new Frame("Driver for SELENIUM IDE");
             frame.addWindowListener( new WindowAdapter() {
                 @Override
                 public void windowClosing(WindowEvent we) {
                     System.exit(0);
                 }
             } );
             activity = new TextArea();
             activity.setEditable(false);
             frame.setSize(300, 300);
             frame.setAlwaysOnTop(true);
             frame.add(activity);
             frame.show();
             Runnable refresher = new Runnable() {
                 @Override
                 public void run() {
                    while(true){
                        activity.setText(exec.state());
                        try{
                            Thread.sleep(3000L);
                        }catch(Exception ex){

                        }
                    }
                 }
             };
             new Thread(refresher).start();


         }


     }
}
