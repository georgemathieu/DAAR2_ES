package com.daar.example.resources;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.daar.example.hooks.CVIndexer;
import com.daar.example.models.es.EsCV;
import com.daar.example.services.CVService;
import com.sun.istack.NotNull;

@RestController
@CrossOrigin
@RequestMapping("v1/api/cvs")
public class CVResource {
	
	@Autowired
	private CVService cvService;
	
	@GetMapping
	public ResponseEntity<List<EsCV>> getCV() throws IOException {
		return ResponseEntity.ok(cvService.getCVs());
	}
	
	@GetMapping(params = {"competence"})
	public Map<String,Object> getCVBySkill(@RequestParam("competence") String comp) throws IOException {
		HashMap<String, Object> map = new HashMap<>();
		List<EsCV> listeCV = cvService.getCVs(comp);
		map.put("status", 200);
		HashMap<String, Object> cvs = new HashMap<>();
		listeCV.forEach(cv -> {
			cvs.put(cv.getId().toString(), cv.getContent());
		});
		map.put("cv", listeCV);
		return map;
	}
	
	@RequestMapping(method = RequestMethod.POST, consumes = {"multipart/form-data"})
    @ResponseBody
    public Map<String,Object> postCV(@RequestPart("file") @NotNull MultipartFile f) throws IOException
    {
		Path path = Paths.get(f.getOriginalFilename()).toAbsolutePath();
		File file = path.toFile();
		f.transferTo(file);
    	HashMap<String, Object> map = new HashMap<>();
    	cvService.postCV(new CVContext(file));
    	map.put("status", 200);
    	map.put("message", "cv post");

    	return map;
    }
}
