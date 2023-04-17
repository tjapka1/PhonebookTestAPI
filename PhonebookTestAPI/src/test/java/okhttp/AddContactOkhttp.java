package okhttp;

import com.google.gson.Gson;
import dto.ContactDTO;
import dto.ContactResponsDTO;
import dto.ErrorDTO;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
public class AddContactOkhttp {
    private  final MediaType JSON = MediaType.get("application/json;charset=utf-8");
    Gson gson = new Gson();
    OkHttpClient client = new OkHttpClient();

    String token = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoidGphQGdtLmRlIiwiaXNzIjoiUmVndWxhaXQiLCJleHAiOjE2ODIxMDc2NjEsImlhdCI6MTY4MTUwNzY2MX0.fAoprt4kwO6tq2TzE1VUHn5f6EsAPeFi-8c8mospCnw";

    @Test
    public void addContactSuccessTest() throws IOException {

        ContactDTO contactDto = ContactDTO.builder().name("Oliver").lastName("Kan")
                .email("kan@gmail.com").phone("1234512345")
                .address("Berlin").description("goalkeeper").build();

        RequestBody body = RequestBody.create(gson.toJson(contactDto),JSON);
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/contacts")
                .addHeader("Authorization", token)
                .post(body).build();

        Response response = client.newCall(request).execute();
        Assert.assertTrue(response.isSuccessful());

        ContactResponsDTO contactResponsDTO= gson.fromJson(response.body().string(), ContactResponsDTO.class);
        System.out.println(contactResponsDTO);

    }

    @Test
    public void addContactSuccessNegativTest() throws IOException {

        ContactDTO contactDto = ContactDTO.builder().name("wer").lastName("Kan")
                .email("kan@gmail.com").phone("1234512345")
                .address("Berlin").description("goalkeeper").build();

        RequestBody body = RequestBody.create(gson.toJson(contactDto),JSON);
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/contacts")
                .addHeader("Authorization", token)
                .post(body).build();

        Response response = client.newCall(request).execute();
        Assert.assertTrue(response.isSuccessful());

        ErrorDTO errorDTO = gson.fromJson(response.body().string(), ErrorDTO.class);
        System.out.println(errorDTO.getMessage());

        Assert.assertEquals(errorDTO.getError(), "Emty Name");

    }

    @Test
    public void addContactSuccessNegEmailTest() throws IOException {

        ContactDTO contactDto = ContactDTO.builder().name("wer").lastName("Kan")
                .email("kangmail.com").phone("1234512345")
                .address("Berlin").description("goalkeeper").build();

        RequestBody body = RequestBody.create(gson.toJson(contactDto),JSON);
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/contacts")
                .addHeader("Authorization", token)
                .post(body).build();

        Response response = client.newCall(request).execute();
        Assert.assertTrue(response.isSuccessful());

        ErrorDTO errorDTO = gson.fromJson(response.body().string(), ErrorDTO.class);
        System.out.println(errorDTO.getMessage());

        Assert.assertEquals(errorDTO.getError(), "Emty Name");

    }
}
