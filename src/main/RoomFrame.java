// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RoomFrame.java

package main;


public class RoomFrame
{

    public RoomFrame()
    {
        sb = new StringBuffer();
    }

    public void RoomFrame(String name, String key, boolean crypted)
    {
        roomname = name;
        this.key = key;
        this.crypted = crypted;
        activated = true;
    }

    public void inputmessage(String s)
    {
    }

    public String roomname;
    private String key;
    public boolean crypted;
    public boolean activated;
    public StringBuffer sb;
}
