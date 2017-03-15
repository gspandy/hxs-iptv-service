package com.eeduspace.cibn.rescode;

/**
 * Author: dingran
 * Date: 2015/10/21
 * Description:返回状态码
 */
public enum ResponseCode {


    /* (code,message,httpCode)
    *
    * pay attention to :
    * the message must  end with '.'
    * */


    SUCCESS("Success", "200", "Success"),

    //service error , 500 begin
    SERVICE_ERROR("Service.Error", "500", "The request service process error."),
    //server end
    //resource error
    RESOURSCEDATA_ERROR("ResourceData.Error","60001","资源库返回数据错误."),
    
    
    PAYFAILED("pay.failed","8000","order pay failed"),
    PAYSUCCESS("pay.success","8001","order pay success"),
    //client error, 400 begin
    UNKNOWN_OPERATION("Operation.UnKnown","40001","The request operation is unsupported."),
    PARAMETER_MISS("Parameter.Miss", "40002","The request parameter is miss."),
    PARAMETER_INVALID("Parameter.Invalid", "40003", "The request parameter is invalid." ),
    STATE_INCORRECT("State.Incorrect", "40004", "The request resource in an incorrect state for the request."),
    RESOURCE_NOTFOUND("Resource.NotFound", "40005", "The request resource is not exist."),
    RESOURCE_INUSE("Resource.InUse", "40006", "The request resource is in use."),
    RESOURCE_DUPLICATE("Resource.Duplicate", "40007", "The request resource is duplicate."),
    RESOURCE_LIMITEXCEEDED("Resource.LimitExceeded", "40008", "The request resource is limitExceeded."),
    RESOURCE_InUSED("Resource.InUSED", "40009", "The request resource is inUSED."),


    FORBIDDEN_AUTHFAILURE("Forbidden.AuthFailure", "40301", "The request credentials is unauthorized，please check your credentials."),

    FORBIDDEN_NOPERMISSION("Forbidden.NoPermission", "40302", "The request resource has no permissions."),
    FORBIDDEN_SIGNATURE_DOESNOT_MATCH("Forbidden.SignatureDoesNotMatch", "40303", "The request signature does not match,please refer to the api reference."),

    FORBIDDEN_TOKENFAILURE("Forbidden.TokenFailure", "40304", "The request token is unauthorized，please check your token."),
    FORBIDDEN_TOKENTIMEOUT("Forbidden.TokenTimeout", "40305", "The request token is timeout."),

    FORBIDDEN_LOCKED("Forbidden.Locked", "40306", "The request account is locked."),

    FORBIDDEN_NOSCAN("Forbidden.NoScan", "40307", "The request resource has no scan."),

    REPEAT_LOGIN("Repeat.Login", "40101", "The user is logged in."),

    

    NOT_LOGIN("Not.Login", "20001", "The user is not login."),
    //client end
	//40307   
	SERVER_OPEN("server.open","40308","The server is opening to the outside world."),
	SERVER_CLOSE("server.close","40309","The server is not open to the public.");
   

    public final String code;
    public final String httpCode;
    public final String message;
    ResponseCode(String code, String httpCode, String message) {
        this.code = code;
        this.httpCode = httpCode;
        this.message = message;
    }

    @Override
    public String toString() {
        return  code;
    }

    public static ResponseCode toEnum(String name) {
        for (ResponseCode res : ResponseCode.values()) {
            if (res.toString().equalsIgnoreCase(name)) {
                return res;
            }
        }
        return null;
    }
}
