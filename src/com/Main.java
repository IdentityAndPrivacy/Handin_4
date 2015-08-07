package com;


import com.sun.org.apache.xml.internal.security.algorithms.MessageDigestAlgorithm;
import sun.plugin2.message.Message;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.*;
import java.security.cert.Extension;
import java.util.ArrayList;

public class Main {

    static Server server;
    static Client client;
    static BigInteger N = new BigInteger("167609434410335061345139523764350090260135525329813904557420930309800865859473551531551523800013916573891864789934747039010546328480848979516637673776605610374669426214776197828492691384519453218253702788022233205683635831626913357154941914129985489522629902540768368409482248290641036967659389658897350067939");
    static BigInteger g = new BigInteger("2");

    public static void main(String[] args) {
	    // From web client
        String username = "1";
        String password = "111";

        BigInteger I = new BigInteger(username);

        BigInteger k = new BigInteger("0");
        BigInteger v;

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            k = new BigInteger(md.digest((N.toString() + g.toString()).getBytes()));

        }
        catch (Exception e){
        }


        // Flow after HTTP-request is received
        client = new Client(I, password, N, g, k);
        server = new Server(N, g, k);
        server.users = createLookupFile();

        // Client computations
        client.generateRandomNumber_a();
        client.calculateA();

        // Client --> Server
        send_I_A(I, client.A);

        // Server computations
        server.lookup(I);
        server.generateRandomNumber_b();
        server.calculateB();

        // Server --> Client
        send_s_B(server.currentUser.s, server.B);

        // Client computation
        client.calculate_u();
        client.calculate_x();
        client.calculate_S();
        client.calculate_K();

        // Server computation
        server.calculate_u();
        server.calculate_S();

    }

    private static ArrayList<User> createLookupFile() {
        ArrayList<User> users = new ArrayList<User>();
        users.add(new User(new BigInteger("1"), new BigInteger("111"), g, N));
        users.add(new User(new BigInteger("2"), new BigInteger("222"), g, N));
        users.add(new User(new BigInteger("3"), new BigInteger("333"), g, N));
        users.add(new User(new BigInteger("4"), new BigInteger("444"), g, N));
        return users;
    }

    public static void send_I_A(BigInteger I, BigInteger A){
        server.I = I;
        server.A = A;
    }

    public static void send_s_B(BigInteger s, BigInteger B){
        client.s = s;
        client.B = B;
    }
}
