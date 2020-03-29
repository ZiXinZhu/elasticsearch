package com.zzx.elasticsearch.service;

import com.zzx.elasticsearch.common.PageUtil;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

@Service
public class SearchService {
    /**
     * 全文查找
     * @param pageNumber
     * @param pageSize
     * @param searchContent
     * @return
     */
    public SearchQuery getEntitySearchQuery(int pageNumber, int pageSize, String searchContent) {
        FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery()
                .add(QueryBuilders.matchQuery("username", searchContent),
                        ScoreFunctionBuilders.weightFactorFunction(100));
//                .add(QueryBuilders.matchPhraseQuery("password", searchContent),
//                        ScoreFunctionBuilders.weightFactorFunction(100))

        // 设置分页
        return PageUtil.getPage(pageNumber,pageSize,functionScoreQueryBuilder);
    }

    /**
     * 精确查找
     * @param pageNumber
     * @param pageSize
     * @param query
     * @return
     */
    public SearchQuery exactSearchQuery(int pageNumber, int pageSize, String query) {
        FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery()
                .add(QueryBuilders.termQuery("username", query),
                        ScoreFunctionBuilders.weightFactorFunction(100))
                //设置权重分 求和模式
                .scoreMode("sum")
                //设置权重分最低分
                .setMinScore(10);
        // 设置分页
        return PageUtil.getPage(pageNumber,pageSize,functionScoreQueryBuilder);
    }

    /**
     * 范围查找
     * @param pageSize
     * @return
     */
    public SearchQuery rangeSearchQuery( int pageSize ) {
        FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery()
//                .add(QueryBuilders.rangeQuery("createTime").from(DataUtil.DateToString(DataUtil.getDate(-5))).to(DataUtil.DateToString(new Date())),
                .add(QueryBuilders.rangeQuery("id").from(0).to(2),
                 ScoreFunctionBuilders.weightFactorFunction(100))
                //设置权重分 求和模式
                .scoreMode("sum")
                //设置权重分最低分
                .setMinScore(10);
        // 设置分页
        return PageUtil.getPage(0,pageSize,functionScoreQueryBuilder);
    }

    /**
     * 多条件查询
     * @param pageSize
     * @return
     */
    public SearchQuery moreConditionSearchQuery( int pageSize ) {
        FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery()
                .add(QueryBuilders.boolQuery().must(QueryBuilders.termQuery("username","zzz")).must(QueryBuilders.termQuery("phone","123789456")),
                        ScoreFunctionBuilders.weightFactorFunction(100))
                .add(QueryBuilders.boolQuery().must(QueryBuilders.termQuery("username","zzx")),ScoreFunctionBuilders.weightFactorFunction(100))
                .scoreMode("sum")
                //设置权重分最低分
                .setMinScore(10);
        // 设置分页
        return PageUtil.getPage(0,pageSize,functionScoreQueryBuilder);
    }
}
