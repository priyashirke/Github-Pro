package com.service.iscon.vcr.Controller;

import android.content.Context;
import android.text.TextUtils;

import com.service.iscon.vcr.Constants.WebServiceConstants;
import com.service.iscon.vcr.Helper.AsyncProcess;
import com.service.iscon.vcr.Helper.AsyncProcessListener;
import com.service.iscon.vcr.Model.UserInfo;
import com.service.iscon.vcr.Utils.NetUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by vkwb on 9/22/15.
 */
public class UserInfoController {

    public AsyncProcessListener<Object> getOnUserAuthentication() {
        return OnUserAuthentication;
    }

    public void setOnUserAuthentication(AsyncProcessListener<Object> onUserAuthentication) {
        OnUserAuthentication = onUserAuthentication;
    }


    public AsyncProcessListener<Object> getOnUserRegistration() {
        return OnUserRegistration;
    }

    public void setOnUserRegistration(AsyncProcessListener<Object> onUserRegistration) {
        OnUserRegistration = onUserRegistration;
    }

    /**********************************************
     * New Services
     *****************************************/

    private AsyncProcessListener<Object> OnUserAuthentication;
    private AsyncProcessListener<Object> OnUserRegistration;

    public static UserInfoController AuthenticateUser(final Context Ctx, final String email, final String password) {

        final UserInfoController UIC = new UserInfoController();

        if (!NetUtils.IsInternet(Ctx, true)) {
            if (UIC.getOnUserAuthentication() != null) {
                UIC.getOnUserAuthentication().ProcessFailed(new Exception("Internet Not Available"));
                return UIC;
            } else {
                return null;
            }
        }

        //Collecting Values to Pass
        final HashMap<String, String> HT = new HashMap<>();
        HT.put("email", email);
        HT.put("password", password);
        final String json = getJsonOf(HT);
        final UserInfo UI = new UserInfo();

        AsyncProcess AP = new AsyncProcess(Ctx, "Requesting Authentication") {
            @Override
            protected Object doInBackground(Object... params) {
                    //String Response1 = NetUtils.PostWebServiceMethodforDotNet(Ctx, WebServiceConstants.MobileAuthenticate, json);

                    // String Response2="{\"error\": {\"alert\": \"Enter Valid Mobile Number\"}}";
                    //String Response = "{\"error\":\"0\",\"user\": {\"name\": \"Aditya Anand\",\"email\": \""+email+"\",\"mobile\": \"9999999991\",\"last_login\": \"2017-02-12T13:26:16\"}}";
                    String Response = "{\"technicalStatus\":\"SUCCESS\",\"responseCode\":\"0\",\"tag\":\"login\",\"user\":{\"userId\":\"5\",\"name\":\"Aditya Anand\",\"createdDate\":\"2017-02-10T23:21:22\",\"lastLoginDate\":\"2017-02-12T13:26:16\",\"email\":\"er.aditya.anand@gmail.com\",\"password\":null,\"mobile\":\"9999999991\"}}";

                    JSONObject JO = null;
                    try {
                        JO = new JSONObject(Response);
                    } catch (Exception ex) {
                        return "INVALID SERVICE RESPONSE";
                    }

                    try {
                        String responseTag = JO.getString("tag");
                        if (!responseTag.equals("login")) {
                            return "INVALID SERVICE";
                        }
                        String technicalStatus = JO.getString("technicalStatus");
                        int responseCode = JO.getInt("responseCode");
                        if (technicalStatus.equals("SUCCESS")) {
                            switch (responseCode) {
                                case 0: //Login Success
                                    if (!JO.isNull("user")) {
                                        JSONObject JOUser = JO.getJSONObject("user");
                                        UserInfo UI = new UserInfo();

                                        if (!JOUser.isNull("userId")) {
                                            UI.setId(JOUser.getInt("userId"));
                                        }

                                        if (!JOUser.isNull("name")) {
                                            UI.setFullName(JOUser.getString("name"));
                                        }

                                        if (!JOUser.isNull("createdDate")) {
                                            UI.setCreatedDate(JOUser.getString("createdDate"));
                                        }
                                        if (!JOUser.isNull("lastLoginDate")) {
                                            UI.setLastLogin(JOUser.getString("lastLoginDate"));
                                        }

                                        if (!JOUser.isNull("email")) {
                                            UI.setEmail(JOUser.getString("email"));
                                        }
                                        if (!JOUser.isNull("mobile")) {
                                            UI.setMobile(JOUser.getString("mobile"));
                                        }

                                        if (!JOUser.isNull("password")) {
                                            UI.setPassword(JOUser.getString("password"));
                                        } else {
                                            UI.setMobile("");
                                        }

                                        return UI;
                                    }
                                    break;
                                case 1:
                                    return "Invalid Email or Password";
                                case 2:
                                    return "Email id not found";
                                case 11:
                                    return "Technical error found";
                            }
                            return "INVALID SERVICE RESPONSE";
                        }

                    } catch (Exception ex) {
                        return ex.getMessage();
                    }
                return "INVALID SERVICE RESPONSE";
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                if (UIC.getOnUserAuthentication() != null) {
                    if (o.getClass() == UserInfo.class) {
                        UIC.getOnUserAuthentication().ProcessFinished(o);
                    } else {
                        UIC.getOnUserAuthentication().ProcessFailed(new Exception(o.toString()));
                    }
                }
            }
        };
        AP.execute();
        return UIC;
    }

