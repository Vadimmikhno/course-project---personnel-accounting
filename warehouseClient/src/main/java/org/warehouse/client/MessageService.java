package org.warehouse.client;

import java.io.*;
import java.net.Socket;

public class MessageService {

    private OutputStream out;
    private InputStream input;

    private static final String escFromServer = "esc";

    public MessageService(Socket clientSocket) {
        try {
            this.out = clientSocket.getOutputStream();
            this.input = clientSocket.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static void esc(OutputStream out) {
        try {
            ObjectOutput outObj = new ObjectOutputStream(out);
            outObj.writeObject(escFromServer);
            outObj.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static synchronized <T> void send(T o, OutputStream out) {
        try {
            ObjectOutput outObj = new ObjectOutputStream(out);
            outObj.writeObject(o);
            outObj.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public static <T> T accept(InputStream in) {

        try {
            ObjectInputStream input = new ObjectInputStream(in);
            T o = (T) input.readObject();
            return o;
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }

    public OutputStream getOut() {
        return out;
    }

    public void setOut(OutputStream out) {
        this.out = out;
    }

    public InputStream getInput() {
        return input;
    }

    public void setInput(InputStream input) {
        this.input = input;
    }
}
