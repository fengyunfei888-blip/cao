package com.cardev.deploy;

import java.io.ByteArrayOutputStream;
import java.net.InetAddress;

public class DnsResponseBuilder {

    public byte[] buildAResponse(byte[] query, int queryLength, String ip) throws Exception {
        if (query == null || queryLength < 16) throw new IllegalArgumentException("invalid dns query");

        int questionEnd = findQuestionEnd(query, queryLength);
        if (questionEnd < 0) throw new IllegalArgumentException("invalid dns question");

        ByteArrayOutputStream out = new ByteArrayOutputStream(512);

        // Transaction ID
        out.write(query[0]);
        out.write(query[1]);
        // Standard response, recursion desired + recursion available, no error
        out.write(0x81);
        out.write(0x80);
        // QDCOUNT = 1
        out.write(0x00);
        out.write(0x01);
        // ANCOUNT = 1
        out.write(0x00);
        out.write(0x01);
        // NSCOUNT / ARCOUNT = 0
        out.write(0x00);
        out.write(0x00);
        out.write(0x00);
        out.write(0x00);

        // Original question section
        out.write(query, 12, questionEnd - 12);

        // Answer name pointer to QNAME at offset 12
        out.write(0xC0);
        out.write(0x0C);
        // TYPE A
        out.write(0x00);
        out.write(0x01);
        // CLASS IN
        out.write(0x00);
        out.write(0x01);
        // TTL 30 sec
        out.write(0x00);
        out.write(0x00);
        out.write(0x00);
        out.write(0x1E);
        // RDLENGTH 4
        out.write(0x00);
        out.write(0x04);

        byte[] rawIp = InetAddress.getByName(ip).getAddress();
        if (rawIp.length != 4) throw new IllegalArgumentException("IPv4 required");
        out.write(rawIp);

        return out.toByteArray();
    }

    private int findQuestionEnd(byte[] data, int length) {
        int p = 12;
        while (p < length) {
            int labelLen = data[p] & 0xFF;
            p++;
            if (labelLen == 0) {
                if (p + 4 <= length) return p + 4;
                return -1;
            }
            if ((labelLen & 0xC0) != 0 || p + labelLen > length) return -1;
            p += labelLen;
        }
        return -1;
    }
}
