package nearsoft.academy.bigdata.recommendation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.BidiMap;
import org.apache.commons.collections.bidimap.DualHashBidiMap;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

public class MovieRecommender {
    BidiMap users = new DualHashBidiMap();
    BidiMap products = new DualHashBidiMap();
    DataModel model;
    int reviewsCount = 0;

    public MovieRecommender(String path) {
        // File from the resources folder 
        File sourceFile = new File(MovieRecommender.class.getClassLoader().getResource(path).getFile());


        try {
            String productId = null;
            String userId = null;
            String score = null;

            int userCount = 0;
            int productCount = 0;

            File csvFile = new File("data.csv");
            BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile));
            LineIterator it;

            it = FileUtils.lineIterator(file, "UTF-8");

            while (it.hasNext()) {
                String currentLine = it.nextLine();

                if (currentLine.startsWith("product/productId: ")) {
                    productId = currentLine.substring(19);
                    if (!products.containsKey(productId)){
                        products.put(productId, productCount);
                        productCount++;
                    }
                }
                if (currentLine.startsWith("review/userId: ")) {
                    userId = currentLine.substring(15);
                    if(!users.containsKey(userId)){    
                        users.put(userId, userCount);
                        userCount++;
                    }
                }
                if (currentLine.startsWith("review/score")) {
                    score = currentLine.substring(14);
                    reviewsCount++;
                    writer.write(users.get(userId) + "," + products.get(productId)  + "," + score + " \n");
                }
            }

            writer.close();
            it.close();

            this.model = new FileDataModel(csvFile);

            } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        
    }
    
   public int getTotalReviews(){
     return this.reviewsCount;
   }

   public int getTotalProducts(){
        try {
            return this.model.getNumItems();
        } catch (TasteException e) {
            e.printStackTrace();
        }
        return 0;
  }

  public int getTotalUsers(){
    try {
        return this.model.getNumUsers();
    } catch (TasteException e) {
        e.printStackTrace();
    }
    return 0;
}

    public List<String> getRecommendationsForUser(String user){

            try {
                UserSimilarity similarity = new PearsonCorrelationSimilarity(this.model);
                UserNeighborhood neighborhood = new ThresholdUserNeighborhood(0.1, similarity, this.model);
                UserBasedRecommender recommender = new GenericUserBasedRecommender(this.model, neighborhood, similarity);
                List<String> recommendations = new ArrayList<String>();
                for (RecommendedItem recommendation : recommender.recommend(Long.parseLong(users.get(user).toString()), 3)) {
                    recommendations.add(products.inverseBidiMap().get((int)recommendation.getItemID()).toString());
                }
                return recommendations;
                
            } catch (TasteException e) {
                System.out.print(e.getMessage());
                e.printStackTrace();
            }
        return null;
    }
    
}
