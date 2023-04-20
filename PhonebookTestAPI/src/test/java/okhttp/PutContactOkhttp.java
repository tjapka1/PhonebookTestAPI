package okhttp;

import com.google.gson.Gson;
import dto.AllContacsDTO;
import dto.ContactDTO;
import dto.ContactResponsDTO;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.Random;

public class PutContactOkhttp {
    private  final MediaType JSON = MediaType.get("application/json;charset=utf-8");
    Gson gson =new Gson();
    OkHttpClient client = new OkHttpClient();
    String token ="eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoidGphQGdtLmRlIiwiaXNzIjoiUmVndWxhaXQiLCJleHAiOjE2ODIxMDc2NjEsImlhdCI6MTY4MTUwNzY2MX0.fAoprt4kwO6tq2TzE1VUHn5f6EsAPeFi-8c8mospCnw";
    String id;

    @BeforeMethod
    public void precondition() throws IOException {
        int i = new Random().nextInt(1000)+1000;
        ContactDTO contactDto = ContactDTO.builder()
                .name("Oliver")
                .lastName("Kan")
                .email("kan"+i+"@gmail.com")
                .phone("1234512345"+i)
                .address("Berlin")
                .description("goalkeeper")
                .build();

        RequestBody body = RequestBody.create(gson.toJson(contactDto), JSON);
        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/contacts")
                .addHeader("Authorization", token)
                .post(body).build();

        Response response = client.newCall(request).execute();

        ContactResponsDTO contactResponsDTO = gson.fromJson(response.body().string(), ContactResponsDTO.class);


        String msg= contactResponsDTO.getMessage();
        String[] split=msg.split(": ");
        id =split[1];
    }

/*
    @Test
    public void putContactNameByIdSuccess() throws IOException {


        String chanchNane = "Alex";

        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/contacts" + id)
                .addHeader("Authorization",token)
                .put(

                ).build();

        Response response = client.newCall(request).execute();

        Assert.assertTrue(response.isSuccessful());

        ContactDTO contactDTO=gson.fromJson(response.body().string(), ContactDTO.class);
        System.out.println(contactDTO.getName());

        }

 */
    }

