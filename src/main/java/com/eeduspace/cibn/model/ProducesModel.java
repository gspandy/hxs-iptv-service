package com.eeduspace.cibn.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 题目对应产生式
 * @author pc
 *
 */
public class ProducesModel {

	/**
	 * 题目分值
	 */
	private Double score;

	/** 所有的产生式包含类型 */
	private List<Production> produces;
	
	/**
	 * 产生式总分数
	 */
	private Double allScore;

	/** 是否正确 */
	private Boolean isRight;
	
	public static Map<String,Double> producesCoefficient;
	
	
	static {
		producesCoefficient  = new HashMap<>();
		 
		producesCoefficient.put("事实(F)",0.1);
		producesCoefficient.put("具体概念类",0.3);
		producesCoefficient.put("抽概类",0.5);
		producesCoefficient.put("具体原理类",0.5);
		producesCoefficient.put("抽象原理类",0.7);
		producesCoefficient.put("方法运用类",0.4);
		producesCoefficient.put("方法分析类",0.8);
		producesCoefficient.put("方法评价类",1.0);
		producesCoefficient.put("创造策略类",1.2);
		producesCoefficient.put("联结型",1.0);
		producesCoefficient.put("表达型",1.0);
		producesCoefficient.put("操作型",1.0);
		
	}

	/**
	 * 
	 * 
	 * 
	 * @param producesCoefficient 产生式系数表 
	 * @param produces 包含的产生式
	 * @param testScore 本体的分数
	 * @param isRight 是否答对
	 */
	public ProducesModel (List<Production> produces ,Double score,Boolean isRight){
		this.score = score;
		this.isRight = isRight;
		this.produces=produces;
		Double allScore = 0.0;
		for (Production key : produces) {
			allScore = allScore + producesCoefficient.get(key.getProductionType());
		}
		this.allScore = allScore;
	}



	public Double getAllScore() {
		return allScore;
	}

	public void setAllScore(Double allScore) {
		this.allScore = allScore;
	}

	public Boolean getIsRight() {
		return isRight;
	}

	public void setIsRight(Boolean isRight) {
		this.isRight = isRight;
	}

	@Override
	public String toString() {
		return "{\"testScore\":\"" + score + "\",\"produces\":\"" + produces + "\",\"allScore\":\"" + allScore + "\",\"isRight\":\"" + isRight + "\"}  ";
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}



	public List<Production> getProduces() {
		return produces;
	}



	public void setProduces(List<Production> produces) {
		this.produces = produces;
	}
	 
}
