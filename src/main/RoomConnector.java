// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RoomConnector.java

package main;


public class RoomConnector extends Thread
{

    public RoomConnector()
    {
    }

    public void Roomconnector(int i, String nam)
    {
        Roomnum = i;
        Roomname = nam;
    }

    int Roomnum;
    String Roomname;
}
