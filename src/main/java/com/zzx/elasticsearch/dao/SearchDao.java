package com.zzx.elasticsearch.dao;

import com.zzx.elasticsearch.entity.UserEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface SearchDao extends ElasticsearchRepository<UserEntity, Long> {

}
