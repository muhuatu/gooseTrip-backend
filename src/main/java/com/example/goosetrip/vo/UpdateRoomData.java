package com.example.goosetrip.vo;

public class UpdateRoomData {

	private int accommodationId;

	private boolean finished;

	public int getAccommodationId() {
		return accommodationId;
	}

	public void setAccommodationId(int accommodationId) {
		this.accommodationId = accommodationId;
	}

	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}

	public UpdateRoomData(int accommodationId, boolean finished) {
		super();
		this.accommodationId = accommodationId;
		this.finished = finished;
	}

	public UpdateRoomData() {
		super();
	}

}
