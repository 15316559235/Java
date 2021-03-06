package service;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class RemoteClient{
    public void invoke() throws Exception{
        Socket socket = new Socket("localhost",8000);
        OutputStream out = socket.getOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(out);
        InputStream in = socket.getInputStream();
        ObjectInputStream ois = new ObjectInputStream(in);

        RemoteCall call = new RemoteCall("service.RPCServiceImpl", "getInformation", new Class[]{String.class}, new Object[]{"测试 RPC！"});      // 向服务器发送Call 对象
        oos.writeObject(call);       //接收包含了方法执行结果的Call 对象

        call = (RemoteCall) ois.readObject();
        System.out.println(call.getResult());
        ois.close();        oos.close();        socket.close();
    }

    public static void main (String args[] )throws Exception {
        new RemoteClient().invoke() ;
    }
}
