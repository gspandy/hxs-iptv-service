package com.eeduspace.cibn.convert;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.eeduspace.cibn.model.AnswerResultModel;
import com.eeduspace.cibn.model.AppUpdateModel;
import com.eeduspace.cibn.model.CourseFavoritesModel;
import com.eeduspace.cibn.model.DiagnosticReportModel;
import com.eeduspace.cibn.model.ProductOrderModel;
import com.eeduspace.cibn.model.UserLogModel;
import com.eeduspace.cibn.model.UserModel;
import com.eeduspace.cibn.model.VipOrderModel;
import com.eeduspace.cibn.model.VipPackModel;
import com.eeduspace.cibn.model.WeakKnowledgePointModel;
import com.eeduspace.cibn.model.response.AnaModel;
import com.eeduspace.cibn.model.response.OptModel;
import com.eeduspace.cibn.persist.enumeration.UserEnum;
import com.eeduspace.cibn.persist.po.AnswerResult;
import com.eeduspace.cibn.persist.po.AppUpdatePo;
import com.eeduspace.cibn.persist.po.CourseFavorites;
import com.eeduspace.cibn.persist.po.DiagnosticReport;
import com.eeduspace.cibn.persist.po.ProBuyRecordPo;
import com.eeduspace.cibn.persist.po.UserLogPo;
import com.eeduspace.cibn.persist.po.UserPo;
import com.eeduspace.cibn.persist.po.VIPPack;
import com.eeduspace.cibn.persist.po.VipBuyRecord;
import com.eeduspace.uuims.comm.util.base.DateUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/*import com.eeduspace.cibn.persist.po.Stage;*/

/**
 * Author: dingran
 * Date: 2016/4/20
 * Description: model与实体转换
 */

public class CIBNConvert {
    private static final Logger logger = LoggerFactory.getLogger(CIBNConvert.class);
    public static UserModel fromUserPo(UserPo userPo,UserEnum.ScanStatus scanStatus) {
        UserModel userModel = new UserModel();
        if(userPo!=null){
            userModel.setUserCode(userPo.getUserCode());
            userModel.setUserName(userPo.getUserName());
            userModel.setEmail(userPo.getEmail());
            userModel.setMobile(userPo.getMobile());
            userModel.setVIPExpireDays(0l);
            userModel.setOverdue(true);
            userModel.setVIPExpireTime(null);
            if(!StringUtils.isEmpty(userPo.getVIPExpireTime())){
                userModel.setVIPExpireTime(DateUtils.getTimeStampStr(userPo.getVIPExpireTime()));
                Date dateNow=new Date();
                if(userPo.getVIPExpireTime().after(dateNow)){
                    userModel.setOverdue(false);
                    userModel.setVIPExpireDays(com.eeduspace.cibn.util.DateUtils.daysBetween(new Timestamp(dateNow.getTime()), new Timestamp(userPo.getVIPExpireTime().getTime()))+1);
                }
            }
            userModel.setPassword(userPo.getPassword());
            userModel.setVip(userPo.isVIP());
            userModel.setScanStatus(scanStatus==null?"":scanStatus.toString());
            userModel.setCreateDate(DateUtils.getTimeStampStr(userPo.getCreateDate()));
        }

        return userModel;
    }
    
