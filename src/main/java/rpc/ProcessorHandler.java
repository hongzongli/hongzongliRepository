package rpc;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

public class ProcessorHandler implements Runnable{

    private Socket socket;
    private Object service;//发布的服务

    public ProcessorHandler(Socket socket, Object service) {
        this.socket = socket;
        this.service = service;
    }

    @Override
    public void run() {
        //处理socket请求
        ObjectInputStream objectInputStream =null;
        ObjectOutputStream objectOutputStream = null;
        try {
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            RpcRequest rpcRequest = (RpcRequest)objectInputStream.readObject();
            Object result = invoke(rpcRequest);
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(result);
            objectOutputStream.flush();
            objectOutputStream.close();
            objectInputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }finally {

            if(socket!=null){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Object invoke(RpcRequest rpcRequest  ) throws Exception {

        String className = rpcRequest.getClassName();
        String methodName = rpcRequest.getMethodName();
        Object[] args = rpcRequest.getParameters();

        System.out.println("接收到新的请求参数为：className="+className +"  methodName="+methodName+"parameter="+args);

        Class<?>[] types = new Class[args.length];

        for (int i = 0; i <args.length ; i++) {
            types[i] = args[i].getClass();
        }
        Method method = service.getClass().getMethod(methodName, types);
        return method.invoke(service,args);
    }

}
