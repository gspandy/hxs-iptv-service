/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2014 All Rights Reserved.
 */
package com.eeduspace.cibn.alipay;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;


/**
 * API调用客户端工厂
 * 
 */
public class AlipayAPIClientFactory {

    /** API调用客户端 */
    private static AlipayClient alipayClient;
    
    /**
     * 获得API调用客户端
     * 
     * @return
     */
    public static AlipayClient getAlipayClient(String ALIPAY_PUBLIC_KEY,String APP_ID,String PRIVATE_KEY){
        
        if(null == alipayClient){
            alipayClient = new DefaultAlipayClient(AlipayServiceEnvConstants.ALIPAY_GATEWAY, APP_ID, 
                PRIVATE_KEY, "json", AlipayServiceEnvConstants.CHARSET,ALIPAY_PUBLIC_KEY);
        }
        return alipayClient;
    }
}
