package com.android.poinku.service.notif;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAcGKeTO0:APA91bHXimdeYIAvYgPPRU9qP3fDl2nZWt1C6Y9f5JaspVqQqOk9vlCl5kXom0hC-XiqmLgmau6L3Gg_KX7XPQah-UbKBzgaFXrEFSSdvWz6EM_Gj2QKkDxCSIg_K8xX2QZbyMOz0sBR" // Your server key refer to video for finding your server key
            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotifcation(@Body NotificationSender body);
}