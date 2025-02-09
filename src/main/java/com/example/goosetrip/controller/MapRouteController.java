package com.example.goosetrip.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.goosetrip.service.MapRouteService;
import com.example.goosetrip.vo.BasicRes;
import com.example.goosetrip.vo.UpdateMapRouteReq;

@RestController
@CrossOrigin
@RequestMapping("map_route/")
public class MapRouteController {

	@Autowired MapRouteService mapRouteService;
	
	@PostMapping(value = "saveRoute")
	public BasicRes saveMapRoute(@RequestBody UpdateMapRouteReq req) {
		return mapRouteService.saveMapRoute(req);
	}
	@PostMapping(value = "updateRoute")
	public BasicRes updateMapRoute(@RequestBody UpdateMapRouteReq req) {
		return mapRouteService.updateMapRoute(req);
	}
	// 新增 saveOrUpdateRoute 方法
    @PostMapping(value = "saveOrUpdateRoute")
    public BasicRes saveOrUpdateRoute(@RequestBody UpdateMapRouteReq req) {
        return mapRouteService.saveOrUpdateRoute(req);
    }
    @PostMapping(value = "saveOrUpdateAllRoute")
    public BasicRes saveOrUpdateAllRoute(@RequestBody List<UpdateMapRouteReq> reqList) {
        return mapRouteService.saveOrUpdateAllRoute(reqList); 
    }
    
}