    public UserEnum.EquipmentType converterSourceEquipmentType(String type) {
        if(org.apache.commons.lang3.StringUtils.isBlank(type)){
            return null;
        }
        switch (type) {
            case "Test":
                return UserEnum.EquipmentType.Test;
            case "Android":
                return UserEnum.EquipmentType.Android;
            case "Ios":
                return UserEnum.EquipmentType.Ios;
            case "Tv":
                return UserEnum.EquipmentType.Tv;
            default:
                return UserEnum.EquipmentType.Web;
        }
    }
    public static VipOrderModel vipBuyRecord2VipOrderModel ( VipBuyRecord vipBuyRecord){
    	VipOrderModel model=new VipOrderModel();
		model.setOrderName(vipBuyRecord.getOrderName());
		model.setOrderUUID(vipBuyRecord.getUuid());
		model.setBuyType(vipBuyRecord.getBuyType());
		model.setBuyDate(DateUtils.toString(vipBuyRecord.getCreateDate(), DateUtils.DATE_FORMAT_DATEONLY));
		model.setDiagnosticUUID(vipBuyRecord.getDiagnositcUUID());
		model.setOrderPrice(vipBuyRecord.getVipPrice());
		model.setOrderSn(vipBuyRecord.getOrderSN());
		model.setUserCode(vipBuyRecord.getUserCode());
		model.setVipDays(vipBuyRecord.getVipDays());
		model.setVipOrderType(vipBuyRecord.getVipType());
		return model;
    }
    public static VipOrderModel productBuyRecordProductOrderModel ( ProBuyRecordPo productBuyRecord){
    	/*
		 *  "orderName": "诊断购买VIP20160424151011000001",
            "orderUUID": "90e7010c49a44fa3968f960283e2d756",
            "buyType": "DIAGNOSTIC",
            "userCode": "b3dbb6ce6acd4035b5108548add0801b",
            "orderSn": "ZD20160428164058000007",
            "orderPrice": 0.01,
            "vipOrderType": "0",
            "vipDays": 0,
            "buyDate": "2016-04-28"
		 * */
    	//ProductOrderModel model=new ProductOrderModel();
    	VipOrderModel model=new VipOrderModel();
    	model.setOrderName(productBuyRecord.getOrderName());
    	model.setOrderUUID(productBuyRecord.getUuid());
    	model.setBuyType(productBuyRecord.getBuyType());
    	model.setBuyDate(DateUtils.toString(productBuyRecord.getCreateDate(), DateUtils.DATE_FORMAT_DATEONLY));
    	//model.setDiagnosticUUID(productBuyRecord.getDiagnositcUUID());
    	//model.setProductUuid(productBuyRecord.getProductUuid());
    	model.setOrderPrice(productBuyRecord.getProductPrice());
    	model.setOrderSn(productBuyRecord.getOrderSn());
    	model.setUserCode(productBuyRecord.getUserCode());
    	//model.setExpireTime(productBuyRecord.getProductDays()+"");
    	model.setVipDays(null);
    	//model.setProductOrderType(productBuyRecord.getProductType());
    	model.setVipOrderType(null);
    	return model;
    }
    
    public static CourseFavoritesModel toCourseFavoritesModel(CourseFavorites courseFavorites){
		CourseFavoritesModel courseFavoritesModel = new CourseFavoritesModel();
		courseFavoritesModel.setId(courseFavorites.getId());
		courseFavoritesModel.setCourseId(courseFavorites.getCourseId());
		courseFavoritesModel.setCourseName(courseFavorites.getCourseName());
		courseFavoritesModel.setCreateDate(courseFavorites.getCreateDate());
		courseFavoritesModel.setIsDel(courseFavorites.isDel());
		courseFavoritesModel.setUserCode(courseFavorites.getUserCode());
		courseFavoritesModel.setUuid(courseFavorites.getUuid());
		courseFavoritesModel.setUnitCode(courseFavorites.getUnitCode());
		courseFavoritesModel.setProduction(courseFavorites.getProduction());

        courseFavoritesModel.setStageCode(courseFavorites.getStageCode());
        courseFavoritesModel.setGradeCode(courseFavorites.getGradeCode());
        courseFavoritesModel.setSubjectCode(courseFavorites.getSubjectCode());
        courseFavoritesModel.setBookTypeCode(courseFavorites.getBookTypeCode());
        courseFavoritesModel.setBookTypeVersionCode(courseFavorites.getBookTypeVersionCode());
        courseFavoritesModel.setStageName(courseFavorites.getStageName());
        courseFavoritesModel.setSubjectName(courseFavorites.getSubjectName());
        courseFavoritesModel.setGradeName(courseFavorites.getGradeName());
        courseFavoritesModel.setBookTypeName(courseFavorites.getBookTypeName());
        courseFavoritesModel.setBookTypeVersionName(courseFavorites.getBookTypeVersionName());
		return courseFavoritesModel;
	}
	public static CourseFavorites toCourseFavorites(CourseFavoritesModel courseFavoritesModel){
		CourseFavorites courseFavorites = new CourseFavorites();
		courseFavorites.setCourseId(courseFavoritesModel.getCourseId());
		courseFavorites.setBookTypeName(courseFavoritesModel.getBookTypeName());
		courseFavorites.setStageName(courseFavoritesModel.getStageName());
		courseFavorites.setSubjectName(courseFavoritesModel.getSubjectName());
		courseFavorites.setGradeName(courseFavoritesModel.getGradeName());
		courseFavorites.setBookTypeVersionName(courseFavoritesModel.getBookTypeVersionName());
		courseFavorites.setCourseName(courseFavoritesModel.getCourseName());
		courseFavorites.setDel(courseFavoritesModel.getIsDel());
		if (!StringUtils.isEmpty(courseFavoritesModel.getUuid())) {
			courseFavorites.setUuid(courseFavoritesModel.getUuid());
		}
		courseFavorites.setUserCode(courseFavoritesModel.getUserCode());
		courseFavorites.setUnitCode(courseFavoritesModel.getUnitCode());
		courseFavorites.setProduction(courseFavoritesModel.getProduction());

        courseFavorites.setStageCode(courseFavoritesModel.getStageCode());
        courseFavorites.setGradeCode(courseFavoritesModel.getGradeCode());
        courseFavorites.setSubjectCode(courseFavoritesModel.getSubjectCode());
        courseFavorites.setBookTypeCode(courseFavoritesModel.getBookTypeCode());
        courseFavorites.setBookTypeVersionCode(courseFavoritesModel.getBookTypeVersionCode());

		return courseFavorites;
	}
	
