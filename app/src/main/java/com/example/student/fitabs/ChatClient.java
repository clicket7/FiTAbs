package com.example.student.fitabs;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatClient implements Runnable {
    Socket socket;
    static int port;
    String host;
    PrintWriter out;
    BufferedReader in;

    public ChatClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public void run() {
        try {
            socket = new Socket(host, port);
            Log.e("Socket", "CONNECTED");
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            String msg;
            while (true) {
                try {
                    msg = in.readLine();
                    Log.e("I", msg);
                }
                catch (IOException e) {
                    e.printStackTrace();
                } 
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendServer(String msg) {
        if (socket != null) {
            out.print(msg);
            Log.e("O", msg);
        }
    }

}
