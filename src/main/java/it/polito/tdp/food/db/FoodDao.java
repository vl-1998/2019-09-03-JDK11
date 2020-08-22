package it.polito.tdp.food.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.food.model.Adiacenza;
import it.polito.tdp.food.model.Condiment;
import it.polito.tdp.food.model.Food;
import it.polito.tdp.food.model.Portion;

public class FoodDao {
	public List<Food> listAllFoods(){
		String sql = "SELECT * FROM food" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Food> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Food(res.getInt("food_code"),
							res.getString("display_name")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}

	}
	
	public List<Condiment> listAllCondiments(){
		String sql = "SELECT * FROM condiment" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Condiment> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Condiment(res.getInt("condiment_code"),
							res.getString("display_name"),
							res.getDouble("condiment_calories"), 
							res.getDouble("condiment_saturated_fats")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<String> listAllPortions(Integer calorie){
		String sql = "SELECT distinct portion_display_name\n" + 
				"from portion\n" + 
				"where calories<?" ;
		List<String> result = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, calorie);
			
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					String s = res.getString("portion_display_name");
					result.add(s);
					
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}
	
	public List<Adiacenza> getAdiacenze (){
		String sql = "select distinct p1.portion_display_name, p2.portion_display_name, count(*) as peso\n" + 
				"from portion p1, portion p2\n" + 
				"where p1.portion_display_name>p2.portion_display_name\n" +
				"and p1.food_code = p2.food_code\n" + 
				"group by p1.portion_display_name, p2.portion_display_name";
		List<Adiacenza> result = new ArrayList<>();
		
		try {
			Connection conn = DBConnect.getConnection() ;
			PreparedStatement st = conn.prepareStatement(sql) ;
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				Adiacenza a = new Adiacenza (res.getString("p1.portion_display_name"), 
						res.getString("p2.portion_display_name"),
						res.getInt("peso"));
					result.add(a);
				}
			
			conn.close();
			return result;
			
		}catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	

}
