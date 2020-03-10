package com.example.zexplore.model.dao;

import com.example.zexplore.model.AccountClass;
import com.example.zexplore.model.City;
import com.example.zexplore.model.Country;
import com.example.zexplore.model.Occupation;
import com.example.zexplore.model.State;
import com.example.zexplore.model.Title;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import io.reactivex.Single;

@Dao
public interface SpinnerDao {
    // Get all countries
    @Query("SELECT * FROM Country")
    Single<List<Country>> getCountries();

    // Insert all countries
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCountries(List<Country> countryList);

    // Delete all countries
    @Query("DELETE FROM Country")
    void deleteCountries();

    // Get all cities
    @Query("SELECT * FROM City")
    Single<List<City>> getCities();

    // Insert all cities
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCities(List<City> cityList);

    // Delete all cities
    @Query("DELETE FROM City")
    void deleteCities();

    // Get all states
    @Query("SELECT * FROM State")
    Single<List<State>> getStates();

    // Insert all states
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertStates(List<State> stateList);

    // Delete all states
    @Query("DELETE FROM State")
    void deleteStates();

    // Get all titles
    @Query("SELECT * FROM Title")
    Single<List<Title>> getTitles();

    // Insert all titles
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTitles(List<Title> titleList);

    // Delete all title
    @Query("DELETE FROM Title")
    void deleteTitles();

    // Get all occupation
    @Query("SELECT * FROM Occupation")
    Single<List<Occupation>> getOccupations();

    // Insert all occupation
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOccupations(List<Occupation> occupationList);

    // Delete all occupation
    @Query("DELETE FROM Occupation")
    void deleteOccupations();

    //Get all account class
    @Query("SELECT * FROM AccountClass")
    Single<List<AccountClass>> getAccountClass();

//    Get all account class
//    @Query("SELECT * FROM AccountClass WHERE ClassType=:classType ORDER BY Description" )
//    Single<List<AccountClass>> getAccountClassByType(String classType);

    //Insert all account class
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAccountClass(List<AccountClass> accountClassList);

    // Delete all account class
    @Query("DELETE FROM AccountClass")
    void deleteAccountClass();
}
