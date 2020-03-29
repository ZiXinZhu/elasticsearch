package com.zzx.elasticsearch.controller;

import com.zzx.elasticsearch.common.DataUtil;
import com.zzx.elasticsearch.dao.SearchDao;
import com.zzx.elasticsearch.entity.UserEntity;
import com.zzx.elasticsearch.service.SearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
public class SearchController {
    private Integer PAGESIZE=10;
    Logger log= LoggerFactory.getLogger("InfoLogger");
    @Autowired
    private SearchDao searchDao;
    @Autowired
    private SearchService searchService;

    /**
     * 添加数据
     * @return
     */
    @GetMapping("/save")
    public String save(){

        UserEntity userEntity_one =new UserEntity();
        userEntity_one.setId(1L);
        userEntity_one.setUsername("zzx");
        userEntity_one.setPassword("123456-123456");
        userEntity_one.setPhone("123456789");
        userEntity_one.setCreateTime(DataUtil.DateToString(DataUtil.getDate(-2)));
        userEntity_one.setUpdateTime(DataUtil.DateToString(new Date()));

        UserEntity userEntity_two =new UserEntity();
        userEntity_two.setId(2L);
        userEntity_two.setUsername("zzz");
        userEntity_two.setPassword("654321-123456");
        userEntity_two.setPhone("123789456");
        userEntity_two.setCreateTime(DataUtil.DateToString(DataUtil.getDate(-2)));
        userEntity_two.setUpdateTime(DataUtil.DateToString(new Date()));

        UserEntity userEntity_one_res=searchDao.save(userEntity_one);
        UserEntity userEntity_twao_res=searchDao.save(userEntity_two);
        log.info(String.valueOf(userEntity_one_res));
        log.info(String.valueOf(userEntity_twao_res));
        return "成功！";
    }

    /**
     * 全文查找
     * @param pageNumber
     * @param query
     * @return
     */
    @GetMapping("/receive")
    public List<UserEntity> getResult(Integer pageNumber, String query){
        if(pageNumber==null) pageNumber = 0;
        //es搜索默认第一页页码是0
        SearchQuery searchQuery=searchService.getEntitySearchQuery(pageNumber,PAGESIZE,query);
        Page<UserEntity> goodsPage = searchDao.search(searchQuery);
        return goodsPage.getContent();
    }

    /**
     * 精确查找
     * @param pageNumber
     * @param query
     * @return
     */
    @GetMapping("/exact")
    public List<UserEntity> exactSearchQuery(Integer pageNumber, String query){
        if(pageNumber==null) pageNumber = 0;
        //es搜索默认第一页页码是0
        SearchQuery searchQuery=searchService.exactSearchQuery(pageNumber,PAGESIZE,query);
        Page<UserEntity> goodsPage = searchDao.search(searchQuery);
        return goodsPage.getContent();
    }

    /**
     * 范围查找
     * @return
     */
    @GetMapping("/range")
    public List<UserEntity> rangeSearchQuery(){
        //es搜索默认第一页页码是0
        SearchQuery searchQuery=searchService.rangeSearchQuery(PAGESIZE);
        Page<UserEntity> goodsPage = searchDao.search(searchQuery);
        return goodsPage.getContent();
    }

    /**
     * 多条件查找
     * @return
     */
    @GetMapping("/more")
    public List<UserEntity> moreConditionSearchQuery(){
        //es搜索默认第一页页码是0
        SearchQuery searchQuery=searchService.moreConditionSearchQuery(PAGESIZE);
        Page<UserEntity> goodsPage = searchDao.search(searchQuery);
        return goodsPage.getContent();
    }
}
