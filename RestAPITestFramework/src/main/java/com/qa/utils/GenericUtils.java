package com.qa.utils;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.restassured.response.Response;

public class GenericUtils {

	public static ArrayList<String> getListOfDataFromAPIResponse(Response response, String firstName, String lastName, String email, String dateOfBirth){
		ArrayList<String> weatherList = null;
		try
		{	   
			weatherList = new ArrayList<String>();
			JSONObject jsonObject = new JSONObject(response.body().asString());
			JSONArray jsonArray = jsonObject.getJSONArray("data");	
			/*for(int i=0;i<numOfDays;i++)
			{
				JSONObject jsonObject1 = jsonArray.getJSONObject(i);
				JSONObject weather = jsonObject1.getJSONObject("weather");
				String weatherCondition = weather.optString("description");
				weatherList.add(weatherCondition);	
			}*/
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
		return weatherList;
	}
	
	public static String RandomString(int length)
	{
		String stringValue="ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvxyz";
		StringBuffer stringbuffer = null;
		try {
			stringbuffer=new StringBuffer(10);
			for(int i=0;i<length;i++){
				int index=(int)(stringValue.length()*Math.random());
				stringbuffer.append(stringValue.charAt(index));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stringbuffer.toString();
	}
	
}
