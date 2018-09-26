package com.pager.pagerapp.bo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pager.pagerapp.model.Event;
import com.pager.pagerapp.model.Member;
import com.pager.pagerapp.model.NewUserNotification;
import com.pager.pagerapp.model.StatusChangeNotification;
import com.pager.pagerapp.sockets.Socket;
import com.pager.pagerapp.sockets.SocketCallback;
import com.pager.pagerapp.sockets.SocketI;

import java.util.List;

public class SocketBO implements SocketI, SocketCallback {

    private final Socket socket;
    private final Callback callback;

    public SocketBO(Callback callback) {
        this.callback = callback;
        socket = new Socket(this);
    }

    @Override
    public void open() {
        socket.open();
    }

    @Override
    public void close() {
        socket.close();
    }

    @Override
    public void message(String jsonMessage) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

        try {
            Event event = gson.fromJson(jsonMessage, Event.class);
            System.out.println(event.toString());

            if (event.getEvent().equals(Event.EventType.NEW_USER.toString())) {
                NewUserNotification newUserNotification = gson.fromJson(jsonMessage, NewUserNotification.class);
                callback.newMemberUpdate(newUserNotification.getMember());

            } else {
                StatusChangeNotification statusChangeNotification = gson.fromJson(jsonMessage, StatusChangeNotification.class);
                callback.statusUpdate(statusChangeNotification.getState(), statusChangeNotification.getUser());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("1");

    }

    public interface Callback {

        void newMemberUpdate(Member newMember);
        void statusUpdate(String status, String member);

    }
}
