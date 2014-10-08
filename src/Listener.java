import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

/**
 * Created by k_sel_support_team on 05.10.2014.
 */
public class Listener {
    public static void main(String[] args) throws Exception {
        final Integrator integrator = new Integrator();

        HttpServer server = HttpServer.create(new InetSocketAddress(7373), 0);
        server.createContext("/", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {


                if(!"GET".equals(exchange.getRequestMethod())) return;
                if(!integrator.isLive()){
                    formatAnswer(exchange, integrator.getError());
                    return;
                }
                Map<String, String> params = getRequestParameters(exchange);
                String operation = params.get("cmd");
                if (operation.equals("clear")){
                    formatAnswer(exchange, integrator.proceedClearMethod());
                }else if (operation.equals("set") ){
                    String way = params.get("way");
                    boolean mult = params.containsKey("mult");
                    Integer minDelay = Integer.valueOf(params.get("mind"));
                    Integer maxDelay = Integer.valueOf(params.get("maxd"));
                    formatAnswer(exchange, integrator.proceedSetMethod(way, mult, minDelay, maxDelay));


                }else{
                    formatAnswer(exchange, "undefined or unknown operation");
                }

            }

            private Map<String, String> getRequestParameters(HttpExchange exchange) {
                Map<String, String> res = new HashMap<String, String>();
                try {
                    String params = exchange.getRequestURI().toString().substring(2);
                    String[] split1 = params.split("&");
                    for (String s : split1) {
                        String[] split2 = s.split("=");
                        res.put(split2[0], split2[1]);
                    }
                }catch(Exception e){

                }
                return res;
            }

            private void formatAnswer(HttpExchange t, String error) throws IOException{
               int retState = "".equals(error)?200:451;
               if(retState==200) error = "OK";
                t.sendResponseHeaders(retState, error.length());
                OutputStream os = t.getResponseBody();
                os.write(error.getBytes());
                os.close();
            }
        });
        server.setExecutor(null); // creates a default executor
        server.start();
    }


}
