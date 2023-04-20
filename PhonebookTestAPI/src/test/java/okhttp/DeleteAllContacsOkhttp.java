package okhttp;

import com.google.gson.Gson;
import dto.AllContacsDTO;
import dto.ContactDTO;
import dto.ContactResponsDTO;
import dto.ErrorDTO;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

public class DeleteAllContacsOkhttp {
    Gson gson =new Gson();
    OkHttpClient client = new OkHttpClient();
    String token ="eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoidGphQGdtLmRlIiwiaXNzIjoiUmVndWxhaXQiLCJleHAiOjE2ODIxMDc2NjEsImlhdCI6MTY4MTUwNzY2MX0.fAoprt4kwO6tq2TzE1VUHn5f6EsAPeFi-8c8mospCnw";

    @Test
    public void deleteAllContactSuccess() throws IOException {

        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/contacts/clear")
                .addHeader("Authorization",token)
                .delete().build();

        Response response = client.newCall(request).execute();

        Assert.assertTrue(response.isSuccessful());
        Assert.assertEquals(response.code(), 200);

        ContactResponsDTO contactResponsDTO= gson.fromJson(response.body().string(), ContactResponsDTO.class);



        Assert.assertEquals(contactResponsDTO.getMessage(),"All contacts was deleted!");
        System.out.println(contactResponsDTO.getMessage());
        }
    }
