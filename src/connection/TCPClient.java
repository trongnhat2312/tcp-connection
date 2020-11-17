package connection;

import model.Answer;
import model.Student;
import tool.KMP_String_Matching;

import java.io.*;
import java.net.Socket;

public class TCPClient {
    public KMP_String_Matching kmp = new KMP_String_Matching();

    public Answer getFinalResult(Socket clientSocket, Student student) throws IOException, ClassNotFoundException {
        Answer answer;
        ObjectOutputStream outToServer = new ObjectOutputStream(clientSocket.getOutputStream());
        ObjectInputStream inFromServer = new ObjectInputStream(clientSocket.getInputStream());
        outToServer.writeObject(student);
        answer = (Answer) inFromServer.readObject();
        System.out.println(answer);
        return answer;
    }

    public Answer answerAQuestion(Socket clientSocket, Student student) throws IOException, ClassNotFoundException {
        Answer answer;
        int code;
        String xau1, xau2, xau3;
        ObjectOutputStream objectOutToServer = new ObjectOutputStream(clientSocket.getOutputStream());
//        DataOutputStream dataOutToServer = new DataOutputStream(clientSocket.getOutputStream());
        ObjectInputStream objectInFromServer = new ObjectInputStream(clientSocket.getInputStream());
//        DataInputStream dataInFromServer = new DataInputStream(clientSocket.getInputStream());

        // start test
        objectOutToServer.writeObject(student.getMaSV());
        objectOutToServer.writeObject(student.getHovaten());
        objectOutToServer.writeInt(student.getGroup());

        code = objectInFromServer.readInt();
        if (code == 0) {
            // nhan xau 1
            xau1 = (String) objectInFromServer.readObject();
            // gui so ky tu cua xau 1
            objectOutToServer.writeInt(xau1.length());
            // gui xau 1 dao nguoc
            objectOutToServer.writeObject(new StringBuilder(xau1).reverse().toString());
            // nhan xau 2
            xau2 = (String) objectInFromServer.readObject();
            // nhan xau 3
            xau3 = (String) objectInFromServer.readObject();
            // dem so lan xuat hien cua xau 3 trong xau 2
            int c32 = kmp.KMPSearch(xau3, xau2);
            // gui so do
            objectOutToServer.writeInt(c32);
        } else {
            // nhan xau 2
            xau2 = (String) objectInFromServer.readObject();
            // nhan xau 3
            xau3 = (String) objectInFromServer.readObject();
            // dem so lan xuat hien cua xau 3 trong xau 2
            int c32 = kmp.KMPSearch(xau3, xau2);
            // gui so do
            objectOutToServer.writeInt(c32);
            // nhan xau 1
            xau1 = (String) objectInFromServer.readObject();
            // gui so ky tu cua xau 1
            objectOutToServer.writeInt(xau1.length());
            // gui xau 1 dao nguoc
            objectOutToServer.writeObject(new StringBuilder(xau1).reverse().toString());
        }
        answer = (Answer) objectInFromServer.readObject();
        System.out.println(answer);
        return answer;
    }


}
