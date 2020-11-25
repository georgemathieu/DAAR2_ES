package com.daar.example.resources;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.daar.example.models.es.EsCV;
import com.daar.example.services.CVService;

@RestController
@RequestMapping("v1/api/cvs")
public class CVResource {
	
	@Autowired
	private CVService cvService;
	
	@GetMapping
	public ResponseEntity<List<EsCV>> getCV() throws IOException {
		return ResponseEntity.ok(cvService.getCVs());
	}
	
	@GetMapping(params = {"competence"})
	public ResponseEntity<List<EsCV>> getCVBySkill(@RequestParam("competence") String comp) throws IOException {
		return ResponseEntity.ok(cvService.getCVs(comp));
	}
	
	@PostMapping
    @ResponseBody
    public Map<String,Object> postCV(@RequestBody CVContext cv)
    {
    	HashMap<String, Object> map = new HashMap<>();
    	//List<User> list = userService.authentifyUser(user.getUsername(),user.getPassword());
//    	if (!list.isEmpty())
//    	{
//    		map.put("status", 200);
//    		map.put("message", "user found");
//    		map.put("username", user.getUsername());
//    		map.put("type", list.get(0).getIs_admin());
//    		return map;
//    	} else {
//    		map.put("status", 404);
//    		map.put("message", "user not found");
//    		return map;
//    	}
    	return null;
    }
	

}
