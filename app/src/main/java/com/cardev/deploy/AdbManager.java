package com.cardev.deploy;


public class AdbManager {


    public static String checkDevice() {


        try {


            Process process = Runtime.getRuntime()
                    .exec("adb devices");


            java.io.BufferedReader reader =
                    new java.io.BufferedReader(
                            new java.io.InputStreamReader(
                                    process.getInputStream()
                            )
                    );


            StringBuilder result = new StringBuilder();


            String line;


            while ((line = reader.readLine()) != null) {

                result.append(line).append("\n");

            }


            String output = result.toString();


            if (output.contains("\tdevice")) {

                return "设备状态：已连接\n" + output;

            } else {

                return "设备状态：未连接";

            }


        } catch (Exception e) {


            return "ADB检测失败：" + e.getMessage();


        }

    }

}
