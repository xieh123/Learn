package com.example.myapplication.util;


import android.text.format.DateFormat;

import com.sun.mail.util.MailSSLSocketFactory;

import java.util.Properties;

import javax.activation.CommandMap;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.activation.MailcapCommandMap;
import javax.mail.Address;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * Created by xieH on 2018/3/9 0009.
 */
public class EmailUtils {

    public void sendMailMsgwithImg(String msg1, String imgUrl) {
        try {
            String sendMsg = "[" + DateFormat.format("yyyy-MM-dd hh:mm:ss", System.currentTimeMillis()).toString() + "]" + msg1;
            Properties props = new Properties();
            // 开启debug调试
            MailSSLSocketFactory sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
            props.put("mail.smtp.ssl.enable", "true");
            props.put("mail.smtp.ssl.socketFactory", sf);

            props.setProperty("mail.debug", "false");
            // 发送邮件协议名称
            props.setProperty("mail.transport.protocol", "smtp");
            props.setProperty("mail.smtp.host", "smtp.qq.com");

            props.setProperty("mail.smtp.auth", "true");

            // 设置环境信息
            Session session = Session.getInstance(props);

            // 创建邮件对象
            javax.mail.Message msg = new MimeMessage(session);
            msg.setSubject("打卡记录");

            MimeBodyPart text = new MimeBodyPart();
            // setContent(“邮件的正文内容”,”设置邮件内容的编码方式”)
            text.setContent(msg1 + "<img src='cid:a'>", "text/html;charset=utf-8");
            MimeBodyPart img = new MimeBodyPart();

            // JavaMail API不限制信息只为文本,任何形式的信息都可能作茧自缚MimeMessage的一部分.
            // 除了文本信息,作为文件附件包含在电子邮件信息的一部分是很普遍的.
            // JavaMail API通过使用DataHandler对象,提供一个允许我们包含非文本BodyPart对象的简便方法.
            DataHandler dh = new DataHandler(new FileDataSource(imgUrl));
            img.setDataHandler(dh);
            // 创建图片的一个表示用于显示在邮件中显示
            img.setContentID("a");
            // 关系  正文和图片的
            MimeMultipart mm = new MimeMultipart();
            mm.addBodyPart(text);
            mm.addBodyPart(img);
            // 设置正文与图片之间的关系
            mm.setSubType("related");
            // 图班与正文的 body
            MimeBodyPart all = new MimeBodyPart();
            all.setContent(mm);
            // 附件与正文（text 和 img）的关系
            MimeMultipart mm2 = new MimeMultipart();
            mm2.addBodyPart(all);
            // 设置正文与附件之间的关系
            mm2.setSubType("mixed");
            msg.setContent(mm2);
            // 保存修改
            msg.saveChanges();
            MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap();
            mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
            mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
            mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
            mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
            mc.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822");
            CommandMap.setDefaultCommandMap(mc);

            // 设置发件人
            msg.setFrom(new InternetAddress("发件方QQ号码" + "@qq.com"));
            // 发送邮件
            Transport transport = session.getTransport();
            transport.connect("发件方QQ号码" + "@qq.com", "QQ邮箱发送邮件的授权码");

            transport.sendMessage(msg, new Address[]{new InternetAddress("收件方QQ号码" + "@qq.com")});
            transport.close();

            System.out.println("成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendMailMsg(String msg1) {
        try {
            String sendMsg = "[" + DateFormat.format("yyyy-MM-dd hh:mm:ss", System.currentTimeMillis()).toString() + "]" + msg1;
            Properties props = new Properties();
            // 开启debug调试
            MailSSLSocketFactory sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
            props.put("mail.smtp.ssl.enable", "true");
            props.put("mail.smtp.ssl.socketFactory", sf);

            props.setProperty("mail.debug", "false");
            // 发送邮件协议名称
            props.setProperty("mail.transport.protocol", "smtp");
            props.setProperty("mail.smtp.host", "smtp.qq.com");

            props.setProperty("mail.smtp.auth", "true");

            // 设置环境信息
            Session session = Session.getInstance(props);

            // 创建邮件对象
            javax.mail.Message msg = new MimeMessage(session);
            msg.setSubject("打卡记录");
            // 设置邮件内容
            msg.setText(sendMsg);
            // 设置发件人
            msg.setFrom(new InternetAddress("发件人qq号码" + "@qq.com"));
            //发送邮件
            Transport transport = session.getTransport();
            transport.connect("发件方QQ号码" + "@qq.com", "QQ邮箱发送邮件的授权码");

            transport.sendMessage(msg, new Address[]{new InternetAddress("收件方QQ号码" + "@qq.com")});
            transport.close();

            System.out.println("成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendEmail(String content) {
        try {
            // 创建配置文件
            Properties props = new Properties();
            // 开启认证
            props.put("mail.smtp.auth", true);
            // 设置协议方式
            props.put("mail.transport.protocol", "smtp");

            // 发件人的邮箱的 SMTP 服务器地址。 qq邮箱："smtp.qq.com" 163邮箱："smtp.163.com"
            String host = "smtp.qq.com";

            // 未采用SSL时，端口一般为25，可以不用设置；采用SSL时，端口为465，需要显示设置
            String port = "465";

            // 设置主机名
            props.put("mail.smtp.host", host);
            // 设置SSL加密
            props.setProperty("mail.smtp.port", port);
            props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.setProperty("mail.smtp.socketFactory.fallback", "false");
            props.setProperty("mail.smtp.socketFactory.port", port);

            String userName = "用户名";
            String password = "密码";

            // 设置账户和密码
            props.put("mail.smtp.username", userName);
            props.put("mail.smtp.password", password);
            // 创建会话，getDefaultInstance得到的始终是该方法初次创建的缺省的对象，getInstance每次获取新对象
            Session session = Session.getDefaultInstance(props);
            // 显示错误信息
            session.setDebug(true);
            // 创建发送时的消息对象
            MimeMessage message = new MimeMessage(session);

            String senderAccount = "发送方的账户";
            String senderName = "发送方的名称";

            // 设置发送发的账户和名称
            message.setFrom(new InternetAddress(senderAccount, senderName, "UTF-8"));

            String receiverAccount = "收件方的账号";

            // 获取收件方的账户和名称
            message.setRecipients(MimeMessage.RecipientType.TO, receiverAccount);

            // To: 收件人（可以增加多个收件人、抄送、密送）
//            message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiverAccount, "亲爱的开发者", "UTF-8"));


            String subject = "邮件主题";

            // 设置主题
            message.setSubject(subject, "UTF-8");
            // 设置内容
            message.setContent(content, "text/html;charset=UTF-8");

            // 保存设置
            message.saveChanges();

            // 发送
            Transport.send(message);


            // 发送邮件, 发到所有的收件地址, message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
//            Transport.send(message, message.getAllRecipients());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
