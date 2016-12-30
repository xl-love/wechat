package com.souvc.weixin.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.souvc.weixin.pojo.Token;
import com.souvc.weixin.util.CommonUtil;
import com.souvc.weixin.util.TokenUtil;

/**
* ����: TokenThread </br>
* ����: ��ʱ��ȡ΢��access_token���߳� </br>
* ������Ա�� souvc </br>
* ����ʱ�䣺  Oct 6, 2015 </br>
* �����汾��V1.0  </br>
 */
public class TokenThread implements Runnable {
    private static Logger log = LoggerFactory.getLogger(TokenThread.class);
    // �������û�Ψһƾ֤
    public static String appid = "";
    // �������û�Ψһƾ֤��Կ
    public static String appsecret = "";
    public static Token accessToken = null;

    public void run() {
        while (true) {
            try {
                accessToken = CommonUtil.getToken(appid, appsecret);
                if (null != accessToken) {
                    //���ô洢�����ݿ�
                    TokenUtil.saveToken(accessToken);
                    log.info("��ȡaccess_token�ɹ�����Чʱ��{}�� token:{}", accessToken.getExpiresIn(), accessToken.getAccessToken());
                    // ����7000��
                    Thread.sleep((accessToken.getExpiresIn() - 200)*1000);
                } else {
                    // ���access_tokenΪnull��60����ٻ�ȡ
                    Thread.sleep(60 * 1000);
                }
            } catch (InterruptedException e) {
                try {
                    Thread.sleep(60 * 1000);
                } catch (InterruptedException e1) {
                    log.error("{}", e1);
                }
                log.error("{}", e);
            }
        }
    }
}