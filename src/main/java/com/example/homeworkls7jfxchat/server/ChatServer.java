package com.example.homeworkls7jfxchat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatServer {

    private final List<ClientHandler> clients;

    public ChatServer() {
        this.clients = new ArrayList<>();
    }
//
    public void  run() {
        try(ServerSocket serverSocket = new ServerSocket(8190);
            AuthService authService = new InMemoryAuthService()){
            while (true) {
                System.out.println("Ожидаю подключения...");
                Socket socket = serverSocket.accept();
                new ClientHandler(socket, this, authService);
                System.out.println("Клиент подключен.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void broadcast(String message){
        for(ClientHandler client : clients ){
            client.sendMessage(message);

        }
    }

    public void subscribe(ClientHandler client) {
        clients.add(client);
    }

    public boolean isNickBusy(String nick) {
        for (ClientHandler client : clients) {
            if (nick.equals(client.getNick())) {
                return true;
            }
        }
        return false;
    }

    public void unsubscribe(ClientHandler client) {
        clients.remove(client);
    }

    public void sendMsgToClient(ClientHandler from, String nickTo, String msg) {
        for (ClientHandler client: clients){
            if (client.getNick().equals(nickTo)) {
                client.sendMessage("от " + from.getNick() + ": " + msg);
                from.sendMessage("клиенту " + nickTo + ": " + msg);
                return;
            }
        }
        from.sendMessage("Клиента с ником: " + nickTo + " нет в чате!");
    }
}
