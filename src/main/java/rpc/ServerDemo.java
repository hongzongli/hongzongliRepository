package rpc;

public class ServerDemo {

    public static void main(String[] args) {

        HelloService helloService = new HelloServiceImpl();
        RpcServer  rpcServer = new RpcServer();
        rpcServer.publisher(helloService,8888);
    }
}
