package service;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Service {                           // 存放远程对象的缓存
    private Map remoteObjects = new HashMap();  //注册服务：把一个远程对象放到缓存中

    public void register(String className, Object remoteObject){
        remoteObjects.put(className, remoteObject);
    }

    public void service() throws Exception {    // 创建基于流的Socket,并在8000 端口监听
        ServerSocket serverSocket = new ServerSocket(8000);
        System.out.println(" 服务器启动......");
        Socket socket = serverSocket.accept();
        InputStream in = socket.getInputStream();
        ObjectInputStream ois = new ObjectInputStream(in);
        OutputStream out = socket.getOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(out);     //接收客户发送的Call 对象

        RemoteCall remotecallobj = (RemoteCall)ois.readObject();
        System.out.println(remotecallobj);     // 调用相关对象的方法

        remotecallobj = invoke(remotecallobj);         // 向客户发送包含了执行结果的remotecallobj 对象

        oos.writeObject(remotecallobj);
        ois.close();
        oos.close();
        socket.close();
    }


    public RemoteCall invoke( RemoteCall call) {
        Object result = null;
        try {
            String className = call.getClassName();
            String methodName = call.getMethodName();
            Object[] params = call.getParams();

            //System.out.println(className);

            Class classType = Class.forName(className);
            Class[] paramTypes = call.getParamTypes();

            Method method = classType.getMethod(methodName, paramTypes);     // 从缓存中取出相关的远程对象Object

            Object remoteObject = remoteObjects.get(className);

            //System.out.println(remoteObject);

            if (remoteObject == null) {
                throw new Exception(className + " 的远程对象不存在");
            }
            else {
                result = method.invoke(remoteObject, params);
            }
        } catch (Exception e) {
            result = e;
        }

        call.setResult(result);

        return call;
    }

    public static void main( String args[ ]) throws Exception {
        Service server = new Service() ;        //注册服务：把事先创建的RemoteServceImpl 对象加入到服务器的缓存中

        server.register("service.RPCServiceImpl",new service.RPCServiceImpl()) ;

        server.service( );
    }
}
