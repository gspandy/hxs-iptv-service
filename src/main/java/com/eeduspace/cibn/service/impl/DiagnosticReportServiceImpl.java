package com.eeduspace.cibn.service.impl;

import com.eeduspace.cibn.client.KnowledgeVideoClient;
import com.eeduspace.cibn.convert.CIBNConvert;
import com.eeduspace.cibn.model.*;
import com.eeduspace.cibn.model.request.VideoRequest;
import com.eeduspace.cibn.model.response.AnaModel;
import com.eeduspace.cibn.model.response.OptModel;
import com.eeduspace.cibn.model.response.VideoByKnowledge;
import com.eeduspace.cibn.model.response.VideoReonse;
import com.eeduspace.cibn.persist.dao.DiagnosticReportDao;
import com.eeduspace.cibn.persist.enumeration.LearnAbilityTypeEnum;
import com.eeduspace.cibn.persist.po.AnswerResult;
import com.eeduspace.cibn.persist.po.DiagnosticReport;
import com.eeduspace.cibn.persist.po.LearningAbility;
import com.eeduspace.cibn.persist.po.PaperTypePo;
import com.eeduspace.cibn.persist.po.TempPaperInfo;
import com.eeduspace.cibn.persist.po.UserPo;
import com.eeduspace.cibn.service.AnswerResultService;
import com.eeduspace.cibn.service.DiagnosticReportService;
import com.eeduspace.cibn.service.LearningAbilityService;
import com.eeduspace.cibn.service.PaperTypeService;
import com.eeduspace.cibn.service.TempPaperInfoService;
import com.eeduspace.cibn.service.UserService;
import com.eeduspace.cibn.util.ArithmeticUtil;
import com.eeduspace.cibn.util.Json;
import com.eeduspace.uuims.comm.util.HTTPClientUtils;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.apache.http.client.ClientProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
@Service
public class DiagnosticReportServiceImpl implements DiagnosticReportService {
	private final Logger logger = LoggerFactory.getLogger(DiagnosticReportServiceImpl.class);
	@Value("${cibn.video_request_url}")
	private String videoRequestUrl;
	@Value("${cibn.english.subjectcode}")
	private String englishSubjectCode;
	@Value("${cibn.chinese.subjectcode}")
	private String chineseSubjectCode;
	@Value("${cibn.diagnostic.price}")
	private String diagnosticPrice;
	@Inject
	private DiagnosticReportDao diagnosticReportDao;
	@Inject
	private LearningAbilityService learningAbilityService;
	@Inject
	private AnswerResultService answerResultService;
	@Inject
	private UserService userService;
	@Resource(name = "entityManagerFactory")
    private EntityManagerFactory emf;
	private Gson gson=new Gson();
	@Inject
	private TempPaperInfoService tempPaperInfoService;
	@Inject 
	private KnowledgeVideoClient knowledgeVideoClient;
	@Inject
	private PaperTypeService paperTypeService;
	@Override
	public DiagnosticReport findById(Long id) {
		return diagnosticReportDao.findOne(id);
	}

	@Override
	public Page<DiagnosticReport> findAll(String userCode,boolean  isDel,Pageable pageable) {
		return diagnosticReportDao.findByUserCodeAndIsDelAndIsBuy(userCode, isDel, true,pageable);
	}

	@Override
	public DiagnosticReport delete(String uuid) {
		DiagnosticReport diagnosticReport=this.findByUUID(uuid);
		diagnosticReport.setDel(true);
		return diagnosticReportDao.save(diagnosticReport);
	}

	@Override
	public DiagnosticReport findByUUID(String uuid) {
		return diagnosticReportDao.findByUuid(uuid);
	}

