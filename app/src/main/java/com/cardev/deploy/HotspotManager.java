package com.cardev.deploy;


import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;


public class HotspotManager {


    public String getLocalIp(){


        try {


            Enumeration<NetworkInterface> interfaces =
                    NetworkInterface.getNetworkInterfaces();


            while(interfaces.hasMoreElements()){


                NetworkInterface network =
                        interfaces.nextElement();


                Enumeration<InetAddress> addresses =
                        network.getInetAddresses();



                while(addresses.hasMoreElements()){


                    InetAddress address =
                            addresses.nextElement();



                    if(!address.isLoopbackAddress()
                            && address.getHostAddress().contains(".")){


                        return address.getHostAddress();


                    }


                }


            }


        } catch(Exception e){


            e.printStackTrace();


        }


        return "未知";


    }



    public String getWebAddress(){


        return "http://" + getLocalIp() + ":8080";


    }


}
