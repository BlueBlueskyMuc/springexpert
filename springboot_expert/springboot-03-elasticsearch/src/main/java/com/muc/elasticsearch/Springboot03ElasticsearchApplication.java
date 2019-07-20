package com.muc.elasticsearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 *  SpringBoot 默认支持三种技术来和ES交互
 *  1、Jest(默认不生效)
 *      需要导入jest的工具包 io.searchbox.client.JestClient
 *  2、Rest(默认不生效)
 *      需要导入Rest的工具包 org.elasticsearch.client.RestClient;
 *  3、SpringData ElasticSearch
 *      1、Client 节点信息 ClusterNodes ClusterName
 *      2、ElasticsearchTemplate 操作es
 *      3、编写一个 ElasticsearchRepository的子接口来操作ES
 */
@SpringBootApplication
public class Springboot03ElasticsearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(Springboot03ElasticsearchApplication.class, args);
    }

}
