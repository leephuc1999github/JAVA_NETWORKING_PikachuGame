/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import Model.DataPackage;
import Model.User;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.Socket;


class ListenServer extends Thread implements Serializable{

    Socket socket;
    OutputStream outputStream;
    ObjectOutputStream objectOutputStream;
    InputStream inputStream;
    ObjectInputStream objectInputStream;
    public User user;
    public inReceiveMessage receive;

    ListenServer(Socket socket) {
        try {
            this.socket = socket;
            outputStream = socket.getOutputStream();
            objectOutputStream = new ObjectOutputStream(outputStream);
            inputStream = socket.getInputStream();
            objectInputStream = new ObjectInputStream(inputStream);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public void run() {
        do {
            try {
                Object o = objectInputStream.readObject();
                if (o != null && receive != null) {
                    receive.ReceiveMessage((DataPackage) o);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        } while (true);

    }
    public void SendMessage(DataPackage dataPackage) {
        try {
            objectOutputStream.reset();
            objectOutputStream.writeObject(dataPackage);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
