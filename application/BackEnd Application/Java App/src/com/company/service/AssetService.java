package com.company.service;

import java.sql.SQLException;
import java.util.List;

import com.company.dao.AssetDao;
import com.company.model.Asset;

public class AssetService implements AssetServiceInterface {
	

	
    private AssetDao assetDao;
    public void setDao(AssetDao dao) {
		this.assetDao = dao;
	}

    public AssetService(AssetDao assetDAO) {
        this.assetDao = assetDAO;
    }

    public AssetService() {
		// TODO Auto-generated constructor stub
	}

	@Override
    public boolean addAsset(Asset asset) throws SQLException  {
        // You can include business logic here if needed
        return assetDao.addAsset(asset);
    }

    @Override
    public boolean updateAsset(Asset asset) {
        // You can include business logic here if needed
        return assetDao.updateAsset(asset);
    }

    @Override
    public boolean deleteAsset(int assetId) {
        // You can include business logic here if needed
        return assetDao.deleteAsset(assetId);
    }

    @Override
    public Asset getAssetById(int assetId) {
        // You can include business logic here if needed
        return assetDao.getAssetById(assetId);
    }

    @Override
    public List<Asset> getAllAssets() {
        // You can include business logic here if needed
        return assetDao.getAllAssets();
    }

	
}
