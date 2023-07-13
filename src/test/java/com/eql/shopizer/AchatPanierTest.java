package com.eql.shopizer;

import java.time.Duration;
import java.util.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import io.gatling.javaapi.jdbc.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import static io.gatling.javaapi.jdbc.JdbcDsl.*;

public class AchatPanierTest extends Simulation {

  {
    HttpProtocolBuilder httpProtocol = http
      .baseUrl("http://192.168.1.11:8080")
      .inferHtmlResources(AllowList(), DenyList(".*\\.js", ".*\\.css", ".*\\.gif", ".*\\.jpeg", ".*\\.jpg", ".*\\.ico", ".*\\.woff", ".*\\.woff2", ".*\\.(t|o)tf", ".*\\.png", ".*\\.svg", ".*detectportal\\.firefox\\.com.*"))
      .acceptHeader("*/*")
      .acceptEncodingHeader("gzip, deflate")
      .acceptLanguageHeader("fr,fr-FR;q=0.8,en-US;q=0.5,en;q=0.3")
      .userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:109.0) Gecko/20100101 Firefox/115.0");
    
    Map<CharSequence, String> headers_0 = new HashMap<>();
    headers_0.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8");
    headers_0.put("Upgrade-Insecure-Requests", "1");
    
    Map<CharSequence, String> headers_2 = new HashMap<>();
    headers_2.put("X-Requested-With", "XMLHttpRequest");
    
    Map<CharSequence, String> headers_5 = new HashMap<>();
    headers_5.put("Accept", "application/json, text/javascript, */*; q=0.01");
    headers_5.put("X-Requested-With", "XMLHttpRequest");
    
    Map<CharSequence, String> headers_9 = new HashMap<>();
    headers_9.put("Accept", "application/json, text/javascript, */*; q=0.01");
    headers_9.put("Origin", "http://192.168.1.11:8080");
    headers_9.put("X-Requested-With", "XMLHttpRequest");
    
    Map<CharSequence, String> headers_11 = new HashMap<>();
    headers_11.put("Accept", "image/avif,image/webp,*/*");
    
    Map<CharSequence, String> headers_12 = new HashMap<>();
    headers_12.put("Accept", "application/json, text/javascript, */*; q=0.01");
    headers_12.put("Content-Type", "application/json;charset=utf-8");
    headers_12.put("Origin", "http://192.168.1.11:8080");
    headers_12.put("X-Requested-With", "XMLHttpRequest");
    
    Map<CharSequence, String> headers_20 = new HashMap<>();
    headers_20.put("Accept", "application/json, text/javascript, */*; q=0.01");
    headers_20.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
    headers_20.put("Origin", "http://192.168.1.11:8080");
    headers_20.put("X-Requested-With", "XMLHttpRequest");


    ScenarioBuilder scn = scenario("AchatPanierTest")
      .// Open website
      exec(
        http("request_0")
          .get("/shopizer")
          .headers(headers_0)
          .resources(
            http("request_1")
              .get("/shopizer/shop")
              .headers(headers_0),
            http("request_2")
              .get("/shopizer/shop/reference/countryName?countryCode=CA")
              .headers(headers_2),
            http("request_3")
              .get("/shopizer/shop/reference/zoneName?zoneCode=QC")
              .headers(headers_2),
            http("request_4")
              .get("/shopizer/shop/cart/displayMiniCartByCode?shoppingCartCode=4271f5e68f7b428494c2059547319d66&_=1689259544279")
              .headers(headers_2),
            http("request_5")
              .get("/shopizer/services/public/DEFAULT/products/group/FEATURED_ITEM")
              .headers(headers_5)
          )
      )
      .pause(18)
      // Choose category
      .exec(
        http("request_6")
          .get("/shopizer/shop/category/living-room.html/ref=c:2")
          .headers(headers_0)
          .resources(
            http("request_7")
              .get("/shopizer/shop/reference/zoneName?zoneCode=QC")
              .headers(headers_2),
            http("request_8")
              .get("/shopizer/shop/reference/countryName?countryCode=CA")
              .headers(headers_2),
            http("request_9")
              .post("/shopizer/services/public/products/page/0/30/DEFAULT/en/living-room")
              .headers(headers_9),
            http("request_10")
              .get("/shopizer/shop/cart/displayMiniCartByCode?shoppingCartCode=4271f5e68f7b428494c2059547319d66&_=1689259563829")
              .headers(headers_2),
            http("request_11")
              .get("/static/products/DEFAULT/nrt345/SMALL/table2.JPG")
              .headers(headers_11)
              .check(status().is(404))
          )
      )
      .pause(9)
      // Add item to cart
      .exec(
        http("request_12")
          .post("/shopizer/shop/cart/addShoppingCartItem")
          .headers(headers_12)
          .body(RawFileBody("com/eql/shopizer/achatpaniertest/0012_request.json"))
      )
      .pause(9)
      // Access cart
      .exec(
        http("request_13")
          .get("/shopizer/shop/cart/shoppingCart.html")
          .headers(headers_0)
          .resources(
            http("request_14")
              .get("/shopizer/shop/reference/zoneName?zoneCode=QC")
              .headers(headers_2),
            http("request_15")
              .get("/shopizer/shop/reference/countryName?countryCode=CA")
              .headers(headers_2),
            http("request_16")
              .get("/shopizer/shop/cart/displayMiniCartByCode?shoppingCartCode=4271f5e68f7b428494c2059547319d66&_=1689259584056")
              .headers(headers_2)
          )
      )
      .pause(11)
      // Go to checkout
      .exec(
        http("request_17")
          .get("/shopizer/shop/order/checkout.html")
          .headers(headers_0)
          .resources(
            http("request_18")
              .get("/shopizer/shop/reference/zoneName?zoneCode=QC")
              .headers(headers_2),
            http("request_19")
              .get("/shopizer/shop/reference/countryName?countryCode=CA")
              .headers(headers_2),
            http("request_20")
              .post("/shopizer/shop/reference/provinces.html")
              .headers(headers_20)
              .formParam("countryCode", "CA")
              .formParam("lang", "en"),
            http("request_21")
              .post("/shopizer/shop/reference/provinces.html")
              .headers(headers_20)
              .formParam("countryCode", "CA")
              .formParam("lang", "en"),
            http("request_22")
              .get("/shopizer/shop/cart/displayMiniCartByCode?shoppingCartCode=4271f5e68f7b428494c2059547319d66&_=1689259596909")
              .headers(headers_2)
          )
      );

	  setUp(scn.injectOpen(atOnceUsers(1))).protocols(httpProtocol);
  }
}
