package service;

public class RPCServiceImpl implements  RPCService {

    public RPCServiceImpl(){}

    @Override
    public String getInformation(String s) {
        return ("收到发来的信息："+s);
    }
}
