import Classes.Listing.Item.*;
import Classes.Template.Category;
import Classes.Template.Subcategory;
import Classes.Template.Template;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class Methods {
    private static String dataServiceBaseUrl = "https://localhost:44321/";
    public List<Listing> GetAllListings() throws IOException{
        CloseableHttpClient httpclient = HttpClients.createDefault();
        var url = dataServiceBaseUrl+"api/ListingsWeb/GetAllListings";
        HttpGet httpGet = new HttpGet(url);
        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
            HttpEntity entity = response.getEntity();
            var json = EntityUtils.toString(entity);
            Type ListingListType = new TypeToken<List<Listing>>(){}.getType();
            List<Listing> Listings = new Gson().fromJson(json, ListingListType);
            EntityUtils.consume(entity);
            return Listings;
        } catch (Exception ex) {
            System.out.println("Unable to complete operation, error: " + ex.toString());
            return null;
        }
    }

    public Listing GetListingById(int id) throws IOException{
        CloseableHttpClient httpclient = HttpClients.createDefault();
        var url = dataServiceBaseUrl+"api/ListingsWeb/GetListing/"+id;
        HttpGet httpGet = new HttpGet(url);
        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
            HttpEntity entity = response.getEntity();
            var json = EntityUtils.toString(entity);
            Type ListingType = new TypeToken<Listing>(){}.getType();
            Listing Listing = new Gson().fromJson(json, ListingType);
            EntityUtils.consume(entity);
            return Listing;
        } catch (Exception ex) {
            System.out.println("Unable to complete operation, error: " + ex.toString());
            return null;
        }
    }

    public String PostListing(Listing listing) throws IOException{
        CloseableHttpClient httpclient = HttpClients.createDefault();
        var url = dataServiceBaseUrl+"api/ListingsWeb/SaveListing";
        HttpPost httpPost = new HttpPost(url);
        Gson gson = new Gson();
        httpPost.setEntity(new StringEntity(gson.toJson(listing)));
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");
        try (CloseableHttpResponse response = httpclient.execute(httpPost)) {
            HttpEntity entity = response.getEntity();
            var json = EntityUtils.toString(entity);
            EntityUtils.consume(entity);
            return json;
        } catch (Exception ex) {
            System.out.println("Unable to complete operation, error: " + ex.toString());
            return null;
        }
    }
    public void PutListing(Listing listing) throws IOException{
        CloseableHttpClient httpclient = HttpClients.createDefault();
        var url =dataServiceBaseUrl+"api/ListingsWeb/UpdateListing";
        HttpPut httpPut = new HttpPut(url);
        Gson gson = new Gson();
        httpPut.setEntity(new StringEntity(gson.toJson(listing)));
        httpPut.setHeader("Accept", "application/json");
        httpPut.setHeader("Content-type", "application/json");
        try {
            httpclient.execute(httpPut);
        } catch (Exception ex) {
            System.out.println("Unable to complete operation, error: " + ex.toString());
        }
    }

    public List<Category> GetCategories() throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        var url = "https://categorydataservice.azurewebsites.net/api/Category/GetAllCategories";
        HttpGet httpGet = new HttpGet(url);
        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
            HttpEntity entity = response.getEntity();
            var json = EntityUtils.toString(entity);
            Type CategoryListType = new TypeToken<List<Category>>(){}.getType();
            List<Category> categories = new Gson().fromJson(json, CategoryListType);
            EntityUtils.consume(entity);

            return categories;
        } catch (Exception ex) {
            System.out.println("Unable to complete operation, error: " + ex.toString());
            return null;
        }
    }

    public List<Subcategory> GetSubcategoriesByCategoryId(long categoryId) throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        var url = String.format("https://categorydataservice.azurewebsites.net/api/Category/GetSubcategoriesByCategoryId?categoryId=%d", categoryId);
        HttpGet httpGet = new HttpGet(url);
        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
            HttpEntity entity = response.getEntity();
            var json = EntityUtils.toString(entity);
            Type SubcategoryListType = new TypeToken<List<Subcategory>>(){}.getType();
            List<Subcategory> subcategories = new Gson().fromJson(json, SubcategoryListType);
            EntityUtils.consume(entity);

            return subcategories;
        } catch (Exception ex) {
            System.out.println("Unable to complete operation, error: " + ex.toString());
            return null;
        }
    }

    public Template GetTemplateBySubcategoryId(long subcategoryId) throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        var url = String.format("https://categorydataservice.azurewebsites.net/api/Category/GetTemplateBySubcategoryId?subcategoryId=%d", subcategoryId);
        HttpGet httpGet = new HttpGet(url);
        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
            HttpEntity entity = response.getEntity();
            var json = EntityUtils.toString(entity);
            Template templates = new Gson().fromJson(json, Template.class);
            EntityUtils.consume(entity);

            return templates;
        } catch (Exception ex) {
            System.out.println("Unable to complete operation, error: " + ex.toString());
            return null;
        }
    }
}
