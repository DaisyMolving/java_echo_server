package echo;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {

    public ServerSocket serverSocket;
    public Socket clientConnection = new Socket();
    public BufferedReader input;
    public PrintWriter output;

    public void start(String[] args) {
        if (args.length == 1) {
            int portNumber = Integer.parseInt(args[0]);
            bindServerSocketToPort(portNumber);
            acceptConnectionFromClient();
            echoToClient();
        } else {
            printErrorMessage("Server does not recognise that port.");
        }
    }

    public void bindServerSocketToPort(int portNumber) {
        try {
            serverSocket = new ServerSocket(portNumber);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void acceptConnectionFromClient() {
        try {
            clientConnection = serverSocket.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void echoToClient() {
        accessClientOutput();
        accessClientInput();
        String clientInput;
        try {
            while ((clientInput = input.readLine()) != null) {
                output.println(clientInput);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeServerSocket() {
        if (serverSocket != null) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void accessClientInput() {
        try {
            input = new BufferedReader(new InputStreamReader(clientConnection.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void accessClientOutput() {
        try {
            output = new PrintWriter(clientConnection.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void printErrorMessage(String errorMessage) {
        System.err.println(errorMessage);
    }

}
