package com.eeduspace.cibn.user;

import com.eeduspace.cibn.BaseTest;
import com.eeduspace.cibn.model.UserInfoModel;
import com.eeduspace.cibn.persist.po.UserPo;
import com.eeduspace.cibn.service.UserService;
import com.eeduspace.cibn.util.JDBCUtil;
import org.apache.commons.lang.StringUtils;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: dingran
 * Date: 2016/4/27
 * Description:
 */
public class InitUserTest extends BaseTest {

//    @Inject
//    private UserInfoService userInfoService;

    @Inject
    private UserService userService;

    @Inject
    private DataSource iwrongDataSource;


   // @Test
    public void userInit() {
        try {

            List<UserInfoModel> userInfoModels=new ArrayList<>();
            Connection conn= getConnection();
            ResultSet resultSet = null;
            String code="'b3dbb6ce6acd4035b5108548add0801b'";
            PreparedStatement preparedStatement= conn.prepareStatement("SELECT * FROM userinfo u");
            resultSet=preparedStatement.executeQuery();

            while (resultSet.next()) {
                Map<String, Object> map = new HashMap<>();

                ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
                int columnCount = resultSetMetaData.getColumnCount();
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = resultSetMetaData.getColumnLabel(i).toUpperCase();// 获取别名
                    Object columnValue = resultSet.getObject(i);
//                    logger.debug("------------columnName--->"+ JDBCUtil.column2field(columnName));
//                    logger.debug("------------columnValue--->"+columnValue );
                    map.put(JDBCUtil.column2field(columnName), columnValue);

                }
                userInfoModels.add(gson.fromJson(gson.toJson(map),UserInfoModel.class));
            }


            conn.close();
            logger.info("authentication center init successful!"+userInfoModels.size());
            for(UserInfoModel userInfoModel:userInfoModels){
                UserPo userPo=new UserPo();
                userPo.setUserCode(userInfoModel.getCtbCode());
                userPo.setMobile(userInfoModel.getMobile());
                userPo.setPassword(userInfoModel.getPassword());
                userPo.setVIP(false);
                userPo.setUserName(userInfoModel.getUserName());
                userPo.setEmail(userInfoModel.getEmail());
                userPo.setRegisterSource("15");
                userPo.setMarketChannel("000");
                userService.save(userPo);
            }
            logger.info("authentication center init successful!");
        } catch (Exception e) {
            logger.error("authentication center init error!", e);
        }
    }
   // @Test
    public void userInitT() {
        try {

            List<UserInfoModel> userInfoModels=new ArrayList<>();
            Connection conn= getConnection();
            ResultSet resultSet = null;
            String code="'b3dbb6ce6acd4035b5108548add0801b'";
            PreparedStatement preparedStatement= conn.prepareStatement("SELECT * FROM userinfo u");
            resultSet=preparedStatement.executeQuery();

            while (resultSet.next()) {
                Map<String, Object> map = new HashMap<>();

                ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
                int columnCount = resultSetMetaData.getColumnCount();
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = resultSetMetaData.getColumnLabel(i).toUpperCase();// 获取别名
                    Object columnValue = resultSet.getObject(i);
//                    logger.debug("------------columnName--->"+ JDBCUtil.column2field(columnName));
//                    logger.debug("------------columnValue--->"+columnValue );
                    map.put(JDBCUtil.column2field(columnName), columnValue);

                }
                userInfoModels.add(gson.fromJson(gson.toJson(map),UserInfoModel.class));
            }


            conn.close();
            logger.info("authentication center init successful!"+userInfoModels.size());
            for(UserInfoModel userInfoModel:userInfoModels){
                UserPo userPo=userService.findByUserCode(userInfoModel.getCtbCode());
                if(userPo!=null){
                    if(StringUtils.isNotBlank(userInfoModel.getPassword())&& !userInfoModel.getPassword().equals(userPo.getPassword())){
                        userPo.setPassword(userInfoModel.getPassword());
                        logger.debug("password is def userPo.pwd:{},userInfoModel.pwd:{}",userPo.getPassword(),userInfoModel.getPassword());
                    }
                    if(StringUtils.isNotBlank(userInfoModel.getMobile())&& !userInfoModel.getMobile().equals(userPo.getMobile())){
                        userPo.setMobile(userInfoModel.getMobile());
                        logger.debug("mobile is def userPo.mobile:{},userInfoModel.mobile:{}",userPo.getMobile(),userInfoModel.getMobile());
                    }
                    if(StringUtils.isNotBlank(userInfoModel.getUserName())&& !userInfoModel.getUserName().equals(userPo.getUserName())){
                        userPo.setUserName(userInfoModel.getUserName());
                        logger.debug("userName is def userPo.userName:{},userInfoModel.userName:{}",userPo.getUserName(),userInfoModel.getUserName());
                    }
                    if(StringUtils.isNotBlank(userInfoModel.getEmail()) && !userInfoModel.getEmail().equals(userPo.getEmail())){
                        userPo.setEmail(userInfoModel.getEmail());
                        logger.debug("email is def userPo.email:{},userInfoModel.email:{}",userPo.getEmail(),userInfoModel.getEmail());
                    }
                    userService.save(userPo);
                }else {
                    logger.debug("ADD NEW USER:{}",userInfoModel.getCtbCode());
                    userPo=new UserPo();
                    userPo.setUserCode(userInfoModel.getCtbCode());
                    userPo.setMobile(userInfoModel.getMobile());
                    userPo.setPassword(userInfoModel.getPassword());
                    userPo.setVIP(false);
                    userPo.setUserName(userInfoModel.getUserName());
                    userPo.setEmail(userInfoModel.getEmail());
                    userPo.setRegisterSource("15");
                    userPo.setMarketChannel("000");
                    userService.save(userPo);
                }
            }
            logger.info("authentication center init successful!");
        } catch (Exception e) {
            logger.error("authentication center init error!", e);
        }
    }

    private Connection getConnection() throws SQLException {
        return iwrongDataSource.getConnection();
    }
}
