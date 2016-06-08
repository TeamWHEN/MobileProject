package com.example.ysm0622.app_when.map;

import java.util.List;

public interface OnFinishSearchListener {
	public void onSuccess(List<Item> itemList);
	public void onFail();
}
