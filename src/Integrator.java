/**
 * Created by root on 05.10.2014.
 */
public class Integrator {
    private ScriptExecutor executor;
    private IntegratorUI ui;

    public Integrator() {
        try {
            executor = new ScriptExecutor();
        } catch (Exception ex) {

        }
        if(uiMode()) ui = new IntegratorUI(executor);
    }

    private boolean uiMode() {
        try{
            return !System.getProperty("ui").equalsIgnoreCase("false");
        }catch (Exception ex){
            return true;
        }
    }

    public String proceedClearMethod() {
        boolean l = executor.clear();

        return l ? "" : "script failed";
    }

    public String proceedSetMethod(String url, boolean mult, Integer minDel, Integer maxDel) {
        if (maxDel == null || minDel == null) {
            return "unsetted delay";
        }
        return executor.set(url, mult, minDel, maxDel) ? "" : "script failed";
    }

    public boolean isLive() {
        return executor != null;
    }

    public String getError() {
        return "script executor down";
    }

    public String getState(){
        if(executor==null) return "Executor creation failed";
        return executor.state();
    }
}
