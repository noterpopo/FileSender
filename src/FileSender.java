import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class FileSender {
    private ServerSocket ss=null;
    public void sendFile(String filePath,int port){
        DataOutputStream dos=null;
        DataInputStream dis=null;
        Socket socket=null;
        try {
            File file=new File(filePath);
            ss=new ServerSocket(port);
            socket=ss.accept();
            dos=new DataOutputStream(socket.getOutputStream());
            dis=new DataInputStream(new BufferedInputStream(new FileInputStream(filePath)));
            byte[]bufArray=new byte[1024];
            dos.writeUTF(file.getName());
            dos.flush();
            dos.writeUTF(filePath);
            dos.flush();
            int read=0;
            while((read=dis.read(bufArray))!=-1){
                dos.write(bufArray, 0, read);
            }
            dos.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (dos != null)
                    dos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (dis != null)
                    dis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (socket != null)
                    socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (ss != null)
                    ss.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String []args){
        String filePath=null;
        Scanner in=new Scanner(System.in);
        filePath=in.next();
        new FileSender().sendFile(filePath, 8821);
    }
}
