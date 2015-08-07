package com;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Random;

/**
 * Created by mixmox on 07/08/15.
 */
public class Client {
    private String username;
    private BigInteger I;
    private BigInteger p;
    private BigInteger a;
    public BigInteger A;
    public BigInteger B;
    private BigInteger g;
    private BigInteger k;
    private BigInteger K;
    private BigInteger N;
    public BigInteger s;
    private BigInteger S;
    private BigInteger x;
    private BigInteger u;

    public Client(BigInteger I, String password, BigInteger N, BigInteger g, BigInteger k){
        this.I = I;
        this.p = new BigInteger(password);
        this.N = N;
        this.g = g;
        this.k = k;
    }

    public void generateRandomNumber_a() {
        a = new BigInteger(32, new Random());
    }


    public void calculateA() {
        A = g.modPow(a, N);
    }

    public void calculate_u() {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            u = new BigInteger(md.digest((A.toString() + B.toString()).getBytes()));

        }
        catch (Exception e){
        }
    }

    public void calculate_x() {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            x = new BigInteger(md.digest((s.toString() + p.toString()).getBytes()));
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    public void calculate_S() {
        BigInteger temp1 = B.subtract(k.multiply(g.modPow(x, N)));
        S = temp1.modPow(a.add(u.multiply(x)), N); // S = (B - (k*g^x))^(a+ux)

        System.out.println("S in Client: " + S.toString());
    }

    public void calculate_K() {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            K = new BigInteger(md.digest(S.toString().getBytes()));
        }
        catch (Exception e){
        }
    }
}
