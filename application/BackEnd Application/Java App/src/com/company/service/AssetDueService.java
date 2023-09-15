package com.company.service;

import java.util.List;

import com.company.Exceptions.AssetAlreadyBorrowedException;
import com.company.Exceptions.AssetAlreadyExistsException;
import com.company.Exceptions.TechnicalException;
import com.company.dao.AssetDueDaoImpl;
import com.company.model.Asset;
import com.company.model.AssetDue;

public class AssetDueService implements AssetDueServiceInterface{
AssetDueDaoImpl dao;
	
	public AssetDueDaoImpl getDao() {
	return dao;
}
	public void setDao(AssetDueDaoImpl dao) {
		this.dao = dao;
	}
	public boolean getAssetDue(int userId, int assetId) throws TechnicalException, AssetAlreadyExistsException, AssetAlreadyBorrowedException {
		return dao.createAssetDue(userId, assetId);
	}
	public List<Asset> searchAllAsset(int userId){
		return dao.readAllAsset(userId);
	}
		public List<Asset> searchAllDueAsset(int userId) {
			return dao.readAllDueAsset(userId);
		}
		public List<AssetDue> searchAllOverDueUser() {
			return dao.readAllOverDueUser();
		}
		public boolean returnAsset(int userId, int assetId) {
			return dao.returnAsset(userId, assetId);
		}
		//operation 
		public double calculateDueFee(int userId, int assetId){
			return dao.calculateDueFee(userId, assetId);
		}
}
