package com.papasfritas.chat_firebase.Fragments;

import com.papasfritas.chat_firebase.Notification.MyResponse;
import com.papasfritas.chat_firebase.Notification.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {

    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAX6dQ4K4:APA91bGbrcfpIyZdvKRNScFopXtVRLVy3A63p6-yXzcgtjObt5XlvOjCMZ_AvLAMSNE682VycrWRKF3XgoQevaEtvM5b0wrSREzzOF0SjMoHJXYZarf7qNmOSIA1ioc_oPzl4GAjbCwl"
            }
    )
    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);

}
