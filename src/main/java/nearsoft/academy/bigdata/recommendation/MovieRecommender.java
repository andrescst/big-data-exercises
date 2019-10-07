package nearsoft.academy.bigdata.recommendation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang.ArrayUtils;
import org.apache.mahout.cf.taste.impl.common.FastByIDMap;
import org.apache.mahout.cf.taste.impl.model.GenericPreference;
import org.apache.mahout.cf.taste.impl.model.GenericUserPreferenceArray;
import org.apache.mahout.cf.taste.model.Preference;
import org.apache.mahout.cf.taste.model.PreferenceArray;


public class MovieRecommender {
    private List<String> users = new LinkedList<String>();
    private List<String> products = new LinkedList<String>();
    private List<String> scores = new LinkedList<String>();
    Map<String, List<String>> reviews = new HashMap<String,List<String>>();
    public MovieRecommender(String path){
        readFile(path);

    }

    public void readFile(String path) {
        
        File file = new File(MovieRecommender.class.getClassLoader().getResource(path).getFile());
        String productId;
        String userId;
        String score;


        LineIterator it;
        try {
            it = FileUtils.lineIterator(file, "UTF-8");

            FastByIDMap<Collection<Preference>> rawData = new FastByIDMap<Collection<Preference>>();
            while (it.hasNext()) {
                List<String> review = new ArrayList<String>();
                
                    productId = it.nextLine().substring(19);
                    
                    this.products.add(productId);
                    review.add(productId);
                    if(!reviews.containsKey(userId)) {
                        reviews.put(userId,review);
                       }
                       else {
                        reviews.get(userId).addAll(review);
                       }
                
              

              System.out.println(rawData.size());
              it.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        

        
    }

    public int getTotalReviews(){
        System.out.println("Reviews: " + this.users.size());
        return this.users.size();
    }

    public int getTotalUsers(){
        
        List<String> listWithoutDuplicates = new ArrayList<String>(
      new HashSet<String>(this.users));
        System.out.println("Users: " + listWithoutDuplicates.size());
        return listWithoutDuplicates.size();
    }

    public int getTotalProducts(){
        
        List<String> listWithoutDuplicates = new ArrayList<String>(
      new HashSet<String>(this.products));
      System.out.println("Products: " + listWithoutDuplicates.size());
        return listWithoutDuplicates.size();
    }

 
   
    
}
