package com.jenisk.realmcrudoprations.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.jenisk.realmcrudoprations.model.DirectoryData;
import com.jenisk.realmcrudoprations.model.DirectoryModel;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
/**
 * Created by Jenis Kasundra on 03/05/2018.
 */
public class RealmHelper {

	private static final String TAG = "RealmHelper";

	private Realm realm;
	private RealmResults<DirectoryData> realmResult;

	/**
	 * constructor to create instance realm
	 *
	 * @param context
	 */
	public RealmHelper(Context context) {
		realm = Realm.getInstance(context);
	}

	/**
	 * add data
	 *
	 * @param name
	 * @param address
	 * @param number
	 * @param latitude
	 * @param longitude
	 */
	public void addDirectory(String name,String number, String address,String latitude,String longitude) {
		DirectoryData directoryData = new DirectoryData();
        directoryData.setId((int) (System.currentTimeMillis() / 1000));
        directoryData.setName(name);
        directoryData.setNumber(number);
        directoryData.setAddress(address);
        directoryData.setLatitude(latitude);
        directoryData.setLongitude(longitude);

		realm.beginTransaction();
		realm.copyToRealm(directoryData);
		realm.commitTransaction();

		showLog("Added ; " + name);
	}

	/**
	 * methodto find all data
	 */
	public ArrayList<DirectoryModel> findAllDirectory() {
		ArrayList<DirectoryModel> data = new ArrayList<>();

		realmResult = realm.where(DirectoryData.class).findAll();
		realmResult.sort("id", Sort.DESCENDING);
		showLog("Size : " + String.valueOf(realmResult.get(0).getName()));

		for (int i = 0; i < realmResult.size(); i++) {
			String name, number,address,latitude,longitude;
			int id = realmResult.get(i).getId();
            name = realmResult.get(i).getName();
            number = realmResult.get(i).getNumber();
            address = realmResult.get(i).getAddress();
            latitude = realmResult.get(i).getLatitude();
            longitude = realmResult.get(i).getLongitude();

			data.add(new DirectoryModel(id, name,number, address,latitude,longitude));
		}

		return data;
	}

	public DirectoryData getDirectory(int id)
	{
		DirectoryData result = realm.where(DirectoryData.class).equalTo("id", id).findFirst();
		return result;
	}

	/**
	 * method update data
	 *
     * @param name
     * @param address
     * @param number
     * @param latitude
     * @param longitude
	 */
	public void updateDirectory(int id, String name,String number, String address,String latitude,String longitude) {
		realm.beginTransaction();
		DirectoryData directoryData = realm.where(DirectoryData.class).equalTo("id", id).findFirst();
        directoryData.setName(name);
        directoryData.setNumber(number);
        directoryData.setAddress(address);
        directoryData.setLatitude(latitude);
        directoryData.setLongitude(longitude);
		realm.commitTransaction();
		showLog("Updated : " + name);
	}

	/**
	 * method delete data by id
	 *
	 * @param id
	 */
	public void deleteDirectory(final int id) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<DirectoryData> rows = realm.where(DirectoryData.class).equalTo("id",id).findAll();
                rows.clear();
            }
        });
	}

	/**
	 * create log
	 *
	 * @param s
	 */
	private void showLog(String s) {
		Log.d(TAG, s);
	}
}