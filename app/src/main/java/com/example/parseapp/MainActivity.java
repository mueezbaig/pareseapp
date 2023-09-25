package com.example.parseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class MainActivity extends AppCompatActivity {

    TextView display;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        display=(TextView) findViewById(R.id.display);
    }

    public void parsexml(View v)
    {
       try
       {
           InputStream is = getAssets().open("city.xml");
           DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
           DocumentBuilder db= dbf.newDocumentBuilder();
           Document doc = db.parse(is);
           StringBuilder sb = new StringBuilder();
           sb.append("XML Data");
           sb.append("\n------");

           NodeList n = doc.getElementsByTagName("place");

           for(int i = 0;i<n.getLength();i++)
           {
               Node node = n.item(i);

               if(node.getNodeType()==Node.ELEMENT_NODE)
               {
                   Element element=(Element) node;
                   sb.append("\n Name:").append(getValue("name",element));
                   sb.append("\n Latitude:").append(getValue("lat",element));
                   sb.append("\n Longitude:").append(getValue("long",element));
                   sb.append("\n Temperature:").append(getValue("temp",element));
                   sb.append("\n Humidity:").append(getValue("humidity",element));
                   sb.append("\n-----------");

               }
           }
           display.setText(sb.toString());
       }
       catch (Exception e)
       {
           e.printStackTrace();
           Toast.makeText(MainActivity.this,"Error in reading XML",Toast.LENGTH_LONG).show();
       }
    }
    public void parsejson(View v)
    {
        String json;
        StringBuilder sb = new StringBuilder();

        try
        {
            InputStream is = getAssets().open("city.json");
            int size = is.available();
            byte[] buffer= new byte[size];
            is.read(buffer);
            sb.append("Json Data:");
            sb.append("\n ---------");
            json = new String(buffer, StandardCharsets.UTF_8);
            JSONArray n = new JSONArray(json);

            for(int i =0;i<n.length();i++)
            {
                JSONObject jsonObject=n.getJSONObject(i);
                sb.append("\n Name:").append(jsonObject.getString("name"));
                sb.append("\n Latitude:").append(jsonObject.getString("lat"));
                sb.append("\n Longitude:").append(jsonObject.getString("long"));
                sb.append("\n Temperature:").append(jsonObject.getString("temp"));
                sb.append("\n Humidity:").append(jsonObject.getString("humidity"));
                sb.append("\n-----------");
            }
            display.setText(sb.toString());
            is.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Toast.makeText(MainActivity.this,"Error in reading Json",Toast.LENGTH_LONG).show();
        }
    }
    private String getValue(String tag, Element element)
    {
        return element.getElementsByTagName(tag).item(0).getChildNodes().item(0).getNodeValue();
    }
}