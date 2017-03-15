package com.eeduspace.cibn.interceptor;


import com.eeduspace.cibn.persist.enumeration.ServerEnum;
import com.eeduspace.cibn.persist.po.DictionaryPo;
import com.eeduspace.cibn.redis.RedisClientTemplate;
import com.eeduspace.cibn.rescode.ResponseCode;
import com.eeduspace.cibn.response.ResponseItem;
import com.eeduspace.cibn.service.DictionaryService;
import com.eeduspace.cibn.service.TokenService;
import com.eeduspace.cibn.service.UserLogService;
import com.eeduspace.cibn.service.UserService;
import com.eeduspace.cibn.util.CommonUtil;
import com.google.gson.Gson;

import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.transport.http.AbstractHTTPDestination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * @author zz
 * @parame token
 * <p/>
 * DATA:2016-4-26 5:44:34
 * 定义一个cxf拦截器拦截所有url
 * 验证token
 */
public class CIBNMyInterceptor extends AbstractPhaseInterceptor<Message> {

    public String requestId;
    private static Gson gson = new Gson();
    
    private final Logger logger = LoggerFactory.getLogger(CIBNMyInterceptor.class);
    @Inject
    private UserLogService userLogService;
    @Inject
    private RedisClientTemplate redisClientTemplate;
    @Inject
    private UserService userService;
    @Inject
    private TokenService tokenService;
    @Inject
	private DictionaryService dictionaryService;	//注入字典表 Service
	 

    public CIBNMyInterceptor(String phase) {
        super(phase);
    }

