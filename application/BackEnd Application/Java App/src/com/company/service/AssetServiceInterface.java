package com.company.service;

import java.sql.SQLException;
import java.util.List;

import com.company.model.Asset;

public interface AssetServiceInterface {
	
	    boolean addAsset(Asset asset) throws SQLException ;
	    boolean updateAsset(Asset asset);
	    boolean deleteAsset(int assetId);
	    Asset getAssetById(int assetId);
	    List<Asset> getAllAssets();
	


}
