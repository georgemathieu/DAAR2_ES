package com.daar.example.hooks;

import static org.elasticsearch.common.xcontent.XContentType.JSON;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.daar.example.models.es.EsCV;
import com.daar.example.utils.PDFParser;
import com.daar.example.utils.WordParser;
import com.google.gson.Gson;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CVIndexer {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CVIndexer.class);
	
	private static final String CV_INDEX = "cvs";
	
	@Autowired
	private RestHighLevelClient restHighLevelClient;
	
	@Autowired
	private Gson gson;
	
	@Scheduled(fixedDelay = 5000, initialDelay = 5000)
    public void reindex() throws IOException {
		LOGGER.info("start cv indexing");
		List<EsCV> listeCV = new ArrayList<>();
		//String content = PDFParser.parsePDF(new File("CV_MATHIEU_GEORGE.pdf"));
		String content = "Java";
		EsCV cv = new EsCV(1L, content);
		listeCV.add(cv);
		
//		content = PDFParser.parsePDF(new File("CV_Gaspard_GOUT.pdf"));
//		cv = new EsCV(2L, content);
//		listeCV.add(cv);
//		
//		content = WordParser.parseDocx(new File("CV_MATHIEU_GEORGE.docx"));
//		cv = new EsCV(3L, content);
//		listeCV.add(cv);
//		
//		content = WordParser.parseDoc(new File("CV_MATHIEU_GEORGE.doc"));
//		cv = new EsCV(4L, content);
//		listeCV.add(cv);
		
		// delete index if it exists
        boolean indexExist = restHighLevelClient.indices().exists(new GetIndexRequest(CV_INDEX), RequestOptions.DEFAULT);

        if(indexExist) {
            DeleteIndexRequest deleteIndex = new DeleteIndexRequest(CV_INDEX);
            restHighLevelClient.indices().delete(deleteIndex, RequestOptions.DEFAULT);
        }

        BulkRequest bulkRequest = new BulkRequest();
        listeCV.forEach(c -> {
            IndexRequest indexRequest = new IndexRequest(CV_INDEX)
                    .source(gson.toJson(c), JSON);

            bulkRequest.add(indexRequest);
        });
        restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);

        LOGGER.info("end cv indexing");
	}
}
