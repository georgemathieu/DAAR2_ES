package com.daar.example.services;

import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.daar.example.models.es.EsCV;
import com.google.gson.Gson;

@Service
public class CVService {
	
	@Autowired
	private RestHighLevelClient restHighLevelClient;
	
	@Autowired
	private Gson gson;
	
	@GetMapping
	public List<EsCV> getCVs() throws IOException {
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder()
                .size(5000)
                .query(QueryBuilders.matchAllQuery());

        return executeQuery(searchSourceBuilder);
	}
	
	@GetMapping
	public List<EsCV> getCVs(String competence) throws IOException {
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder()
                .size(5000)
                .query(QueryBuilders.matchQuery("content", competence));

        return executeQuery(searchSourceBuilder);
	}
	
	
	private List<EsCV> executeQuery(SearchSourceBuilder searchSourceBuilder) throws IOException {
        SearchRequest searchRequest = new SearchRequest("cvs");
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        return Stream.of(searchResponse.getHits().getHits())
                .map(hit -> hit.getSourceAsString())
                .map(cv -> gson.fromJson(cv, EsCV.class))
                .collect(toList());
    }
	

}
