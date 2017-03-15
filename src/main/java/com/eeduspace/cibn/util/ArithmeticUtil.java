package com.eeduspace.cibn.util;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.eeduspace.cibn.model.KnowledgeModel;
import com.eeduspace.cibn.model.ProducesModel;
import com.eeduspace.cibn.model.Production;
import com.eeduspace.cibn.model.WeakKnowledgePointModel;
import com.eeduspace.cibn.model.request.VideoRequest;
import com.eeduspace.cibn.persist.enumeration.LearnAbilityTypeEnum;
import com.eeduspace.uuims.comm.util.HTTPClientUtils;
import com.google.gson.Gson;

/**
 * 诊断报告算法
 * 
 * @author pc
 *
 */
@Service
public class ArithmeticUtil{
	
	/**
	 * 知识点掌握程度
	 * 
	 * @param tests
	 * @return
	 */
	public static Map<String, Double> getRight(List<KnowledgeModel> tests,List<WeakKnowledgePointModel> pointModels) {
		Map<String, Double> rightMap = new HashMap<>();
		Map<String, Double> map = new HashMap<>();
		for (KnowledgeModel test : tests) {
			for (WeakKnowledgePointModel kn : test.getKnowledges()) {
				for (WeakKnowledgePointModel weakKnowledgePointModel : pointModels) {
					if(weakKnowledgePointModel.getKnowledgeCode().equals(kn.getKnowledgeCode())){
						Double score = rightMap.get(weakKnowledgePointModel.getKnowledgeName()) == null ? 0.0 : rightMap.get(weakKnowledgePointModel.getKnowledgeName());
						if (test.getIsRight()) {
							rightMap.put(weakKnowledgePointModel.getKnowledgeName(), score + test.getKnowlegesScore());
						}else{
							rightMap.put(weakKnowledgePointModel.getKnowledgeName(), score);
						}
						Double allScoreDouble=map.get(weakKnowledgePointModel.getKnowledgeName())==null?0.0:map.get(weakKnowledgePointModel.getKnowledgeName());
						map.put(weakKnowledgePointModel.getKnowledgeName(), allScoreDouble+ test.getKnowlegesScore());
					}
				}
			}
		}

		Map<String, Double> newMap = new HashMap<>();
		for (Entry<String, Double> en : rightMap.entrySet()) {
			newMap.put(en.getKey(), en.getValue() / map.get(en.getKey()));
		}

		return newMap;
	}

	/**
	 * 攻克可提分
	 * 
	 * @param tests
	 * @return
	 */
	public static Map<String, Double> getError(List<KnowledgeModel> tests) {
		Map<String, Double> map = new HashMap<>();
		for (KnowledgeModel test : tests) {
			for (WeakKnowledgePointModel kn : test.getKnowledges()) {
				Double d = map.get(kn.getKnowledgeName()) == null ? 0.0 : map.get(kn);
				if (test.getIsRight() == false) {
					map.put(kn.getKnowledgeName(), d + test.getKnowlegesScore());
				}

			}
		}

		return map;
	}
	/**
	 * 能力图形 七科  除语文 英语
	 * @return
	 */
	public static Map<Integer, Double> getGraspMapFor7Subject(List<ProducesModel> testProduces) {
		Map<String, Double> map = new HashMap<>();
		Map<String, Double> totalMap = new HashMap<>();

		for (ProducesModel t : testProduces) {
			for (Production production : t.getProduces()) {
					Double old = map.get(production.getProductionType()) == null ? 0.0 : map.get(production.getProductionType());
					Double d = ProducesModel.producesCoefficient.get(production.getProductionType()) * t.getScore() / t.getAllScore();
					if (t.getIsRight() == true) {
						map.put(production.getProductionType(), old + d);
					}
					totalMap.put(production.getProductionType(), old + d);
			}
		}

		for (Entry<String, Double> en : map.entrySet()) {
			String key = en.getKey();
			Double dou = en.getValue() / totalMap.get(key);
			en.setValue(dou);
		}

		Map<Integer, Double> newMap = new HashMap<>();
		Double ss = map.get("事实(F)") == null ? 0.0 : map.get("事实(F)");
		Double jg = map.get("具体概念类") == null ? 0.0 : map.get("具体概念类");
		Double cg = map.get("抽概类") == null ? 0.0 : map.get("抽概类");
		Double jy = map.get("具体原理类") == null ? 0.0 : map.get("具体原理类");
		Double cy = map.get("抽象原理类") == null ? 0.0 : map.get("抽象原理类");
		Double fy = map.get("方法运用类") == null ? 0.0 : map.get("方法运用类");
		Double ff = map.get("方法评价类") == null ? 0.0 : map.get("方法评价类");
		Double fp = map.get("方法评价类") == null ? 0.0 : map.get("方法评价类");
		Double cl = map.get("创造策略类") == null ? 0.0 : map.get("创造策略类");
		newMap.put(LearnAbilityTypeEnum.ANALYSIS.getValue(), Double.parseDouble(new DecimalFormat("0").format((jy*5+cg*10+cy*15+ff*30+fp*30))));
		newMap.put(LearnAbilityTypeEnum.APPLY.getValue(), Double.parseDouble(new DecimalFormat("0").format((fy*100))));
		newMap.put(LearnAbilityTypeEnum.CREATE.getValue(), Double.parseDouble(new DecimalFormat("0").format((cl*100))));
		newMap.put(LearnAbilityTypeEnum.MEMORY.getValue(), Double.parseDouble(new DecimalFormat("0").format((ss* 50+jg*50))));
		Double allDouble=0.0;
		Double aveDouble=0.0;
		Map<Integer, Double> returnMap=new HashMap<>();
		for (Integer producesModel : newMap.keySet()) {
			allDouble+=newMap.get(producesModel);
		}
		aveDouble=allDouble/newMap.size();
		for (Integer producesModel : newMap.keySet()) {
			if(newMap.get(producesModel)==new Double("0").doubleValue()){
				returnMap.put(producesModel,aveDouble);
			}else{
				returnMap.put(producesModel, newMap.get(producesModel));
			}
		}
		return returnMap;
	}
	