	@Override
	@Transactional
	public DiagnosticReportModel saveDiagnosticReport(String uuid,
			DiagnosticReportModel diagnosticReportModel) throws JsonSyntaxException, ClientProtocolException, IOException {
		UserPo userPo=userService.findByUserCode(uuid);
		if(userPo==null){
			return null;
		}
		DiagnosticReport diagnosticReport=null;
		diagnosticReportModel=getPaperInfo(diagnosticReportModel);
		List<DiagnosticReport> lists=diagnosticReportDao.findByUserCodeAndPaperCodeAndUnitCode(uuid, diagnosticReportModel.getPaperCode(),diagnosticReportModel.getUnitCode());
		if(lists.size()>0){
			diagnosticReport=lists.get(0);
		}else{
			diagnosticReport=new DiagnosticReport();
		}
		//功能暂时不需要
		//获取错误题的产生式 code集合  
		//List<String>productionCodes=ArithmeticUtil.getErrorSubjectProductionCodes(diagnosticReportModel.getProducesModels());
		//根据产生式集合获取视频
		//List<RecommendedCoursesModel> recommendedCourses=getRecommendedCoursesModels(productionCodes);
		
		//产生式图表  学习能力信息 一期去掉
		/*Map<Integer, Double>grasppMap=null;
		
		//语文或英语 能力分析
		if(diagnosticReportModel.getSubjectCode().equals(englishSubjectCode)||diagnosticReportModel.getSubjectCode().equals(chineseSubjectCode)){
			grasppMap=ArithmeticUtil.getGraspFor2Subject(diagnosticReportModel.getProducesModels());
		}else{
			//其它7科的能力分析
			grasppMap=ArithmeticUtil.getGraspMapFor7Subject(diagnosticReportModel.getProducesModels());
		}
		//保存学习能力信息
		saveAbility(grasppMap,uuid,diagnosticReportModel.getPaperCode());*/
		//弱项MAP 知识点对应攻克可得分数  一期去掉
		//Map<String ,Double> knowledegMap= ArithmeticUtil.getError(diagnosticReportModel.getKnowledge());
		
		
		Long rank=countRank(diagnosticReportModel.getPaperCode(),diagnosticReportModel.getScore());
		Long countLong=this.count(diagnosticReportModel.getPaperCode());
		Long total=countLong+1;
		String rankPercentage=	new BigDecimal(total-(rank-1))
		.divide(new BigDecimal(total), 3, BigDecimal.ROUND_DOWN)
		.multiply(new BigDecimal(100)).doubleValue()+"%";
		diagnosticReport.setCreateDate(new Date());
		diagnosticReport.setGradeCode(diagnosticReportModel.getGradeCode());
		diagnosticReport.setPaperCode(diagnosticReportModel.getPaperCode());
		diagnosticReport.setPaperName(diagnosticReportModel.getPaperName());
		diagnosticReport.setScore(diagnosticReportModel.getScore());
		diagnosticReport.setSubjectCode(diagnosticReportModel.getSubjectCode());
		diagnosticReport.setUnitCode(diagnosticReportModel.getUnitCode());
		diagnosticReport.setGradeName(diagnosticReportModel.getGradeName());
		diagnosticReport.setSubjectName(diagnosticReportModel.getSubjectName());
		diagnosticReport.setVersionName(diagnosticReportModel.getVersionName());
		diagnosticReport.setDiagnosticResult(gson.toJson(diagnosticReportModel.getKnowledge()));
		diagnosticReport.setStageName(diagnosticReportModel.getStageName());
		diagnosticReport.setUserCode(diagnosticReportModel.getUserCode());
		diagnosticReport.setUseTime(diagnosticReportModel.getUseTime());
		diagnosticReport.setVersionCode(diagnosticReportModel.getVersionCode());
		diagnosticReport.setRankPercentage(rankPercentage);//排名比例  需要去算
		diagnosticReport.setUserRanking(rank);//排名 需要去算
		diagnosticReport.setStageCode(diagnosticReportModel.getStageCode());
		diagnosticReport.setVolumeCode(diagnosticReportModel.getVolumeCode());
		diagnosticReport.setVolumeName(diagnosticReportModel.getVolumeName());
		if(!userPo.isVIP()){
			diagnosticReport.setBuy(false);
		}else{
			UserModel userModel=CIBNConvert.fromUserPo(userPo, null);
			if(userModel.isOverdue()){
				diagnosticReport.setBuy(false);
			}else{
				diagnosticReport.setBuy(true);
			}
		}
		diagnosticReport=diagnosticReportDao.save(diagnosticReport);//保存报告
		saveAnswerResult(diagnosticReportModel.getDiagnosticResult(),diagnosticReport.getUuid());//保存做题结果
		tempPaperInfoService.deleteByUUID(diagnosticReportModel.getPaperUUID());//删除试卷临时信息
		return CIBNConvert.fromDiagnosticReport(diagnosticReport, true);
	}
	/**
	 * 保存答题结果信息
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年5月5日 上午9:58:48
	 * @param diagnosticResult
	 */
	private void saveAnswerResult(List<AnswerResultModel> diagnosticResult,String diagnositcUUID) {
		//删除所有答题记录
		answerResultService.deleteByDiagnosticUUID(diagnositcUUID);
		for (AnswerResultModel answerResultModel : diagnosticResult) {
			AnswerResult answerResult=new AnswerResult();
			answerResult.setAnswerAnalysis(gson.toJson(answerResultModel.getAnswerAnalysis()));
			answerResult.setAnswerInfo(answerResultModel.getAnswerInfo());
			answerResult.setCreateDate(new Date());
			answerResult.setDiagnosticUUID(diagnositcUUID);
			answerResult.setRight(answerResultModel.getIsRight());
			answerResult.setRightOption(answerResultModel.getRightOption());
			answerResult.setSubjectScore(answerResultModel.getSubjectScore());
			answerResult.setSubjectSn(answerResultModel.getSubjectSn());
			answerResult.setUserOption(answerResultModel.getUserOption());
			answerResult.setVoiceAnalysis(answerResultModel.getVoiceAnalysis());
			answerResult.setOptionInfo(gson.toJson(answerResultModel.getOptions()));
			answerResultService.save(answerResult);
		}
	}

