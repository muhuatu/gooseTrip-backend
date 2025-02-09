package com.example.goosetrip.vo;

public class HotelList {

	private String webName;

	private String name;

	private String currency;

	private int oneNightPrice;

	private double opinion;

	private String url;

	private String img;

	private int allPrice;

	public String getWebName() {
		return webName;
	}

	public void setWebName(String webName) {
		this.webName = webName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public int getOneNightPrice() {
		return oneNightPrice;
	}

	public void setOneNightPrice(int oneNightPrice) {
		this.oneNightPrice = oneNightPrice;
	}

	public double getOpinion() {
		return opinion;
	}

	public void setOpinion(double opinion) {
		this.opinion = opinion;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public int getAllPrice() {
		return allPrice;
	}

	public void setAllPrice(int allPrice) {
		this.allPrice = allPrice;
	}

	public HotelList(String webName, String name, String currency, int oneNightPrice, double opinion, String url,
			String img, int allPrice) {
		super();
		this.webName = webName;
		this.name = name;
		this.currency = currency;
		this.oneNightPrice = oneNightPrice;
		this.opinion = opinion;
		this.url = url;
		this.img = img;
		this.allPrice = allPrice;
	}

	public HotelList() {
		super();
	}

}
