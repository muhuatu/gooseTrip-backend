package com.example.goosetrip.vo;

import java.util.List;

import com.example.goosetrip.dto.MapRoute;

public class SearchSpotAndRouteSortOutRes extends BasicRes {

	private List<SpotData> spotList;

	private List<MapRoute> routeList;

	public List<SpotData> getSpotList() {
		return spotList;
	}

	public void setSpotList(List<SpotData> spotList) {
		this.spotList = spotList;
	}

	public List<MapRoute> getRouteList() {
		return routeList;
	}

	public void setRouteList(List<MapRoute> routeList) {
		this.routeList = routeList;
	}

	public SearchSpotAndRouteSortOutRes(List<SpotData> spotList, List<MapRoute> routeList) {
		super();
		this.spotList = spotList;
		this.routeList = routeList;
	}

	public SearchSpotAndRouteSortOutRes() {
		super();
	}

	public SearchSpotAndRouteSortOutRes(int code, String message) {
		super(code, message);
	}

	public SearchSpotAndRouteSortOutRes(int code, String message, List<SpotData> spotList, List<MapRoute> routeList) {
		super(code, message);
		this.spotList = spotList;
		this.routeList = routeList;
	}
}
