package com.muc.elasticsearch.repository;

import com.muc.elasticsearch.pojo.Book;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface BookRepository extends ElasticsearchRepository<Book,Integer> {

}
