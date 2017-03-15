package com.eeduspace.cibn.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.eeduspace.cibn.persist.dao.DictionaryDao;
import com.eeduspace.cibn.persist.po.DictionaryPo;
import com.eeduspace.cibn.service.DictionaryService;


/**
 * @author huyanguang
 *
 */

@Service
public class DictionaryServiceImpl implements DictionaryService {

	@Inject
	private DictionaryDao dictionaryDao;


	@Override
	public DictionaryPo findByName(String name) {
		// TODO Auto-generated method stub
		return dictionaryDao.findByName(name);
	}


	@Override
	public List<DictionaryPo> findAll() {
		// TODO Auto-generated method stub
		return dictionaryDao.findAll();
	}

}
