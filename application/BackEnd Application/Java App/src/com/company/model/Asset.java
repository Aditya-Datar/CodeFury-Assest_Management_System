package com.company.model;

import java.util.Date;

public class Asset {
	private int id;
    private String name;
    //private int categoryId;
    private AssetCategory category;
    private String description;
    private Date dateAdded;
    private boolean isAvailable;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getDateAdded() {
		return dateAdded;
	}
	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}
	public boolean isAvailable() {
		return isAvailable;
	}
	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}
//	public int getCategoryId() {
//        return categoryId;
//    }
//
//    public void setCategoryId(int categoryId) {
//        this.categoryId = categoryId;
//    }
	
	public AssetCategory getCategory() {
        return category;
    }

    public void setCategory(AssetCategory category) {
        this.category = category;
    }
	public Asset(int id, String name,AssetCategory category, String type, String description, Date dateAdded, boolean isAvailable) {
		super();
		this.id = id;
		this.name = name;
		this.category = category;
		this.description = description;
		this.dateAdded = dateAdded;
		this.isAvailable = isAvailable;
	}
	public Asset() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Asset [id=" + id + ", name=" + name +  ", description=" + description + ", dateAdded="
				+ dateAdded + ", isAvailable=" + isAvailable + "]";
	}
	
    
}
