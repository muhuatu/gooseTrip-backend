package com.example.goosetrip.vo;

import java.util.List;

import com.example.goosetrip.dto.MapRoute;
import com.example.goosetrip.dto.Spot;

public class SearchSpotAndRouteRes extends BasicRes{
	
 	private List<Spot>spotList;
 	
 	private List<MapRoute>routeList;

	public SearchSpotAndRouteRes(int code, String message,List<Spot> spotList, List<MapRoute> routeList) {
		super(code, message);
		this.spotList = spotList;
		this.routeList = routeList;
	}

	public SearchSpotAndRouteRes() {
		super();
	}

	public SearchSpotAndRouteRes(int code, String message) {
		super(code, message);
	}

	public List<Spot> getSpotList() {
		return spotList;
	}

	public List<MapRoute> getRouteList() {
		return routeList;
	}
 	
 	
}
