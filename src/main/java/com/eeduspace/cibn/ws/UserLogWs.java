package com.eeduspace.cibn.ws;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import com.eeduspace.cibn.convert.CIBNConvert;
import com.eeduspace.cibn.model.UserLogModel;
import com.eeduspace.cibn.persist.po.UserLogPo;
import com.eeduspace.cibn.persist.po.UserPo;
import com.eeduspace.cibn.response.BaseResponse;
import com.eeduspace.cibn.service.EventOperationService;
import com.eeduspace.cibn.service.UserLogService;
import com.eeduspace.cibn.service.UserService;
import com.eeduspace.cibn.util.CommonUtil;
import com.eeduspace.uuims.comm.util.base.UIDGenerator;
import com.eeduspace.uuims.rescode.ResponseCode;
import com.google.gson.Gson;

/**
 * Author:zengzhe
 * Date: 2016/5/3
 * Description:用户操作日志记录
 */
@Component
@Path(value = "/log")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON + ";charset=UTF-8"})
@CrossOriginResourceSharing(allowAllOrigins = true)
public class UserLogWs  {
    private final Logger logger = LoggerFactory.getLogger(UserLogWs.class);
    private String requestId;
    private Gson gson=new Gson();
    @Inject
    private UserService userService;
    @Inject
    private UserLogService  userLogService;
    @Inject
    private EventOperationService eventOperationService;
    /* 用户操作日志记录
     * 用户日志操作
     * @param userCode
     * @param page  页数
     * @param size  条数
     * @param 
     * @return
     */
    @POST
    @Path("/userlog")
    public Response getUserLog(@QueryParam("requestId") String requestId,String requestBody, @Context HttpServletRequest request,@Context HttpServletResponse response) {
		UserLogModel userLogModel = gson.fromJson(requestBody, UserLogModel.class);
        try {
            this.requestId = requestId;
            if (StringUtils.isBlank(this.requestId)) {
                this.requestId = UIDGenerator.getUUID();
            }
            logger.info("HttpServletRequest: requestId:{},remoteAddr:{},ContextPath:{},RequestURI:{},requestBody{}",this.requestId, CommonUtil.getIpAddress(request),request.getContextPath(),request.getRequestURI(),requestBody);
            if(StringUtils.isBlank(userLogModel.getUserCode())){
				logger.error("userlog  ExceptionrequestId："+"requestId,"+ResponseCode.PARAMETER_MISS.toString()+"."+ "userCode");
				return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.PARAMETER_MISS.toString(),"userCode"))).build();
			}
			if(org.springframework.util.StringUtils.isEmpty(userLogModel.getPageable())){
				logger.error("userlog  ExceptionrequestId："+"requestId,"+ResponseCode.PARAMETER_MISS.toString()+"."+ "pageable");
				return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse(requestId), ResponseCode.PARAMETER_MISS.toString(),"pageable"))).build();
			}
			String userCode = userLogModel.getUserCode();	
    		UserPo userPo= userService.findByUserCode(userCode);
            if(userPo==null){
                logger.error("userLog Exception：requestId："+ this.requestId+","+ ResponseCode.PARAMETER_MISS.toString()+".user_id");
                return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse( this.requestId), ResponseCode.PARAMETER_INVALID.toString(), "user_id"))).build();
            }
			PageRequest pageable = new PageRequest(userLogModel.getPageable().getPageNumber(),userLogModel.getPageable().getPageSize());
            Page<UserLogPo> courseFavoritesPage =userLogService.getList(userPo,pageable);
			List<UserLogPo> list=courseFavoritesPage.getContent();
			List<UserLogModel> userLogPoList = new ArrayList<UserLogModel>();
			for (UserLogPo userLogPos : list) {
				userLogPoList.add(CIBNConvert.toUserLogModel(userLogPos,userPo,false));
			}
            BaseResponse baseResponse=new BaseResponse(this.requestId);
			Page<UserLogModel> newPage=new PageImpl<UserLogModel>(userLogPoList, pageable, courseFavoritesPage.getTotalElements());
			baseResponse.setResult(newPage);
            logger.debug("userLog response:"+gson.toJson(baseResponse));
            //TODO 添加log
            //userLogService.create(userPo,LogActionEnum.LOGIN.toString(), LogActionEnum.USER.toString(),true,null,requestId);

            return Response.ok(gson.toJson(baseResponse)).build();
        }catch (Exception e){
            logger.error(" userLog error:", e);
            return Response.ok(gson.toJson(BaseResponse.setResponse(new BaseResponse( this.requestId), ResponseCode.SERVICE_ERROR.toString()))).build();
        }
    }
    

    
  
}
