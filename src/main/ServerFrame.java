// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ServerFrame.java

package main;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ServerFrame
    implements Runnable
{

    static ServerSocket serverSocket;
    Thread ThreadArr[];
    static String logfiledir = "./log";
    static String winlogfiledir = "g:/test";
    static boolean iflogfilewrite = true;
    int Threadcount;
    public int notConnected;
    private StringBuffer sb;
    byte TEST;
    static File f;
    static StringBuffer log = new StringBuffer();

    public static void main(String arg[])
        throws Throwable
    {
        System.out.println(System.getProperty("os.name"));
        if(System.getProperty("os.name").contains("Windows"))
        {
            System.out.println("Windows!");
            f = new File((new StringBuilder(String.valueOf(winlogfiledir))).append(getTime()).append(".log").toString());
        } else
        {
            System.out.println("Linux or Other OS!");
            f = new File((new StringBuilder(String.valueOf(logfiledir))).append(getTime()).append(".log").toString());
        }
        ServerFrame server = new ServerFrame(12);
        server.star();
    }

    public ServerFrame(int num)
    {
        notConnected = 0;
        TEST = 0;
        try
        {
            serverSocket = new ServerSocket(20007);
            logWrite(" server Ready. ");
            ThreadArr = new Thread[num];
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public void star()
        throws IOException
    {
        for(int i = 0; i < ThreadArr.length; i++)
        {
            ThreadArr[i] = new Thread(this);
            ThreadArr[i].start();
            logWrite((new StringBuilder("Thread ")).append(i).append(" started. Id is \"").append(ThreadArr[i].getId()).append("\"\n").toString());
        }

    }

    public void run()
    {
        do
        {
            notConnected++;
            try
            {
                logWrite(" waiting for connection..");
                Socket socket = serverSocket.accept();
                notConnected--;
                (new Thread(this)).start();
                logWrite((new StringBuilder()).append(socket.getInetAddress()).append(" catched connection request.").toString());
                OutputStream out = socket.getOutputStream();
                BufferedReader bin = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                DataOutputStream dos = new DataOutputStream(out);
                ///to Test
                for(int i = 0; i < 1000; i++)
                    writeout(out, new String((new StringBuilder("테스트 메시지")).append(i).toString().getBytes("utf-8"), "utf-8"), TEST);

                sb = new StringBuffer();
                while(socket.isConnected()) 
                {
                    writeout(out, "message", TEST);
                    sb.append(bin.readLine());
                    for(; sb == null && sb.toString().length() != 0; sb.delete(0, sb.length()))
                        System.out.println(sb.toString());

                }
                ///to Test
                logWrite("sent data.");
                bin.close();
                out.close();
                socket.close();
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
            if(notConnected > 12)
            {
                logWrite("Thread returned");
                break;
            }
            logWrite("Thread loop");
        } while(true);
    }

    static void logWrite(String t)
    {
        String name = Thread.currentThread().getName();
        System.out.println((new StringBuilder(String.valueOf(getTime()))).append(name).append(" : ").append(t).toString());
        if(iflogfilewrite)
        {
            try
            {
                FileWriter fw = new FileWriter(f);
                log.append((new StringBuilder(String.valueOf(getTime()))).append(name).append(" : ").append(t).append("\r\n").toString());
                fw.write(log.toString());
                fw.close();
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
            if(log.length() > 65535)
            {
                log.delete(0, log.length());
                if(System.getProperty("os.name").contains("Windows"))
                {
                    System.out.println("Windows!");
                    f = new File((new StringBuilder(String.valueOf(winlogfiledir))).append(getTime()).append(".log").toString());
                } else
                {
                    System.out.println("Linux or Other OS!");
                    f = new File((new StringBuilder(String.valueOf(logfiledir))).append(getTime()).append(".log").toString());
                }
            }
        }
    }

    static void writeout(OutputStream out, String message, byte Start)
        throws IOException
    {
        byte end = -1;
        out.write(Start);
        out.write(message.getBytes());
        out.write(end);
    }

    static String getTime()
    {
        SimpleDateFormat f = new SimpleDateFormat("yyyy.MM.dd G 'at'HH.mm.ss z");
        return f.format(new Date());
    }

}
