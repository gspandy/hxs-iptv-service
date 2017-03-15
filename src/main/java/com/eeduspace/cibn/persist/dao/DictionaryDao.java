package com.eeduspace.cibn.persist.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eeduspace.cibn.persist.po.DictionaryPo;


/**
 * @author huyanguang
 */
public interface DictionaryDao  extends JpaRepository<DictionaryPo, Long> {


	DictionaryPo findByName(String name);

}
