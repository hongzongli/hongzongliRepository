package rpc;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RpcServer {

    private static final ExecutorService executorService = Executors.newCachedThreadPool();

    //发布服务
    public void publisher(final Object service,int port){
        ServerSocket serverSocket = null;
        try {
            serverSocket= new ServerSocket(port);//启动一个监听服务
            while (true){
                Socket socket = serverSocket.accept();
                executorService.execute(new ProcessorHandler(socket,service));
            }


        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(serverSocket!= null){
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