	public static VipPackModel fromVipPackPo(VIPPack vipPack){
		VipPackModel vipPackModel=new VipPackModel();
		vipPackModel.setVipDesc(vipPack.getVipDesc());
		vipPackModel.setVipPrice(vipPack.getVipPrice());
		vipPackModel.setVipType(vipPack.getVipType());
		if (vipPack.getDiscountStartDate()!=null&&vipPack!=null) {
			if(DateUtils.isBetween(new Date(), vipPack.getDiscountStartDate(), vipPack.getDiscountEndDate(), 1)){
				//vipPackModel.setVipSale(vipPack.getVipSale());
				if(vipPack.getVipSale()!=null){
					if(vipPack.getVipSale().intValue()==0){
						vipPack.setVipSale(10d);
					}
					BigDecimal dd=new BigDecimal(vipPack.getVipSale()).multiply(new BigDecimal(vipPack.getVipPrice()));
					vipPackModel.setVipPrice(dd.divide(new BigDecimal(10),2,BigDecimal.ROUND_HALF_EVEN).doubleValue());
				}
			}
		}
		
		return vipPackModel;
	}
	/**
     * 转换
     * Author： zhuchaowei
     * e-mail:zhuchaowei@e-eduspace.com
     * 2016年4月21日 上午11:25:37
     * @param diagnosticReport
     * @param isAll  是否转换所有属性  true 是  false 否
     * @return
     */
    @SuppressWarnings("unchecked")
	public static DiagnosticReportModel fromDiagnosticReport(DiagnosticReport diagnosticReport,Boolean isAll){
    	Gson gson=new Gson();
    	DiagnosticReportModel diagnosticReportModel=new DiagnosticReportModel();
    	diagnosticReportModel.setPaperName(diagnosticReport.getPaperName());
    	diagnosticReportModel.setPaperCode(diagnosticReport.getPaperCode());
    	diagnosticReportModel.setVolumeCode(diagnosticReport.getVolumeCode());
    	diagnosticReportModel.setVolumeName(diagnosticReport.getVolumeName());
    	diagnosticReportModel.setStageCode(diagnosticReport.getStageCode());
    	diagnosticReportModel.setBuy(diagnosticReport.isBuy());
    	diagnosticReportModel.setDiagnosticReportUUID(diagnosticReport.getUuid());
    	diagnosticReportModel.setStageName(diagnosticReport.getStageName());
    	diagnosticReportModel.setUserCode(diagnosticReport.getUserCode());
    	diagnosticReportModel.setGradeName(diagnosticReport.getGradeName());
    	diagnosticReportModel.setVersionName(diagnosticReport.getVersionName());
    	diagnosticReportModel.setSubjectName(diagnosticReport.getSubjectName());
    	diagnosticReportModel.setGradeCode(diagnosticReport.getGradeCode());
    	diagnosticReportModel.setScore(diagnosticReport.getScore());
    	diagnosticReportModel.setDiagnosticDate(DateUtils.toString(diagnosticReport.getCreateDate(), DateUtils.DATE_FORMAT_DATETIME));
    	diagnosticReportModel.setSubjectCode(diagnosticReport.getSubjectCode());
    	diagnosticReportModel.setUnitCode(diagnosticReport.getUnitCode());
    	diagnosticReportModel.setUseTime(diagnosticReport.getUseTime());
    	diagnosticReportModel.setVersionCode(diagnosticReport.getVersionCode());
		if (isAll) {
			diagnosticReportModel.setRankPercentage(diagnosticReport
					.getRankPercentage());
			diagnosticReportModel.setUserRanking(diagnosticReport
					.getUserRanking());
			// 做题结果
			// if(org.apache.commons.lang3.StringUtils.isBlank(diagnosticReport.getDiagnosticResult())){
			// List<DiagnosticResultModel> resultModels=
			// gson.fromJson(diagnosticReport.getDiagnosticResult(), new
			// TypeToken<List<DiagnosticResultModel>>(){}.getType());
			// diagnosticReportModel.setDiagnosticResult(resultModels);
			// }
			List<WeakKnowledgePointModel> list=new ArrayList<>();
			// 弱项知识点
			diagnosticReportModel.setWeak(diagnosticReport.getWeak());
			Map<String, Double> map=new HashMap<String, Double>();
			try {
				list=gson.fromJson(diagnosticReport.getRecommendedCourses(),  new TypeToken<List<WeakKnowledgePointModel>>() {}.getType());
				// 推荐课程
				diagnosticReportModel.setRecommendedCourses(list);
				map=gson.fromJson(diagnosticReport.getKnowledgeMastery(), Map.class);
				// 知识点掌握程度
				diagnosticReportModel.setKnowledgeMasteryMap(map);
			} catch (Exception e) {
			}
			// 产生式图表
			diagnosticReportModel.setProductionChartMap(diagnosticReport
					.getProductionChart());
		}
    	return diagnosticReportModel;
    }
	/*public static StageModel toStageModel(Stage stage){
		if (StringUtils.isEmpty(stage)) {
			return null;
		}
		StageModel stageModel = new StageModel();
		stageModel.setStageCode(stage.getCode());
		stageModel.setStageName(stage.getStageName());
		stageModel.setVersion(stage.getVersion());
		return stageModel;
	}*/
    /**
     * 转换日志Model
     * @param userLogPos
     * @param userPo,Boolean isAll 
     * @return
     */
    
