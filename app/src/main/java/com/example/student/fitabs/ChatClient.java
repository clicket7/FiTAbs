package com.example.student.fitabs;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by KarishoK27 on 17.12.7.
 */

public class ChatClient {
    private String serverMessage;
    public static  String host = "192.168.8.117";
    public static final int port = 9999;
    private OnMessageReceived mMessageListener = null;
    private boolean mRun = false;

    PrintWriter out;
    BufferedReader in;

    public ChatClient(OnMessageReceived listener) {
        mMessageListener = listener;
    }

    public void sendMessage(String message) {
        if (out != null && !out.checkError()) {
            out.println(message);
            out.flush();
        }
    }

    public void stopClient() {
        mRun = false;
    }

    public void run() {
        mRun = true;
        try {
            InetAddress serverAddr = InetAddress.getByName(host);
            Log.e("serverAddr", serverAddr.toString());
            Log.e("TCP Client", "C: Connecting...");

                Socket socket = new Socket(host, port);
                Log.e("TCP Server IP", host);
                try {
                    out = new PrintWriter(socket.getOutputStream(), true);
                    Log.e("TCP Client", "C: Sent.");
                    Log.e("TCP Client", "C: Done.");

                    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                    while (mRun) {
                        serverMessage = in.readLine();
                        if (serverMessage != null && mMessageListener != null) {
                            mMessageListener.messageReceived(serverMessage);
                        }
                        serverMessage = null;
                    }

                    Log.e("RESPONSE FROM SERVER", "S: Received Message: '"  + serverMessage + "'");
                } catch (Exception e) {
                    Log.e("TCP", "S: Error", e);
                } finally {
                    socket.close();
                }

            } catch (Exception e) {
                Log.e("TCP", "C: Error", e);
            }

        }

    public interface OnMessageReceived {
        public void messageReceived(String message);
    }
}
