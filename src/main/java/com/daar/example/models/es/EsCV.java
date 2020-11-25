package com.daar.example.models.es;

import javax.persistence.Id;

import org.springframework.data.elasticsearch.annotations.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Document(indexName = "cv")
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class EsCV {
	
	@Id
	private Long id;
	
	private String content;
}
