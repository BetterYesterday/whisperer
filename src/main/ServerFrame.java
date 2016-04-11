package main;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ServerFrame implements Runnable {//서버 메인 프레임
	
	ServerSocket serverSocket;
	Thread[] ThreadArr;
	File f;
	static FileWriter fw;
	String logfiledir = "";//로그파일 디렉터리
	static boolean iflogfilewrite = false;
	
	public static void main(String[] arg) throws Throwable{
		
		
		ServerFrame server = new ServerFrame(12);
		 server.start();
		
		
	}
	public ServerFrame(int num) {
		
	    try {
	        // 서버 소켓을 생성하여 7777번 포트와 바인딩.
	        serverSocket = new ServerSocket(20007);
	        
	        logWrite(getTime() + " 서버가 준비되었습니다.");

	        ThreadArr = new Thread[num];
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	public void start() throws IOException {
		f  = new File(logfiledir);
		fw = new FileWriter(f);
	    for (int i = 0; i < ThreadArr.length; i++) {
	        ThreadArr[i] = new Thread(this);
	        ThreadArr[i].start();
	    }
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
	        try {
	            logWrite(getTime() + " is waiting for connection..");

	            Socket socket = serverSocket.accept();
	            logWrite(getTime() + " " + socket.getInetAddress()
	                    + " catched connection request.");

	            OutputStream out = socket.getOutputStream();
	            DataOutputStream dos = new DataOutputStream(out);

	            dos.writeUTF("[Notice] Test Message1 from Server");
	            logWrite(getTime() + " sent data.");

	            dos.close();
	            socket.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	}
	static void logWrite(String t){
		if(iflogfilewrite){
			System.out.println(t);
			try {
	            fw.write(t);
	            fw.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
			
		}
	}
	static String getTime() {
	    String name = Thread.currentThread().getName();
	    SimpleDateFormat f = new SimpleDateFormat("[hh:mm:ss]");
	    return f.format(new Date()) + name;
	}

}
