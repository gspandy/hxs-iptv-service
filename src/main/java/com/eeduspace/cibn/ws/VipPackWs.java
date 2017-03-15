package com.eeduspace.cibn.ws;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.eeduspace.cibn.convert.CIBNConvert;
import com.eeduspace.cibn.model.VipPackModel;
import com.eeduspace.cibn.persist.po.VIPPack;
import com.eeduspace.cibn.response.BaseResponse;
import com.eeduspace.cibn.service.VipPackService;
import com.google.gson.Gson;

@Component
@Path(value = "/vip_pack")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
public class VipPackWs {
	@Inject
	private VipPackService vipPackService;
	private Gson gson=new Gson();
	private final Logger logger = LoggerFactory.getLogger(VipPackWs.class);
	@GET
	@Path("/get_all")
	public Response saveVipBuyRecord(	@Context HttpServletRequest request,@QueryParam("requestId") String requestId){
		BaseResponse baseResponse=new BaseResponse();
		List<VIPPack> packs=vipPackService.findAll();
		List<VipPackModel> models=new ArrayList<>();
		for (VIPPack vipPack : packs) {
			VipPackModel model=new VipPackModel();
			model=CIBNConvert.fromVipPackPo(vipPack);
			models.add(model);
		}
		baseResponse.setResult(models);
		return Response.ok(gson.toJson(baseResponse)).build();
	}
}
