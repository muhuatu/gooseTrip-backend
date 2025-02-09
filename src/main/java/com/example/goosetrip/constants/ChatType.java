package com.example.goosetrip.constants;

public enum ChatType {

	// 圖片、文字、地點、路徑，前端傳入內容須與 ("內容")相同
	IMAGE("image"), //
	TEXT("text"), //
	PLACE("place"), //
	ROUTE("route");

	private String type;

	private ChatType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	// 比對傳入的chatType是否符合enum中的ChatType
	public static boolean checkType(String type) {
		for (ChatType item : ChatType.values()) {
			if (item.getType().equalsIgnoreCase(type)) {
				return true;
			}
		}
		return false;
	}
}
