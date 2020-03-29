package com.zzx.elasticsearch.common;

import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;

public class PageUtil {
    public static SearchQuery getPage(int pageNumber, int pageSize, FunctionScoreQueryBuilder functionScoreQueryBuilder){
        Pageable pageable = new PageRequest(pageNumber, pageSize);
        return new NativeSearchQueryBuilder()
                .withPageable(pageable)
                .withQuery(functionScoreQueryBuilder).build();
    }
}
