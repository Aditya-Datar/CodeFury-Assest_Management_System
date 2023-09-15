package com.company.model;

import java.util.Date;
import java.util.List;

public class AssetDue {
	private UserDetails borrower;  // Represents the borrower of the assets
	private List<Asset> assets;	 // Represents the list of assets due
	private Date addedDate;		//// Represents the date when the assets were added
	
	public AssetDue() {
		super();
	}
	public AssetDue(UserDetails borrower, List<Asset> assets, Date addedDate) {
		super();
		this.borrower = borrower;
		this.assets = assets;
		this.addedDate = addedDate;
	}

	public UserDetails getBorrower() {
		return borrower;
	}

	public void setBorrower(UserDetails borrower) {
		this.borrower = borrower;
	}

	public List<Asset> getAssets() {
		return assets;
	}

	public void setAssets(List<Asset> assets) {
		this.assets = assets;
	}

	public Date getAddedDate() {
		return addedDate;
	}

	public void setAddedDate(Date addedDate) {
		this.addedDate = addedDate;
	}

	@Override
	public String toString() {
		return "AssetDue [borrower=" + borrower + ", assets=" + assets + ", addedDate=" + addedDate + "]";
	}
	
	
}