	public static UserLogModel toUserLogModel(UserLogPo userLogPos, UserPo userPo,Boolean isAll) {
		UserLogModel userLogModel = new UserLogModel();
		userLogModel.setAction(userLogPos.getAction());
		userLogModel.setDescription(userLogPos.getDescription());
		userLogModel.setModule(userLogPos.getModule());
		userLogModel.setRequestId(userLogPos.getRequestId());
		userLogModel.setId(userLogPos.getId());
		userLogModel.setCreateDate(userLogPos.getCreateDate());
		userLogModel.setResult(userLogPos.getResult());
		userLogModel.setSourceIp(userLogPos.getSourceIp());
		if (isAll) {
			userLogModel.setUserCode(userPo.getUserCode());
		}
		return userLogModel;
	}
	public static AnswerResultModel fromAnswerResult(AnswerResult answerResult){
		Gson gson=new Gson();
		AnswerResultModel answerResultModel=new AnswerResultModel();
		List<AnaModel> analysisModels=gson.fromJson(answerResult.getAnswerAnalysis(), new TypeToken<List<AnaModel>>() {}.getType());
		answerResultModel.setAnswerAnalysis(analysisModels);
		List<OptModel> optionModels=gson.fromJson(answerResult.getOptionInfo(), new TypeToken<List<OptModel>>() {}.getType());
		answerResultModel.setOptions(optionModels);
		answerResultModel.setAnswerInfo(answerResult.getAnswerInfo());
		answerResultModel.setDiagnosticUUID(answerResult.getDiagnosticUUID());
		answerResultModel.setIsRight(answerResult.isRight());
		answerResultModel.setRightOption(answerResult.getRightOption());
		answerResultModel.setSubjectScore(answerResult.getSubjectScore());
		answerResultModel.setUserOption(answerResult.getUserOption());
		answerResultModel.setVoiceAnalysis(answerResult.getVoiceAnalysis());
		answerResultModel.setSubjectSn(answerResult.getSubjectSn());
		return answerResultModel;
	}
	
	public static AppUpdateModel toAppUpdateModel(AppUpdatePo appUpdatePo){
		AppUpdateModel aum = new AppUpdateModel();
		if (!StringUtils.isEmpty(appUpdatePo)) {
			aum.setId(StringUtils.isEmpty(appUpdatePo.getId()) ? null : appUpdatePo.getId());
			aum.setUuid(StringUtils.isEmpty(appUpdatePo.getUuid()) ? null : appUpdatePo.getUuid());
			aum.setCreateDate(StringUtils.isEmpty(appUpdatePo.getCreateDate()) ? null : appUpdatePo.getCreateDate());
			aum.setAppName(StringUtils.isEmpty(appUpdatePo.getAppName()) ? null : appUpdatePo.getAppName());
			aum.setAppVersion(appUpdatePo.getAppVersion());
			aum.setAppDescribe(StringUtils.isEmpty(appUpdatePo.getAppDescribe()) ? null : appUpdatePo.getAppDescribe());
			aum.setAvailable(StringUtils.isEmpty(appUpdatePo.getAvailable()) ? null : appUpdatePo.getAvailable());
			aum.setDownUrl(StringUtils.isEmpty(appUpdatePo.getDownUrl()) ? null : appUpdatePo.getDownUrl());
			aum.setNecessary(StringUtils.isEmpty(appUpdatePo.getNecessary()) ? null : appUpdatePo.getNecessary());
		}
		return aum;
	}
}