	/**
	 * 诊断报告中添加题干信息和题目解析信息
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年5月10日 上午9:49:50
	 * @param diagnosticReportModel
	 * @return
	 */
	public DiagnosticReportModel getPaperInfo(DiagnosticReportModel diagnosticReportModel){
		TempPaperInfo tempPaperInfo=tempPaperInfoService.findByUUID(diagnosticReportModel.getPaperUUID());
		ExamDataDetailBeanForResponse response=new ExamDataDetailBeanForResponse();
		List<QuestionDataTemp> questionDataTemps=new ArrayList<>();
		List<AnswerResultModel> resultModels=new ArrayList<>();
		List<KnowledgeModel>knowledgeModels=new ArrayList<>();
		if(tempPaperInfo!=null){
			if(!"".equals(tempPaperInfo.getPaperInfo())||tempPaperInfo.getPaperInfo()!=null){
				response=gson.fromJson(tempPaperInfo.getPaperInfo(), ExamDataDetailBeanForResponse.class);
			}
			questionDataTemps=response.getQuestions();
		}
		for (QuestionDataTemp questionDataTemp : questionDataTemps) {
			for (AnswerResultModel answerResultModel : diagnosticReportModel.getDiagnosticResult()) {
				List<WeakKnowledgePointModel> pointModels=new ArrayList<>();
				if(questionDataTemp.getCode().equals(answerResultModel.getCode())){
					if(questionDataTemp.getKnowledgeCodes()!=null){
						for (Object obj : questionDataTemp.getKnowledgeCodes()) {
							WeakKnowledgePointModel model=new WeakKnowledgePointModel();
							model.setKnowledgeCode((String) obj);
							pointModels.add(model);
						}
					}
					KnowledgeModel kModel=new KnowledgeModel(Double.valueOf(answerResultModel.getSubjectScore()), answerResultModel.getIsRight(), pointModels);
					knowledgeModels.add(kModel);
					answerResultModel.setVoiceAnalysis(questionDataTemp.getAudioAnalyzePath());
					answerResultModel.setAnswerInfo(questionDataTemp.getStem());
					answerResultModel.setAnswerAnalysis(questionDataTemp.getAnalyzeModels());
					answerResultModel.setOptions(questionDataTemp.getOptionModels());
					resultModels.add(answerResultModel);
					break;
				}
			}
		}
		diagnosticReportModel.setKnowledge(knowledgeModels);
		diagnosticReportModel.setDiagnosticResult(resultModels);
		return diagnosticReportModel;
	}
	/**
	 * 根据产生式code获取课程
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年4月21日 下午5:49:47
	 * @return
	 */
	public List<RecommendedCoursesModel> findByProductionCode(List<String> pCode){
		RecommendedCoursesModel recommendedCoursesModel=new RecommendedCoursesModel();
		List<RecommendedCoursesModel> list=new ArrayList<>();
		for (String code : pCode) {
			//TODO 根据code获取视频   取一条记录  接口获取
			list.add(recommendedCoursesModel);
		}
		return list;
	}
	/**
	 * 统计排名 
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年4月21日 下午5:55:58
	 * @param paperCode 试卷code
	 * @param score 分数
	 * @return
	 */
	public Long countRank(String paperCode,Integer score){
		String sql="SELECT COUNT(*) FROM cibn_diagnostic_report  where score>=?1 and paper_code=?2 ";
		EntityManager em = emf.createEntityManager();
		Query query=em.createNativeQuery(sql);
		query.setParameter(1, score);
		query.setParameter(2, paperCode);
		Object object =query.getSingleResult();
		em.close();
		BigInteger rank=(BigInteger) object;
		rank=rank.add(new BigInteger("1"));
		return Long.valueOf(rank.longValue());
	}

