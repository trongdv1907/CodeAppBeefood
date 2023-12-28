package com.Users.beefood.retrofit;



import com.Users.beefood.model.NotiResponse;
import com.Users.beefood.model.NotisendData;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiPushNotification {
    @Headers({
            "Content-Type: application/json",
           "Authorization: key=AAAArsnBtH4:APA91bGmnEMurB4G75DASQqFMygSS-GNXIssqZcLHShotMURU7xPhJIcoPL8IuPJZPZglfC4yY8Nt5UPj1arBA3XGWdq8d1oelV0lLy_nB_DM_pWNT193sfUGgaQa76YnN2yEQ83_DcA"
    })

    @POST("fcm/send")
    Observable<NotiResponse> sendNotification(@Body NotisendData data);
}