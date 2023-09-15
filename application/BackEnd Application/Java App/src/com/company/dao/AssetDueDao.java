package com.company.dao;

import java.util.Date;
import java.util.List;

import com.company.Exceptions.AssetAlreadyBorrowedException;
import com.company.Exceptions.AssetAlreadyExistsException;
import com.company.Exceptions.TechnicalException;
import com.company.model.Asset;
import com.company.model.AssetDue; 

public interface AssetDueDao {
	//Create
	public boolean createAssetDue(int userId, int assetId) throws TechnicalException, AssetAlreadyExistsException, AssetAlreadyBorrowedException;
	//Read
	public List<Asset> readAllAsset(int userId);
	public List<Asset> readAllDueAsset(int userId);
	public List<AssetDue> readAllOverDueUser(); 
	//Update
	
	//Delete
	public boolean returnAsset(int userId, int assetId); 
	
	//operation 
	public double calculateDueFee(int userId, int assetId);
}
