package com.muc.elasticsearch;

import com.muc.elasticsearch.pojo.Article;
import com.muc.elasticsearch.pojo.Book;
import com.muc.elasticsearch.repository.BookRepository;
import io.searchbox.client.JestClient;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.elasticsearch.client.RestClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Springboot03ElasticsearchApplicationTests {

    @Autowired
    JestClient jestClient;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    RestClient restClient;

    @Test
    public void testRestClient(){
    }

    // 测试 BookRepository操作ES
    @Test
    public void testBookRepository(){
        Book book = new Book();
        book.setId(1);
        book.setBookName("西游记");
        book.setAuthor("吴承恩");
        bookRepository.index(book);
    }


    // 测试 JestClient操作ES
    @Test
    public void contextLoads() {
        // 给ES索引(保存)一个文档
        Article article = new Article();
        article.setId(2);
        article.setTitle("好消息");
        article.setAuthor("李四");
        article.setContent("Hello world lisi");

        // 构建一个索引功能
        Index index = new Index.Builder(article).index("muc").type("news").build();

        try {
            jestClient.execute(index);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 测试索引
    @Test
    public void search(){

        String searchJson = "{\n" +
                "    \"query\" : {\n" +
                "        \"match\" : {\n" +
                "            \"content\" : \"Hello\"\n" +
                "        }\n" +
                "    }\n" +
                "}";

        // 构建全文索引
        Search search = new Search.Builder(searchJson).
                addIndex("muc").
                addType("news").build();

        // 执行
        try {
            SearchResult result = jestClient.execute(search);
            System.out.println(result.getJsonString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
