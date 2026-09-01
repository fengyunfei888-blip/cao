package com.cardev.deploy;


import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


public class UdpDnsServer {


    private boolean running = false;

    private Thread thread;


    private DnsPacketParser parser;

    private DnsRuleManager ruleManager;



    public UdpDnsServer(
            DnsPacketParser parser,
            DnsRuleManager ruleManager
    ){

        this.parser = parser;

        this.ruleManager = ruleManager;

    }




    public void start(){


        if(running){

            return;

        }


        running = true;


        thread = new Thread(() -> {


            try{


                DatagramSocket socket =
                        new DatagramSocket(53);



                byte[] buffer =
                        new byte[512];



                while(running){


                    DatagramPacket packet =
                            new DatagramPacket(
                                    buffer,
                                    buffer.length
                            );



                    socket.receive(packet);



                    String domain =
                            parser.parseDomain(
                                    packet.getData()
                            );



                    if(domain != null){


                        String ip =
                                ruleManager.getIp(domain);


                        if(ip != null){


                            sendResponse(
                                    socket,
                                    packet,
                                    ip
                            );


                        }


                    }


                }


                socket.close();



            }catch(Exception e){


                e.printStackTrace();


            }



        });


        thread.start();


    }





    private void sendResponse(
            DatagramSocket socket,
            DatagramPacket request,
            String ip
    ){


        try{


            byte[] data =
                    ip.getBytes();



            DatagramPacket response =
                    new DatagramPacket(
                            data,
                            data.length,
                            request.getAddress(),
                            request.getPort()
                    );


            socket.send(response);



        }catch(Exception e){


            e.printStackTrace();


        }


    }





    public void stop(){

        running = false;

    }




    public boolean isRunning(){

        return running;

    }


}
