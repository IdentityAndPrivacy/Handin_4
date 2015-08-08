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
    public BigInteger M1;
    private BigInteger N;
    public BigInteger s;
    private BigInteger S;
    private BigInteger x;
    private BigInteger u;
    public BigInteger M2;
    public BigInteger M2_server;

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
        S = B.subtract(k.multiply(g.modPow(x, N))).modPow(a.add(u.multiply(x)), N); // S = (B - (k*g^x))^(a+ux)

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

    public void calculate_M1() {
        BigInteger N_Hash;
        BigInteger g_Hash;
        BigInteger I_Hash;

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            // Calculate H(N)
            N_Hash = new BigInteger(md.digest(N.toString().getBytes()));
            md.reset();

            // Calculate H(g)
            g_Hash = new BigInteger(md.digest(g.toString().getBytes()));
            md.reset();

            // Calculate H(I)
            I_Hash = new BigInteger(md.digest(I.toString().getBytes()));
            md.reset();

            // Calculate M1
            M1 = new BigInteger(md.digest( (N_Hash.xor(g_Hash).toString() + I_Hash.toString() + s.toString() + A.toString() + B.toString() + K.toString()  ).getBytes() ));
            System.out.println("Client M1: " + M1.toString());
        }
        catch (Exception e){
        }
    }
    public void calculate_M2() {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            // Calculate H(N)
            M2 = new BigInteger(md.digest((A.toString() + M1.toString() + K.toString() ).getBytes()));
        }
        catch (Exception e) {
        }
    }

    public boolean verify_M2() {
        return M2.equals(M2_server);
    }
}
