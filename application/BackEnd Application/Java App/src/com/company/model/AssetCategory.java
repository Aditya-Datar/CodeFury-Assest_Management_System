package com.company.model;

public class AssetCategory {
	
	private int id;
    private String category;
    private int lendingPeriodInDays;
    private double lateReturnFee;
    private int banPeriodInDays;

    // Constructors
    public AssetCategory() {
        // Default constructor
    }
    
    public AssetCategory(int id, String category, int lendingPeriodInDays, double lateReturnFee, int banPeriodInDays) {
        this.id = id;
        this.category = category;
        this.lendingPeriodInDays = lendingPeriodInDays;
        this.lateReturnFee = lateReturnFee;
        this.banPeriodInDays = banPeriodInDays;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getLendingPeriodInDays() {
		return lendingPeriodInDays;
	}

	public void setLendingPeriodInDays(int lendingPeriodInDays) {
		this.lendingPeriodInDays = lendingPeriodInDays;
	}

	public double getLateReturnFee() {
		return lateReturnFee;
	}

	public void setLateReturnFee(double lateReturnFee) {
		this.lateReturnFee = lateReturnFee;
	}

	public int getBanPeriodInDays() {
		return banPeriodInDays;
	}

	public void setBanPeriodInDays(int banPeriodInDays) {
		this.banPeriodInDays = banPeriodInDays;
	}

	@Override
	public String toString() {
		return "AssetCategory [id=" + id + ", category=" + category + ", lendingPeriodInDays=" + lendingPeriodInDays
				+ ", lateReturnFee=" + lateReturnFee + ", banPeriodInDays=" + banPeriodInDays + "]";
	}
    
    
	

}
