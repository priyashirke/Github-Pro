package com.service.iscon.vcr.Constants;

/**
 * Created by vkwb on 9/22/15.
 */

public final class WebServiceConstants {

    /**
     * Change following ENVIRONMENT Constant before publish the APP
     * <p>
     * 1 = LIVE
     * 2 = UAT
     * 3 = PREUAT
     */


//  public int ENVIRONMENT=2;   //1-LIVE  2-UAT

  /*
    Base Service URL constants
    */

    //  public static String BaseUrl="http://50.62.149.120/";
    public static String BaseUrl = "http://goappline.com/";



    //UAT server

    public static String ServiceUrl = BaseUrl+"applinewebuat/index.php?/webservices/";
    public static String ServiceProductThumbUrl = BaseUrl+"applinenetuat/img/product/thumb/";
    public static String ServiceProductFullUrl= BaseUrl+"applinenetuat/img/product/";
    public static String ServiceAttachmentThumbUrl = BaseUrl+"applinenetuat/Resources/img/order_attachment/thumb/";
    public static String ServiceAttachmentFullUrl= BaseUrl+"applinenetuat/Resources/img/order_attachment/";
    public static String ServiceProductThumbUrl_backup = BaseUrl+"applinenetuat/Resources/img/product/thumb/";
    public static String ServiceProductFullUrl_backup= BaseUrl+"applinenetuat/Resources/img/product/";



    /********************************
     * DOTNET API
     ******************************/


    //UAT
    public static String NetServiceUrl = BaseUrl+"applinedotnetweb/api/customer/";

    //LIVE
    //public static String NetServiceUrl = BaseUrl + "applinedotnetlive/api/customer/";

    public static String MobileAuthenticate = NetServiceUrl + "authenticate_mobile";
    public static String OTPAuthenticate = NetServiceUrl + "authenticate_otp";
    public static String RefreshHome = NetServiceUrl + "refresh_home";
    public static String PromotionList = NetServiceUrl + "get_promotions";
    public static String SearchVendor = NetServiceUrl + "search_vendor";

    /****************************************
     * FCM
     ************************************/
    //UAT
    public static String Token_Registration_Service= BaseUrl + "applinedotnetweb/api/vender/token_registration_service";

    //LIVE
    //public static String Token_Registration_Service = BaseUrl + "applinedotnetlive/api/vender/token_registration_service";
}