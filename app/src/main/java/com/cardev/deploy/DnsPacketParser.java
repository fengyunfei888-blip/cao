package com.cardev.deploy;


public class DnsPacketParser {


    /**
     * 从DNS请求数据中解析域名
     *
     * 当前版本：
     * 提供解析框架
     *
     * 后续接入UDP 53端口数据
     */


    public String parseDomain(byte[] data){


        if(data == null || data.length == 0){

            return null;

        }


        try{


            StringBuilder domain =
                    new StringBuilder();



            int index = 12;


            while(index < data.length){


                int length =
                        data[index] & 0xff;



                if(length == 0){

                    break;

                }



                if(index + length >= data.length){

                    break;

                }



                if(domain.length() > 0){

                    domain.append(".");

                }



                for(int i = 1; i <= length; i++){


                    domain.append(
                            (char)data[index+i]
                    );


                }



                index += length + 1;


            }



            return domain.toString();



        }catch(Exception e){


            return null;


        }


    }



    /**
     * 判断是否为目标域名
     */

    public boolean isTargetDomain(String domain){


        if(domain == null){

            return false;

        }


        return domain.contains("update")
                || domain.contains("download")
                || domain.contains("apk");


    }


}
