package com.eeduspace.cibn.service;

import java.util.List;

import com.eeduspace.cibn.persist.po.DictionaryPo;


/**
 * @author huyanguang
 * 用来表示当前服务器是否对外开放
 */
public interface DictionaryService {

	List<DictionaryPo> findAll();
	
	DictionaryPo findByName(String name);
}
