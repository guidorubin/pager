package com.pager.pagerapp.sockets;

import com.pager.pagerapp.BuildConfig;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class Socket extends WebSocketListener implements SocketI {

    private static final String BASE_SOCKET = BuildConfig.SOCKET_URL;

    private WebSocket webSocket;
    private final Request request;
    private final OkHttpClient client;
    private SocketCallback socketCallback;

    public Socket(SocketCallback socketCallback) {
        this.socketCallback = socketCallback;
        client = new OkHttpClient.Builder()
                .build();
        request = new Request.Builder().url(BASE_SOCKET).build();

    }

    @Override
    public void open() {
        webSocket = client.newWebSocket(request, this);
    }

    @Override
    public void close() {
        webSocket.close(1001,null);
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        super.onMessage(webSocket, text);
        System.out.println(text);
        socketCallback.message(text);
    }


}