    public static UserInfoController RegisterUser(final Context Ctx, final UserInfo UI) {

        final UserInfoController UIC = new UserInfoController();

        if (!NetUtils.IsInternet(Ctx, true)) {
            if (UIC.getOnUserRegistration() != null) {
                UIC.getOnUserRegistration().ProcessFailed(new Exception("Internet Not Available"));
                return UIC;
            } else {
                return null;
            }
        }

        //Collecting Values to Pass
        final HashMap<String, String> HT = new HashMap<>();
        HT.put("email", UI.getEmail());
        HT.put("password", UI.getPassword());
        if(!TextUtils.isEmpty(UI.getMobile()) )
            HT.put("mobile", UI.getMobile());
        if(!TextUtils.isEmpty(UI.getFullName()))
            HT.put("name", UI.getFullName());
        final String json = getJsonOf(HT);

        //final UserInfo UI = new UserInfo();

        AsyncProcess AP = new AsyncProcess(Ctx, "Requesting Authentication") {
            @Override
            protected Object doInBackground(Object... params) {
                    //String Response1 = NetUtils.PostWebServiceMethodforDotNet(Ctx, WebServiceConstants.MobileAuthenticate, json);

                    // String Response2="{\"error\": {\"alert\": \"Enter Valid Mobile Number\"}}";
                    //String Response = "{\"error\":\"0\",\"user\": {\"name\": \"Shrinath Tamada\",\"email\": \"shri@gmail.com\",\"mobile\": \"9898989898\",\"last_login\": \"2015-09-17 13:26:16\"}}";
                    String Response = "{\"technicalStatus\":\"SUCCESS\",\"responseCode\":\"0\",\"tag\":\"registration\",\"user\":{\"userId\":\"5\",\"name\":\""+UI.getFullName()+"\",\"createdDate\":\"2017-02-10T23:21:22\",\"lastLoginDate\":\"2017-02-12T13:26:16\",\"email\":\""+UI.getEmail()+"\",\"password\":null,\"mobile\":\""+UI.getMobile()+"\"}}";

                    JSONObject JO = null;
                    try {
                        JO = new JSONObject(Response);
                    } catch (Exception ex) {
                        return "INVALID SERVICE RESPONSE";
                    }

                    try {
                        String responseTag = JO.getString("tag");
                        if (!responseTag.equals("registration")) {
                            return "INVALID SERVICE";
                        }
                        String technicalStatus = JO.getString("technicalStatus");
                        int responseCode = JO.getInt("responseCode");
                        if (technicalStatus.equals("SUCCESS")) {
                            switch (responseCode) {
                                case 0: //Login Success
                                    if (!JO.isNull("user")) {
                                        JSONObject JOUser = JO.getJSONObject("user");
                                        UserInfo UI = new UserInfo();

                                        if (!JOUser.isNull("userId")) {
                                            UI.setId(JOUser.getInt("userId"));
                                        }

                                        if (!JOUser.isNull("name")) {
                                            UI.setFullName(JOUser.getString("name"));
                                        }

                                        if (!JOUser.isNull("createdDate")) {
                                            UI.setCreatedDate(JOUser.getString("createdDate"));
                                        }
                                        if (!JOUser.isNull("lastLoginDate")) {
                                            UI.setLastLogin(JOUser.getString("lastLoginDate"));
                                        }

                                        if (!JOUser.isNull("email")) {
                                            UI.setEmail(JOUser.getString("email"));
                                        }
                                        if (!JOUser.isNull("mobile")) {
                                            UI.setMobile(JOUser.getString("mobile"));
                                        }

                                        if (!JOUser.isNull("password")) {
                                            UI.setPassword(JOUser.getString("password"));
                                        } else {
                                            UI.setMobile("");
                                        }

                                        return UI;
                                    }
                                    break;
                                case 1:
                                    return "Invalid Email or Password";
                                case 2:
                                    return "Email id not found";
                                case 11:
                                    return "Technical error found";
                            }
                            return "INVALID SERVICE RESPONSE";
                        }

                    } catch (Exception ex) {
                        return ex.getMessage();
                    }
                    return "INVALID SERVICE RESPONSE";
                }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                if (UIC.getOnUserRegistration() != null) {
                    if (o.getClass() == UserInfo.class) {
                        UIC.getOnUserRegistration().ProcessFinished(o);
                    } else {
                        UIC.getOnUserRegistration().ProcessFailed(new Exception(o.toString()));
                    }
                }
            }
        };
        AP.execute();
        return UIC;
    }

    private static String getJsonOf(HashMap<String, String> ht) {

        JSONObject JORequest = new JSONObject();
        for (Map.Entry<String, String> entry : ht.entrySet()) {
            try {
                JORequest.put(entry.getKey(), entry.getValue());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return JORequest.toString();
    }
}