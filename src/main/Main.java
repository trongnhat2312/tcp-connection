package main;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws IOException {
        String clientSentences;
        String serverSentences;

        System.out.println("Input from client: ");
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

        clientSentences = inFromUser.readLine();
        System.out.println(clientSentences);
        // Tạo socket cho client kết nối đến server
        Socket clientSocket = new Socket("0.0.0.0", 2312);
        try {
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            outToServer.writeBytes("\n");
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            serverSentences = inFromServer.readLine();
            outToServer.writeBytes(clientSentences + "\n");


            System.out.println("From server: " + serverSentences);

        } finally {
            clientSocket.close();
        }

    }
}
