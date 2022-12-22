package com.example.demo.services;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

import org.springframework.stereotype.Component;

import com.example.demo.models.IpStatus;

@Component
public class IpStatusChecker {

    final private int TIME_LIMIT = 10000;

    public int getIpStatus(String ip) {
        // try {
        // InetAddress address = InetAddress.getByName(ip);
        // boolean reachable = address.isReachable(TIME_LIMIT);

        // System.out.println(ip);
        // System.out.println(reachable);

        // if(reachable) {
        // return IpStatus.CONNECTED;
        // }
        // return IpStatus.DIS_CONNECTED;

        // } catch (Exception e) {
        // e.printStackTrace();
        // return IpStatus.UNKNOW;
        // }

        // Any Open port on other machine
        // openPort = 22 - ssh, 80 or 443 - webserver, 25 - mailserver etc.
        int[] arr = { 22, 80, 443, 25 };
        for (int port : arr) {
            try (Socket soc = new Socket()) {
                soc.connect(new InetSocketAddress(ip, port), 2000);
                return IpStatus.CONNECTED;
            } catch (IOException ex) {
            }
        }

        return IpStatus.DIS_CONNECTED;
    }
}
