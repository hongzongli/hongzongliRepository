package rpc;

public class HelloServiceImpl implements HelloService {

    @Override
    public String sayHello(String name) {
        System.out.println("hello  "+name);
        return "hello  "+name;
    }
}
