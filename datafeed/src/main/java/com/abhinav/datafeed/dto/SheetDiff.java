package com.abhinav.datafeed.dto;

import java.util.List;

public class SheetDiff {

	private String baseSheetId;

	private String compareSheetId;
	
	private List<String[]> changeSet;

	public String getBaseSheetId() {
		return baseSheetId;
	}

	public void setBaseSheetId(String baseSheetId) {
		this.baseSheetId = baseSheetId;
	}

	public String getCompareSheetId() {
		return compareSheetId;
	}

	public void setCompareSheetId(String compareSheetId) {
		this.compareSheetId = compareSheetId;
	}

	public List<String[]> getChangeSet() {
		return changeSet;
	}

	public void setChangeSet(List<String[]> changeSet) {
		this.changeSet = changeSet;
	}
}
