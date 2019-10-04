package nearsoft.academy.bigdata.recommendation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

public class MovieRecommender {

    private ClassLoader loader = MovieRecommender.class.getClassLoader();
    private BufferedReader buffered;

    public MovieRecommender(String name) {
        try {
            this.buffered = this.getFileAsBufferedReader(name);
            this.processFile();
        } catch (Exception e) {
            //TODO: handle exception
        }

    }

    public void processFile() throws Exception{
        String line;
        while ((line = this.buffered.readLine()) != null) {
                System.out.println(line);
        }
    }

    public BufferedReader getFileAsBufferedReader(String name) throws Exception{
        File file = new File(loader.getResource(name).getFile());
        InputStream fileStream = new FileInputStream(file);
        InputStream gzipStream = new GZIPInputStream(fileStream);
        Reader decoder = new InputStreamReader(gzipStream, "UTF-8");
        BufferedReader buffered = new BufferedReader(decoder);
        return buffered;
    }
    public int getTotalReviews() {
        return 1;
    }

    public int getTotalProducts() {
        return 1;
    }

    public int getTotalUsers() {
        return 1;
    }

    public List<String> getRecommendationsForUser() {
        List<String> recommendations = new ArrayList<String>();


        return recommendations;
    }
}
