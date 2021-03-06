package com.dave.fantasyfootball.scanner;

import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import com.dave.fantasyfootball.repository.PlayerRepositoryImpl;

public class PlayerScanner {

	public static void main(String[] args) throws JSONException, IOException {
		PlayerRepositoryImpl playerRepository = new PlayerRepositoryImpl();
		FileWriter writer = new FileWriter("C:\\Users\\Dave\\Dropbox\\HFL\\FplRoster201516_300_23102015_2.csv");
		writer.append("ID");
		writer.append(',');
		writer.append("First Name");
		writer.append(',');
		writer.append("Last Name");
		writer.append(',');
		writer.append("Web Name");
		writer.append(',');
		writer.append("Team");
		writer.append(',');
		writer.append("Position");
		writer.append('\n');

		for (int i = 300; i < 619; i++) {
			JSONObject playerJson;
			try {
				System.out.println("Getting player " + i);
				playerJson = playerRepository.getPlayerJson(i);
				writer.append(String.valueOf(i));
				writer.append(',');
				writer.append(playerJson.getString("first_name"));
				writer.append(',');
				writer.append(playerJson.getString("second_name"));
				writer.append(',');
				writer.append(playerJson.getString("web_name"));
				writer.append(',');
				writer.append(playerJson.getString("team_name"));
				writer.append(',');
				writer.append(playerJson.getString("type_name"));
				writer.append('\n');
			} catch (IOException e) {
				System.out.println("Missing player ID: " + (i));
			}
		}
		writer.close();
	}
}
