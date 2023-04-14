package okhttp;

import com.google.gson.Gson;
import dto.AuthRequestDTO;
import dto.AuthResponseDTO;
import dto.ErrorDTO;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class LoginTestsOkhttp {

    private final MediaType JSON = MediaType.get("application/json;charset=utf-8");
    Gson gson= new Gson();
    OkHttpClient client = new OkHttpClient();

    @Test
    public void  loginSuccessTest() throws IOException {
        AuthRequestDTO auth = AuthRequestDTO.builder().username("tja@gm.de").password("tja@gmde2S!").build();
        RequestBody body= RequestBody.create(gson.toJson(auth), JSON);
        Request request = new Request.Builder().url("https://contactapp-telran-backend.herokuapp.com/v1/user/login/usernamepassword").post(body).build();

        Response response = client.newCall(request).execute();
        Assert.assertTrue(response.isSuccessful());
        Assert.assertEquals(response.code(),200);

        AuthResponseDTO responseDTO = gson.fromJson(response.body().string(), AuthResponseDTO.class);
        System.out.println(responseDTO.getToken());
    }
    @Test
    public void loginWithWrongEmailTest() throws IOException {

        AuthRequestDTO auth = AuthRequestDTO.builder()
                .username("tja@gmx.de")
                .password("tja@gmde2S!")
                .build();

        RequestBody body = RequestBody.create(gson.toJson(auth),JSON);

        Request request = new Request.Builder()
                .url("https://contactapp-telran-backend.herokuapp.com/v1/user/login/usernamepassword")
                .post(body).build();

        Response response = client.newCall(request).execute();
        Assert.assertFalse(response.isSuccessful());
        Assert.assertEquals(response.code(),401);

        ErrorDTO errorDto = gson.fromJson(response.body().string(), ErrorDTO.class);
        System.out.println(errorDto.getMessage());

        Assert.assertEquals(errorDto.getError(),"Unauthorized");
        Assert.assertEquals(errorDto.getMessage(),"Login or Password incorrect");
    }

    //eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoidGphQGdtLmRlIiwiaXNzIjoiUmVndWxhaXQiLCJleHAiOjE2ODIxMDc2NjEsImlhdCI6MTY4MTUwNzY2MX0.fAoprt4kwO6tq2TzE1VUHn5f6EsAPeFi-8c8mospCnw

}
