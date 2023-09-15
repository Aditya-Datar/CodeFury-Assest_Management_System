package com.company.dao;

import java.sql.SQLException;
import java.util.List;

import com.company.model.Asset;
import com.company.model.AssetCategory;

public interface AssetCategoryInterface {
	
     AssetCategory getOrCreateAssetCategory(String category) throws SQLException;
     AssetCategory getAssetCategoryByCategory(String category) throws SQLException;
     AssetCategory createAssetCategory(AssetCategory newCategory) throws SQLException;

}
