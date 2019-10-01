import Classes.Template.Category;
import Classes.Template.Subcategory;
import Classes.Template.Template;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class Methods {
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