	/**
	 * 能力图形  语文 英语 产生的图形
	 * @return
	 */
	public static Map<Integer, Double> getGraspFor2Subject(List<ProducesModel> testProduces) {
		Map<String, Double> map = new HashMap<>();
		Map<String, Double> totalMap = new HashMap<>();

		for (ProducesModel t : testProduces) {
			for (Production production : t.getProduces()) {
					Double old = map.get(production.getProductionType()) == null ? 0.0 : map.get(production.getProductionType());
					Double d = ProducesModel.producesCoefficient.get(production.getProductionType()) * t.getScore() / t.getAllScore();
					if (t.getIsRight() == true) {
						map.put(production.getProductionType(), old + d);
					}
					totalMap.put(production.getProductionType(), old + d);
			}
		}

		for (Entry<String, Double> en : map.entrySet()) {
			String key = en.getKey();
			Double dou = en.getValue() / totalMap.get(key);
			en.setValue(dou);
		}

		Map<Integer, Double> newMap = new HashMap<>();
		Double lj = map.get("联结型") == null ? 0.0 : map.get("联结型");
		Double bd = map.get("表达型") == null ? 0.0 : map.get("表达型");
		Double cz = map.get("操作型") == null ? 0.0 : map.get("操作型");
		newMap.put(LearnAbilityTypeEnum.THOUGHT.getValue(), Double.parseDouble(new DecimalFormat("0").format((cz*100))));
		newMap.put(LearnAbilityTypeEnum.EXPRESS.getValue(), Double.parseDouble(new DecimalFormat("0").format((bd*100))));
		newMap.put(LearnAbilityTypeEnum.MEMORY.getValue(), Double.parseDouble(new DecimalFormat("0").format((lj*100))));
		
		
		Double allDouble=0.0;
		Double aveDouble=0.0;
		Map<Integer, Double> returnMap=new HashMap<>();
		for (Integer producesModel : newMap.keySet()) {
			allDouble+=newMap.get(producesModel);
		}
		aveDouble=allDouble/newMap.size();
		for (Integer producesModel : newMap.keySet()) {
			if(returnMap.get(producesModel)==null){
				returnMap.put(producesModel, aveDouble);
			}else{
				returnMap.put(producesModel, newMap.get(producesModel));
			}
		}
		return returnMap;
	}
	/**
	 * 获取错误题的产生式集合
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年4月25日 下午1:37:57
	 * @param producesModels  题对应的产生式集合
	 * @return
	 */
	public static List<String> getErrorSubjectProductionCodes(List<ProducesModel> producesModels) {
		List<String> productionCodes = new ArrayList<>();
		for (ProducesModel producesModel : producesModels) {
			if (!producesModel.getIsRight()) {
				for (Production production : producesModel.getProduces()) {
					productionCodes.add(production.getProductionCode());
				}
			}
		}
		return productionCodes;
	}
	/*public static void main(String[] args) {
		List<KnowledgeModel> list = new ArrayList<>();
		List<String> knowledges = new ArrayList<>();
		knowledges.add("AAA");
		knowledges.add("BBB");
		knowledges.add("CCC");
		knowledges.add("DDD");
		KnowledgeModel t1 = new KnowledgeModel(5.0, true, knowledges);

		List<String> knowledges2 = new ArrayList<>();
		knowledges2.add("AAA");
		knowledges2.add("FFF");
		KnowledgeModel t2 = new KnowledgeModel(5.0, false, knowledges2);

		list.add(t1);
		list.add(t2);

		Map<String, Double> map = getRight(list);

		List<TestProduces> testPs = new ArrayList<>();

		List<String> pes = new ArrayList<>();
		pes.add("方运类");
		pes.add("抽概类");
		pes.add("具概类");

		List<String> pes2 = new ArrayList<>();
		pes2.add("方运类");
		pes2.add("具原类");
		pes2.add("具概类");

		TestProduces tt1 = new TestProduces(pes, 10.0, true);

		TestProduces tt2 = new TestProduces(pes2, 10.0, true);
		testPs.add(tt1);
		testPs.add(tt2);

		Map<String, Integer> map4 = getGraspMap(testPs);

		// Map<String,Double> map = getError(list);
	}*/
	/**
	 * 获取知识点错误的数量
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年4月27日 上午10:21:07
	 * @param tests
	 * @return
	 */
	public static List<WeakKnowledgePointModel> getWeakKnowledge(List<KnowledgeModel> tests){
		Gson gson=new Gson();
		List<WeakKnowledgePointModel> knowledgePointModels=new ArrayList<>();
		List<String> allKnowledgeList=new ArrayList<>();
		for (KnowledgeModel knModel : tests) {
			if(!knModel.getIsRight()){
				List<WeakKnowledgePointModel>kList=knModel.getKnowledges();
				for (WeakKnowledgePointModel k : kList) {
					allKnowledgeList.add(k.getKnowledgeCode());
				}
			}
			
		}
		//获取知识点对应错误题数
		for (String string : allKnowledgeList) {
			WeakKnowledgePointModel knowledgePointModel=new WeakKnowledgePointModel();
			knowledgePointModel.setKnowledgeCode(string);
			knowledgePointModel.setErrorTimes(Collections.frequency(allKnowledgeList, string));
			knowledgePointModels.add(knowledgePointModel);
		}
		
		//去除重复的数据
		for (int i = 0; i < knowledgePointModels.size() - 1; i++) {  
            for (int j = knowledgePointModels.size() - 1; j > i; j--) {  
                if(knowledgePointModels.get(j).getKnowledgeCode().equals(knowledgePointModels.get(i).getKnowledgeCode())){  
                	knowledgePointModels.remove(j);  
                }  
            }  
        }  
		
		return knowledgePointModels;
	}
	
	public static List<WeakKnowledgePointModel> getAllWeakKnowledge(List<KnowledgeModel> tests){
		List<WeakKnowledgePointModel> knowledgePointModels=new ArrayList<>();
		List<String> allKnowledgeList=new ArrayList<>();
		for (KnowledgeModel knModel : tests) {
				List<WeakKnowledgePointModel>kList=knModel.getKnowledges();
				for (WeakKnowledgePointModel k : kList) {
					allKnowledgeList.add(k.getKnowledgeCode());
				}
			
		}
		//获取知识点对应错误题数
		for (String string : allKnowledgeList) {
			WeakKnowledgePointModel knowledgePointModel=new WeakKnowledgePointModel();
			knowledgePointModel.setKnowledgeCode(string);
			knowledgePointModels.add(knowledgePointModel);
		}
		
		//去除重复的数据
		for (int i = 0; i < knowledgePointModels.size() - 1; i++) {  
            for (int j = knowledgePointModels.size() - 1; j > i; j--) {  
                if(knowledgePointModels.get(j).getKnowledgeCode().equals(knowledgePointModels.get(i).getKnowledgeCode())){  
                	knowledgePointModels.remove(j);  
                }  
            }  
        }  
		
		return knowledgePointModels;
	}
	
}
