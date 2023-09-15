package com.company.service;

import java.sql.SQLException;

import com.company.model.AssetCategory;

public interface CategoryServiceInterface {
	 AssetCategory getAssetCategory(String category) throws SQLException;
     AssetCategory getAssetCategoryByName(String category) throws SQLException;
     AssetCategory createAssetCategory(AssetCategory newCategory) throws SQLException;

}
