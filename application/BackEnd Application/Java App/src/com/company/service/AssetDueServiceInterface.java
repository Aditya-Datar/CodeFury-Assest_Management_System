package com.company.service;

import java.util.List;

import com.company.Exceptions.AssetAlreadyBorrowedException;
import com.company.Exceptions.AssetAlreadyExistsException;
import com.company.Exceptions.TechnicalException;
import com.company.model.Asset;
import com.company.model.AssetDue;

public interface AssetDueServiceInterface {
	public boolean getAssetDue(int userId, int assetId) throws TechnicalException, AssetAlreadyExistsException, AssetAlreadyBorrowedException;

	public List<Asset> searchAllAsset(int userId);
	public List<Asset> searchAllDueAsset(int userId);
	public List<AssetDue> searchAllOverDueUser(); 
	public boolean returnAsset(int userId, int assetId); 
	//operation 
	public double calculateDueFee(int userId, int assetId);
}
