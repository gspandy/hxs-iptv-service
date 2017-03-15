package com.eeduspace.cibn.service;

import com.eeduspace.uuims.comm.util.base.CommandUtil;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Author: dingran
 * Date: 2016/4/19
 * Description:
 */
public class InitService {

    private static final Logger logger = LoggerFactory.getLogger(InitService.class);

    @Inject
    private DataSource iwrongDataSource;

    private Gson gson=new Gson();

    @PostConstruct
    @Transactional
    public void init() {
        try {

            logger.info("authentication center init successful!");
        } catch (Exception e) {
            logger.error("authentication center init error!", e);
        }
    }

    private Connection getConnection() throws SQLException {
        return iwrongDataSource.getConnection();
    }
    //@Test
    public void test() {
        try {
            // executeShell(sendKondorShellName);
            String res=   CommandUtil.exec("", "cmd /c start *.sh");
            logger.info(res);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
