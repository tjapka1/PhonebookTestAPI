package okhttp;

import com.google.gson.Gson;
import dto.ContactDTO;
import dto.ContactResponsDTO;
import dto.ErrorDTO;
import lombok.SneakyThrows;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Random;

public class DeleteContactByIdTestsOkhttp {
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
    @Test
    public void deleteContactByIdTest() throws IOException {
        Request request= new Request.Builder().url("https://contactapp-telran-backend.herokuapp.com/v1/contacts/"+id)
                .addHeader("Authorization", token )
                .delete()
                .build();

        Response response=client.newCall(request).execute();
        Assert.assertTrue(response.isSuccessful());

        ContactResponsDTO contactResponsDTO= gson.fromJson(response.body().string(), ContactResponsDTO.class);

        Assert.assertEquals(contactResponsDTO.getMessage().toString(), "Contact was deleted!");

    }
}
