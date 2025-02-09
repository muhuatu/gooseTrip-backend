package com.example.goosetrip.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.goosetrip.service.MapPlaceService;
import com.example.goosetrip.vo.BasicRes;
import com.example.goosetrip.vo.UpdateMapPlaceReq;

@RestController
@CrossOrigin
@RequestMapping("map_place/")
public class MapPlaceController {

	@Autowired MapPlaceService mapPlaceService;
	
	@PostMapping(value = "savePlace")
	public BasicRes updateMapPlace(@RequestBody UpdateMapPlaceReq req) {
		return mapPlaceService.saveMapPlace(req);
	}
	@PostMapping(value = "saveAllPlace")
	public BasicRes saveAllMapPlace(@RequestBody List<UpdateMapPlaceReq> reqList) {
		return mapPlaceService.saveAllMapPlace(reqList);
	}
	@PostMapping(value = "updateAllPlace")
	public BasicRes updateAllMapPlace(@RequestBody List<UpdateMapPlaceReq> reqList) {
		return mapPlaceService.updateAllPlace(reqList);
	}
}
