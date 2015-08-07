package com;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by mixmox on 07/08/15.
 */
public class Server {
    public BigInteger A;
    private BigInteger b;
    public BigInteger B;
    private BigInteger g;
    public BigInteger I;
    private BigInteger k;
    private BigInteger K;
    private BigInteger N;
    private BigInteger S;
    private BigInteger x;
    private BigInteger u;
    public ArrayList<User> users;
    public User currentUser;

    public Server(BigInteger N, BigInteger g, BigInteger k){
        this.N = N;
        this.g = g;
        this.k = k;
    }

    public void lookup(BigInteger I) {
        // Find user in database from 'I'
        for (User user: users ){
            if (user.I.equals(I)){
                currentUser = user;
                return;
            }
        }
    }

    public void generateRandomNumber_b() {
        b = (new BigInteger(32, new Random())).mod(N);
    }

    public void calculateB() {
        B = (k.multiply(currentUser.v).add(g.modPow(b,N))).mod(N); // k*v + g^b (mod N)
    }


    public void calculate_u(){
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            u = (new BigInteger(md.digest((A.toString() + B.toString()).getBytes("UTF-8")))).mod(N);

        }
        catch (Exception e){
        }
    }

    public void calculate_S() {
        S = ((A.multiply(currentUser.v.modPow(u,N))).modPow(b, N)).mod(N);
        System.out.println("S in Server: " + S.toString());
    }
}