    // 指定该拦截器在哪个阶段被激发  RECEIVE只对in有效   send对out有效   PRE_STREAM触发点在流关闭之前
    public CIBNMyInterceptor() {
        super(Phase.RECEIVE);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void handleMessage(Message message) throws Fault {

        logger.debug("开始执行拦截器");

        String REQUEST_URL = (String) message.get(message.REQUEST_URL);
        String QUERY_STRING = (String) message.get(message.QUERY_STRING);
        logger.debug("开始执行拦截器:REQUEST_URL=" + REQUEST_URL);
        logger.debug("开始执行拦截器   METHOD =" + message.get(message.HTTP_REQUEST_METHOD));
        logger.debug("开始执行拦截器:QUERY_STRING=" + QUERY_STRING);

        if ("_wadl".equals(message.get(message.QUERY_STRING))) {
            return;
        }

/*        //todo 1.获取requestId
        Map<String, String> paramsMap = getParamsMap(QUERY_STRING);
        String requestId = paramsMap.get("requestId");
        logger.debug("requestId= {}", requestId);
        if (StringUtils.isBlank(requestId)) {
            requestId = UIDGenerator.getUUID();
            logger.info("UIDGenerator.getUUID() requestId= ：" + requestId);
        }*/
        ResponseItem ri = new ResponseItem();
        HttpServletRequest request = (HttpServletRequest) message.get(AbstractHTTPDestination.HTTP_REQUEST);//这句可以获取到request
        HttpServletResponse response = (HttpServletResponse) message.get(AbstractHTTPDestination.HTTP_RESPONSE);//这句可以获取到request
        logger.info("HttpServletRequest: requestId:{},remoteAddr:{},ContextPath:{},RequestURI:{}", this.requestId, CommonUtil.getIpAddress(request), request.getContextPath(), request.getRequestURI());

        
    	DictionaryPo dictionaryPo = dictionaryService.findByName(ServerEnum.FIRST.getName());
    	try {
	    	if(dictionaryPo == null || !"true".equals(dictionaryPo.getValue())){
	    		logger.debug("------------------------------------------------------服务器暂时不对外开放");
	    		ri.setMessage("The server is not open to the public");
	    		ri.setCode(ResponseCode.SERVER_CLOSE.code);
                ri.setHttpCode(ResponseCode.SERVER_CLOSE.httpCode);
	    		response.getWriter().println(gson.toJson(ri));
                throw new Fault(new NotOpenException("服务器暂时不对外开放！"));
	    	}
	    	logger.debug("------------------------------------------------------服务器为开启状态");
	    	
    	
		} catch (IOException e) {
			e.printStackTrace();
		}

    /*    Map<String, String> map = new HashMap<>();
        String reqParams = null;
        String tokenValue = null;
        String tokenValue1 = null;*/

/*        String url = "/test/postTest,/login/user,/test/testgets";
        String[] arr = url.split(",");//分割字符串得到数组
        for (String str : arr) {
            //如果url位特定的url，则直接不验证
            if (REQUEST_URL.contains(str)) {
                return;
            }
        }

        if (message.get(message.HTTP_REQUEST_METHOD).equals("GET")) {//采用GET方式请求
            reqParams = (String) message.get(message.QUERY_STRING);
            message.remove(message.QUERY_STRING);
            reqParams = this.getParams(this.getParamsMap(reqParams));
            logger.debug("reqParams get {}", reqParams);
            if (StringUtils.isBlank(reqParams)) {
                logger.error("requestId：" + requestId + "," + ResponseCode.PARAMETER_MISS.toString() + "." + ParamName.TOKEN.toString());
                message.getExchange().put(Response.class, Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(this.requestId), ResponseCode.PARAMETER_MISS.toString(), "TOKEN"))).build());
                return;
            }
            message.put(message.QUERY_STRING, reqParams);
            map = this.getParamsMap(reqParams);
        } else if (message.get(message.HTTP_REQUEST_METHOD).equals("POST")) {//采用POST方式请求
            try {
                InputStream inStream = message.getContent(InputStream.class);
                ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len = 0;
                while ((len = inStream.read(buffer)) != -1) {
                    outSteam.write(buffer, 0, len);
                }
                outSteam.close();
                inStream.close();
                reqParams = new String(outSteam.toByteArray(), "utf-8");
                logger.debug("reqParams post {}", reqParams);
                if (StringUtils.isBlank(reqParams)) {
                    logger.error("requestId：" + requestId + "," + ResponseCode.PARAMETER_MISS.toString() + "." + ParamName.TOKEN.toString());
                    message.getExchange().put(Response.class, Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(this.requestId), ResponseCode.PARAMETER_MISS.toString(), "TOKEN"))).build());
                    return;
                }
                if (inStream != null) {
                    message.setContent(InputStream.class, new ByteArrayInputStream(reqParams.getBytes()));
                }
                JSONObject jsonObject = JSONObject.fromObject(reqParams);
                map = JSONObject.fromObject(jsonObject);
            } catch (Exception e) {
                logger.error("MyInterceptor异常", e);
            }
        }

        tokenValue1 = paramsMap.get("token");
        logger.info("token tokenValue1--value：" + tokenValue1);
        tokenValue = map.get("token");
        logger.info("token--value：" + tokenValue);
        if (StringUtils.isBlank(tokenValue) && StringUtils.isBlank(tokenValue1)) {
            logger.error(" tokenValue requestId：" + requestId + "," + ResponseCode.PARAMETER_MISS.toString() + "." + ParamName.TOKEN.toString());
            message.getExchange().put(Response.class, Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(this.requestId), ResponseCode.PARAMETER_MISS.toString(), "TOKEN"))).build());
            return;
        }
        if (StringUtils.isBlank(tokenValue) && StringUtils.isNotBlank(tokenValue1)) {
            tokenValue = tokenValue1;
        }
        //post请求如果url有 body也有，则优先选择body体
        //TODO 验证TOKEN   当没有token 时，则不进行验证
        logger.info("token--value：" + tokenValue);
        if (StringUtils.isBlank(tokenValue)) {
            logger.error(" tokenValue requestId：" + requestId + "," + ResponseCode.PARAMETER_MISS.toString() + "." + ParamName.TOKEN.toString());
            message.getExchange().put(Response.class, Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(this.requestId), ResponseCode.PARAMETER_MISS.toString(), "TOKEN"))).build());
            return;
        }

        //验证redis中获取的token
        String token_ = redisClientTemplate.get(tokenValue);
        logger.info("redis  redisClientTemplate get  token  = " + token_);
        if (StringUtils.isBlank(token_)) {
            //redisClientTemplate.del(token_);
            logger.error(" redisClientTemplate requestId：" + requestId + "," + ResponseCode.FORBIDDEN_TOKENTIMEOUT.toString() + "." + ParamName.TOKEN.toString());
//	                   message.getExchange().put(Response.class, Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(this.requestId), ResponseCode.FORBIDDEN_TOKENTIMEOUT.toString(), "TOKEN"))).build());
            return;
        }*/


    }


    //url参数类型转换map  --get 转换map添加操作
    private Map<String, String> getParamsMap(String strParams) {
        Map<String, String> map = new HashMap<String, String>();
        if (strParams == null || strParams.trim().length() <= 0) {
            return map;
        }
        String[] params = strParams.split("&");
        if (params.length > 0) {
            for (int i = 0; i < params.length; i++) {
                String[] arr = params[i].split("=");
                if (arr.length > 1) {
                    map.put(arr[0], arr[1]);
                }
            }
            return map;
        }
        return map;
    }

    private String getParams(Map<String, String> map) {
        if (map == null || map.size() == 0) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        Iterator<String> it = map.keySet().iterator();
        while (it.hasNext()) {
            String key = it.next();
            String value = map.get(key);
                             /*这里可以对客户端上送过来的输入参数进行特殊处理。如密文解密；对数据进行验证等等。。。
		 		            if(key.equals("content")){ 
		 		                value.replace("%3D", "="); 
		 		                value = DesEncrypt.convertPwd(value, "DES"); 
		 		            }*/
            if (sb.length() <= 0) {
                sb.append(key + "=" + value);
            } else {
                sb.append("&" + key + "=" + value);
            }
        }
        return sb.toString();
    }
}

		  
		    
		    

