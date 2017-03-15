package com.eeduspace.cibn.service.impl;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.eeduspace.cibn.persist.dao.LearningAbilityDao;
import com.eeduspace.cibn.persist.enumeration.LearnAbilityTypeEnum;
import com.eeduspace.cibn.persist.po.LearningAbility;
import com.eeduspace.cibn.service.LearningAbilityService;
@Service
public class LearningAbilityServiceImpl implements LearningAbilityService{
	@Inject
	private LearningAbilityDao abilityDao;
	@Resource(name = "entityManagerFactory")
    private EntityManagerFactory emf;
	@Override
	public LearningAbility savePo(LearningAbility learningAbility) {
		return abilityDao.save(learningAbility);
	}
	@Override
	public Object getAve(String paperCode,LearnAbilityTypeEnum lTypeEnum) {
		String sql="SELECT AVG(ability_score)  FROM cibn_learning_ability   where   paper_code=?1 and ability_type=?2";
		EntityManager em = emf.createEntityManager();
		Query query=em.createNativeQuery(sql);
		query.setParameter(1, paperCode);
		query.setParameter(2, lTypeEnum);
		Object object =query.getSingleResult();
		em.close();
		return object;
	}
	@Override
	public Object getMax(String paperCode,LearnAbilityTypeEnum lTypeEnum) {
		String sql="SELECT MAX(ability_score)  FROM cibn_learning_ability   where   paper_code=?1 and ability_type=?2";
		EntityManager em = emf.createEntityManager();
		Query query=em.createNativeQuery(sql);
		query.setParameter(1, paperCode);
		query.setParameter(2, lTypeEnum);
		Object object =query.getSingleResult();
		em.close();
		return object;
	}
	

}
