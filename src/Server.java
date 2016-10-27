/**
 * Created by semeykin on 26.10.2016.
 */

import java.net.*;
import java.io.*;
import java.util.ArrayList;

public class Server {
    public static void main(String[] ar) {
        int port = 6666; // случайный порт (может быть любое число от 1025 до 65535)
        ArrayList<Integer> newArray = new ArrayList<>();
        try {
            ServerSocket ss = new ServerSocket(port); // создаем сокет сервера и привязываем его к вышеуказанному порту
            System.out.println("Waiting for a client...");

            Socket socket = ss.accept(); // заставляем сервер ждать подключений и выводим сообщение когда кто-то связался с сервером
            System.out.println("Got a client :) ... Finally, someone saw me through all the cover!");
            System.out.println();

            // Берем входной и выходной потоки сокета, теперь можем получать и отсылать данные клиенту.
            InputStream sin = socket.getInputStream();
            OutputStream sout = socket.getOutputStream();

            // Конвертируем потоки в другой тип, чтоб легче обрабатывать текстовые сообщения.
            DataInputStream in = new DataInputStream(sin);
            DataOutputStream out = new DataOutputStream(sout);

            ObjectInputStream ois = new ObjectInputStream(sin);

            try {
                while (true) {
                    //Десериализация
                    newArray = (ArrayList<Integer>) ois.readObject();
                    String line = newArray.toString();

                    System.out.println("The dumb client just sent me this line : " + line);

                    System.out.println("I'm sending it back...");
                    out.writeUTF(line); // отсылаем клиенту обратно ту самую строку текста.
                    out.flush(); // заставляем поток закончить передачу данных.
                }
            } catch (EOFException e) {
            }
        } catch (Exception x) {
            x.printStackTrace();
        }
        //System.out.println(newArray.toString());
    }
}