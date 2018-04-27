import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class FileIncepter {
    public FileIncepter(){

    }
    public void receiveFile(String savePath,String ip,int port){
        Socket socket=null;
        try {
            socket = new Socket(ip,port);
        } catch (UnknownHostException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        DataInputStream dis=null;
        try {
            dis = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        int bufferSize = 1024;
        byte[] buf = new byte[bufferSize];
        long len = 0;
        try{
            savePath += dis.readUTF();
            DataOutputStream fileOut = new DataOutputStream(
                    new BufferedOutputStream(new BufferedOutputStream(
                            new FileOutputStream(savePath))));
            System.out.println("文件原路径为："+dis.readUTF());
            System.out.println("开始接收文件!");
            int read = 0;
            while ((read=dis.read(buf))!=-1){
                fileOut.write(buf, 0, read);
            }
            System.out.println("接收完成，文件存为" + savePath);
            fileOut.close();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }
    public static void main(String[] args) {
        String savePath=null;
        Scanner in=new Scanner(System.in);
        savePath=in.next();
        new FileIncepter().receiveFile(savePath, "localhost", 8821);
    }
}  