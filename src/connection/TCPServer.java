package connection;

import model.Answer;
import model.Student;
import tool.KMP_String_Matching;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Random;

public class TCPServer {
    HashMap<String, Student> studentMap;
    HashMap<String, Answer> answerMap;
    final String xau1 = "Em Uyen dang yeu";
    final String xau2 = "Em Uyen Em Uyen Em Uyen Em Uyen dang lam sao vay em Uyen";
    final String xau3 = "Uyen";
    final int len_xau1 = xau1.length();
    final String xau1_reverse = new StringBuilder(xau1).reverse().toString();
    final int c32 = new KMP_String_Matching().KMPSearch(xau3, xau2);

    public void objectServer() throws IOException, ClassNotFoundException {
        // Tạo socket ở server, chờ ở port 11005
        ServerSocket serverSocket = new ServerSocket(11005);

        while (true) {
            // Lắng nghe yêu cầu từ client
            Socket connectionSocket = serverSocket.accept();
            Student student;
            Answer answer;
            // Input stream
            ObjectInputStream inFromClient = new ObjectInputStream(connectionSocket.getInputStream());

            // Output stream
            ObjectOutputStream outToClient = new ObjectOutputStream(connectionSocket.getOutputStream());

            student = (Student) inFromClient.readObject();
            if (studentMap.containsKey(student.getMaSV())) {
                answer = answerMap.get(student.getMaSV());
            } else {
                System.out.println("Register student: " + student.getHovaten());
                studentMap.put(student.getMaSV(), student);
                answer = new Answer(student, true);
                answerMap.put(student.getMaSV(), answer);
            }
            outToClient.writeObject(answer);
        }
    }

    public void stringServer() throws IOException, ClassNotFoundException {
        // Tạo socket ở server, chờ ở port 11001
        ServerSocket serverSocket = new ServerSocket(11001);
        Random rand = new Random();
        while (true) {
            // Lắng nghe yêu cầu từ client
            Socket connectionSocket = serverSocket.accept();
            Student student;
            Answer answer;
            Boolean isRight;
            int code = rand.nextInt(2);
            // Input stream
            ObjectInputStream inFromClient = new ObjectInputStream(connectionSocket.getInputStream());

            // Output stream
            ObjectOutputStream outToClient = new ObjectOutputStream(connectionSocket.getOutputStream());

            String maSV = (String) inFromClient.readObject();
            String hovaten = (String) inFromClient.readObject();
            int group = inFromClient.readInt();

            outToClient.writeInt(code);
            if (!studentMap.containsKey(maSV)) {
                student = new Student(maSV, hovaten, connectionSocket.getRemoteSocketAddress().toString(), group);
                answer = new Answer(student, false);
                answerMap.put(student.getMaSV(), answer);
            } else if (code == 0) {
                outToClient.writeObject(xau1);
                int len_xau1_fc = inFromClient.readInt();

            }


            outToClient.writeObject(answer);
        }
    }

    public static void main(String[] args) {
        TCPServer tcpServer = new TCPServer();
        try {
            tcpServer.objectServer();
        } catch (ClassNotFoundException e) {
            System.err.println("");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
