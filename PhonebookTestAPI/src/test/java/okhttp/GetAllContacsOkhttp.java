package okhttp;

import com.google.gson.Gson;
import dto.AllContacsDTO;
import dto.ContactDTO;
import dto.ErrorDTO;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

public class GetAllContacsOkhttp {
    Gson gson =new Gson();
    OkHttpClient client = new OkHttpClient();
    String token ="eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoidGphQGdtLmRlIiwiaXNzIjoiUmVndWxhaXQiLCJleHAiOjE2ODIxMDc2NjEsImlhdCI6MTY4MTUwNzY2MX0.fAoprt4kwO6tq2TzE1VUHn5f6EsAPeFi-8c8mospCnw";

    @Test
    public void getAllContactSuccess() throws IOException {

        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/contacts")
                .addHeader("Authorization",token)
                .get().build();

        Response response = client.newCall(request).execute();

        Assert.assertTrue(response.isSuccessful());

        AllContacsDTO allContacsDTO=gson.fromJson(response.body().string(), AllContacsDTO.class);

        List <ContactDTO> contacts = allContacsDTO.getContacts();

        for (ContactDTO contact:contacts){
            System.out.println(contact.getId());
            System.out.println(contact.getName() + " " + contact.getLastName());

            System.out.println("==================================================");
        }
        }



    @Test
    public void getAllContactNegative() throws IOException {

        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/contacts")
                .addHeader("Authorization","dfghj")
                .get().build();

        Response response = client.newCall(request).execute();

        Assert.assertEquals(response.code(),401);

        ErrorDTO errorDTO =gson.fromJson(response.body().string(), ErrorDTO.class);

        Assert.assertEquals(errorDTO.getMessage().toString(),"JWT strings must contain exactly 2 period characters. Found: 0");
        Assert.assertEquals(errorDTO.getError(), "Unauthorized");
    }


}

