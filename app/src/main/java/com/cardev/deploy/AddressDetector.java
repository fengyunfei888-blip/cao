package com.cardev.deploy;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

public class AddressDetector {

    public String detectLocalIpv4() {
        try {
            Enumeration<NetworkInterface> all = NetworkInterface.getNetworkInterfaces();
            if (all == null) return "未知";

            List<NetworkInterface> interfaces = Collections.list(all);
            String fallback = null;

            for (NetworkInterface ni : interfaces) {
                if (!ni.isUp() || ni.isLoopback()) continue;

                String name = ni.getName() == null ? "" : ni.getName().toLowerCase();
                List<InetAddress> addresses = Collections.list(ni.getInetAddresses());

                for (InetAddress address : addresses) {
                    if (!(address instanceof Inet4Address) || address.isLoopbackAddress()) continue;
                    String ip = address.getHostAddress();
                    if (ip == null) continue;

                    if (fallback == null && address.isSiteLocalAddress()) fallback = ip;

                    if (looksLikeHotspotInterface(name) && address.isSiteLocalAddress()) {
                        return ip;
                    }
                }
            }

            return fallback == null ? "未知" : fallback;
        } catch (Exception e) {
            return "未知";
        }
    }

    private boolean looksLikeHotspotInterface(String name) {
        return name.startsWith("ap")
                || name.contains("wlan")
                || name.contains("wifi")
                || name.contains("swlan")
                || name.contains("rndis")
                || name.contains("usb")
                || name.contains("eth");
    }
}
