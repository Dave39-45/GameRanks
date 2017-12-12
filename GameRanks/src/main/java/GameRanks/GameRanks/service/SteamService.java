package GameRanks.GameRanks.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
public class SteamService {
    public String getSteamDataForGame(String steamId){
        try {
            URL url = new URL("http://store.steampowered.com/api/appdetails?appids=" + steamId);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestProperty("Content-Type", "application/json");
            
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            
            in.close();
            con.disconnect();
            
            return content.toString();
        } catch (MalformedURLException ex) {} catch (IOException ex) {}
        
        return null;
    }
}