	@Override
	public Long count(String paperCode) {
		return diagnosticReportDao.count(paperCode);
	}
	/**
	 * 获取弱项视频
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年4月25日 下午4:19:19
	 * @param productionCodes
	 * @return
	 */
	public List<RecommendedCoursesModel> getRecommendedCoursesModels(List<String> productionCodes){
		VideoRequest videoRequest=new VideoRequest();
		videoRequest.setList(productionCodes);
		//弱项推荐课程
		List<RecommendedCoursesModel> recommendedCourses=new ArrayList<>();
		if(productionCodes.size()==0){
			return recommendedCourses;
		}
		try {
			VideoReonse videoReonse=gson.fromJson(HTTPClientUtils.httpPostRequestJson(videoRequestUrl, gson.toJson(videoRequest)), VideoReonse.class);
			List<WebVideoModel> maps=videoReonse.getReponseVedio();
			for (WebVideoModel webVideoModel : maps) {
				RecommendedCoursesModel model=new RecommendedCoursesModel();
				model.setVideo_name(webVideoModel.getVideoName());
				model.setCourseId(webVideoModel.getId());//视频ID
				//model.setVideo_url(webVideoModel.getVideoUrl());
				recommendedCourses.add(model);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return recommendedCourses;
	}

	@Override
	public List<DiagnosticReport> findByUserCodeAndPaperCodeAndUnitCode(String userCode,
			String paperCode,String unitCode) {
		return diagnosticReportDao.findByUserCodeAndPaperCodeAndUnitCode(userCode, paperCode,unitCode);
	}
	
	/**
	 * 返回带视频信息的知识点对应的错误题数
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年4月27日 下午4:31:06
	 * @param lists
	 * @return
	 */
	public List<WeakKnowledgePointModel> getWeakKnowledgePointModels(List<WeakKnowledgePointModel> lists,List<VideoByKnowledge> videoByKnowledges) {
		for (WeakKnowledgePointModel model : lists) {
			for (VideoByKnowledge videoByKnowledge : videoByKnowledges) {
				if (model.getKnowledgeCode().equals(videoByKnowledge.getKnowledge())) {
					model.setWebVideoModel(videoByKnowledge.getResourceVideoList());
					model.setKnowledgeName(videoByKnowledge.getKnowledgeName());
				}
			}
		}
		return lists;
	}
	/**
	 * 获取所有知识点视频信息
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年5月17日 上午9:12:41
	 * @param allKnowledge
	 * @param diagnosticReportModel
	 * @return
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 * @throws JsonSyntaxException 
	 */
	public List<VideoByKnowledge> getVideoInfo(List<WeakKnowledgePointModel> allKnowledge,DiagnosticReportModel diagnosticReportModel) throws JsonSyntaxException, ClientProtocolException, IOException{
		VideoRequest request=new VideoRequest();
		request.setBookTypeCode(diagnosticReportModel.getVersionCode());
		request.setGradeCode(diagnosticReportModel.getGradeCode());
		request.setSubjectCode(diagnosticReportModel.getSubjectCode());
		List<String> konwledgeList=new ArrayList<>();
		for (WeakKnowledgePointModel model : allKnowledge) {
			konwledgeList.add(model.getKnowledgeCode());
		}
		request.setKnowledges(konwledgeList);
		List<VideoByKnowledge> knowledges=new ArrayList<>();
		knowledges = knowledgeVideoClient.getVideo(request);
		for (WeakKnowledgePointModel model : allKnowledge) {
			for (VideoByKnowledge videoByKnowledge : knowledges) {
				if (model.getKnowledgeCode().equals(videoByKnowledge.getKnowledge())) {
					model.setWebVideoModel(videoByKnowledge.getResourceVideoList());
					model.setKnowledgeName(videoByKnowledge.getKnowledgeName());
				}
			}
		}

		return knowledges;
	}
	/**
	 * 保存学习能力信息
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年4月27日 下午5:28:41
	 * @param map
	 */
	public void saveAbility(Map<Integer, Double> map,String userCode,String paperCode){
		for (Integer key : map.keySet()) {
			LearningAbility learningAbility=new LearningAbility();
			learningAbility.setPaperCode(paperCode);
			learningAbility.setUserCode(userCode);
			learningAbility.setAbilityType(LearnAbilityTypeEnum.getEnum(key.intValue()));
			learningAbility.setAbilityScore(map.get(key));
			learningAbilityService.savePo(learningAbility);
		}
		
	}

	@Override
	public DiagnosticReport save(DiagnosticReport diagnosticReport) {
		return diagnosticReportDao.save(diagnosticReport);
	}

	@Override
	public DiagnosticReportModel findDiagnosticReport(String uuid) throws JsonSyntaxException, ClientProtocolException, IOException {
		DiagnosticReport diagnosticReport= this.findByUUID(uuid);
		DiagnosticReportModel diagnosticReportModel=new DiagnosticReportModel();
		if(diagnosticReport!=null){
			if("".equals(diagnosticReport.getRecommendedCourses())||diagnosticReport.getRecommendedCourses()==null){
				diagnosticReportModel.setGradeCode(diagnosticReport.getGradeCode());
				diagnosticReportModel.setSubjectCode(diagnosticReport.getSubjectCode());
				diagnosticReportModel.setVersionCode(diagnosticReport.getVersionCode());
				diagnosticReportModel.setStageCode(diagnosticReport.getStageCode());
				diagnosticReportModel.setVolumeCode(diagnosticReport.getVolumeCode());
				diagnosticReportModel.setVolumeName(diagnosticReport.getVolumeName());
				List<KnowledgeModel> knowledgeModels=gson.fromJson(diagnosticReport.getDiagnosticResult(), new TypeToken<List<KnowledgeModel>>() {}.getType());
				//弱项知识点列表
				List<WeakKnowledgePointModel>weakList=ArithmeticUtil.getWeakKnowledge(knowledgeModels);
				//所有知识点列表
				List<WeakKnowledgePointModel> allweakList=ArithmeticUtil.getAllWeakKnowledge(knowledgeModels);
				//去资源库获取视频数据
				List<VideoByKnowledge> videoByKnowledges=getVideoInfo(allweakList, diagnosticReportModel);
				//错误知识点
				weakList=getWeakKnowledgePointModels(weakList,videoByKnowledges);
				//所有知识点
				allweakList=getWeakKnowledgePointModels(allweakList,videoByKnowledges);
				Map<String ,Double> knowledgeMasteryMap= ArithmeticUtil.getRight(knowledgeModels,allweakList);
				diagnosticReport.setKnowledgeMastery(gson.toJson(knowledgeMasteryMap));//知识点掌握情况
				diagnosticReport.setRecommendedCourses(gson.toJson(weakList));//推荐课程  
				this.save(diagnosticReport);
			}
			List<AnswerResultModel> answerResultModels=answerResultService.findListByDiagnosticUUID(uuid);
			diagnosticReportModel=CIBNConvert.fromDiagnosticReport(diagnosticReport,true);
			diagnosticReportModel.setDiagnosticResult(answerResultModels);
			PaperTypePo findByName = paperTypeService.findByName("单元测试");
			diagnosticReportModel.setDiagnosticPrice(Double.parseDouble(findByName.getPrice()));
		}
		return diagnosticReportModel;
	}
}
