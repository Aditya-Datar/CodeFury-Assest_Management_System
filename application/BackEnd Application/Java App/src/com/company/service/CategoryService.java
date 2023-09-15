package com.company.service;

import java.sql.SQLException;

import com.company.dao.AssetCategoryImpl;

import com.company.model.AssetCategory;

public class CategoryService implements CategoryServiceInterface{
	
	private AssetCategoryImpl catdao;
	
	 public void setDao(AssetCategoryImpl dao) {
			this.catdao = dao;
		}

	    public CategoryService(AssetCategoryImpl dao) {
	        this.catdao = dao;
	    }

	     public CategoryService() {
			
		}

	@Override
	public AssetCategory getAssetCategory(String category) throws SQLException {
		return catdao.getOrCreateAssetCategory(category);
	}

	public AssetCategory getAssetCategoryByName(String category) throws SQLException {
		return catdao.getAssetCategoryByCategory(category);
	}

	@Override
	public AssetCategory createAssetCategory(AssetCategory newCategory) throws SQLException {
		return catdao.createAssetCategory(newCategory);
	}

}
