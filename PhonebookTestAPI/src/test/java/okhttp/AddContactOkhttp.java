package okhttp;

import com.google.gson.Gson;
import dto.ContactDTO;
import dto.ContactResponsDTO;
import dto.ErrorDTO;

import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Random;

public class AddContactOkhttp {
    private  final MediaType JSON = MediaType.get("application/json;charset=utf-8");
    Gson gson = new Gson();
    OkHttpClient client = new OkHttpClient();

    String token = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoidGphQGdtLmRlIiwiaXNzIjoiUmVndWxhaXQiLCJleHAiOjE2ODIxMDc2NjEsImlhdCI6MTY4MTUwNzY2MX0.fAoprt4kwO6tq2TzE1VUHn5f6EsAPeFi-8c8mospCnw";

    @Test
    public void addContactSuccessTest() throws IOException {
        int i = new Random().nextInt(1000)+1000;
        ContactDTO contactDto = ContactDTO.builder().name("Oliver").lastName("Kan")
                .email("kan"+i+"@gmail.com").phone("1234512345"+i)
                .address("Berlin").description("goalkeeper").build();

        RequestBody body = RequestBody.create(gson.toJson(contactDto),JSON);
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/contacts")
                .addHeader("Authorization", token)
                .post(body).build();

        Response response = client.newCall(request).execute();
        Assert.assertTrue(response.isSuccessful());

        ContactResponsDTO contactResponsDTO= gson.fromJson(response.body().string(), ContactResponsDTO.class);
        System.out.println(contactResponsDTO.getMessage());
        Assert.assertTrue(contactResponsDTO.getMessage().contains("Contact was added!"));


    }

    @Test
    public void addContactWithoutTest409() throws IOException {

        ContactDTO contactDto = ContactDTO.builder()

                .lastName("Kan")
                .email("kan@gmail.com")
                .phone("1234512345")
                .address("Berlin")
                .description("goalkeeper").build();

        RequestBody body = RequestBody.create(gson.toJson(contactDto),JSON);
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/contacts")
                .addHeader("Authorization", token)
                .post(body).build();

        Response response = client.newCall(request).execute();
        Assert.assertEquals(response.code(),409);

        ErrorDTO errorDTO=gson.fromJson(response.body().string(), ErrorDTO.class);
        System.out.println(errorDTO.getMessage().toString());
        Assert.assertEquals(errorDTO.getMessage().toString(), "{name=must not be blank}");
    }

    @Test
    public void addContactFrongEmaiTest400() throws IOException {

        ContactDTO contactDto = ContactDTO.builder()
                .name("Oliver")
                .lastName("Kan")
                .email("kangmail.com")
                .phone("1234512345")
                .address("Berlin")
                .description("goalkeeper").build();

        RequestBody body = RequestBody.create(gson.toJson(contactDto),JSON);
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/contacts")
                .addHeader("Authorization", token)
                .post(body).build();

        Response response = client.newCall(request).execute();
        Assert.assertEquals(response.code(),400);

        ErrorDTO errorDTO=gson.fromJson(response.body().string(), ErrorDTO.class);
        System.out.println(errorDTO.getMessage().toString());
        Assert.assertEquals(errorDTO.getMessage().toString(), "{email=must be a well-formed email address}");
    }

}
