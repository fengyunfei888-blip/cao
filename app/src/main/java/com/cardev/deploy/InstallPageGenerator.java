package com.cardev.deploy;


public class InstallPageGenerator {


    public String generate(String apkName){


        return

        "<html>" +

        "<head>" +

        "<meta charset='utf-8'>" +

        "<title>车机应用部署助手</title>" +

        "</head>" +


        "<body>" +

        "<h2>车机应用部署助手</h2>" +

        "<hr>" +

        "<p>检测到安装包：</p>" +

        "<h3>" + apkName + "</h3>" +

        "<br>" +

        "<button>立即下载</button>" +

        "</body>" +

        "</html>";

    }


}
