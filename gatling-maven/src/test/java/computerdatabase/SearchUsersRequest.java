package computerdatabase;

import java.time.Duration;
import java.util.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import io.gatling.javaapi.jdbc.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import static io.gatling.javaapi.jdbc.JdbcDsl.*;

public class SearchUsersRequest extends Simulation {

  private HttpProtocolBuilder httpProtocol = http
    .baseUrl("http://localhost:8081")
    .inferHtmlResources(AllowList(), DenyList(".*\\.js", ".*\\.css", ".*\\.gif", ".*\\.jpeg", ".*\\.jpg", ".*\\.ico", ".*\\.woff", ".*\\.woff2", ".*\\.(t|o)tf", ".*\\.png", ".*\\.svg", ".*detectportal\\.firefox\\.com.*"))
    .acceptHeader("*/*")
    .acceptEncodingHeader("gzip, deflate, br")
    .acceptLanguageHeader("en-US,en;q=0.9,ru-RU;q=0.8,ru;q=0.7,kk-KZ;q=0.6,kk;q=0.5")
    .authorizationHeader("Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI2LGFsdGFpckBnbWFpbC5jb20sUk9MRV9VU0VSX1JFQURFUiIsImlzcyI6IlNwcmluZyIsImlhdCI6MTcwMjI5ODc0MSwiZXhwIjoxNzAyMzg1MTQxfQ.9nwwM2f_VeR_VrX81A2eTzoXAxhtgl9_TVdbYehhoOoYZU81za5pfB4SmfrJsiSjsFN4T9BmB82TfaKQbiAuJg")
    .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");
  
  private Map<CharSequence, String> headers_0 = Map.ofEntries(
    Map.entry("Sec-Fetch-Dest", "empty"),
    Map.entry("Sec-Fetch-Mode", "cors"),
    Map.entry("Sec-Fetch-Site", "same-origin"),
    Map.entry("sec-ch-ua", "Not_A Brand\";v=\"8\", \"Chromium\";v=\"120\", \"Google Chrome\";v=\"120"),
    Map.entry("sec-ch-ua-mobile", "?0"),
    Map.entry("sec-ch-ua-platform", "macOS")
  );


  private ScenarioBuilder scn = scenario("SearchUsersRequest")
    .exec(
      http("request_0")
        .get("/users/search/Altair")
        .headers(headers_0)
    );

  {
	  setUp(scn.injectOpen(rampUsers(1000).during(20))).protocols(httpProtocol);
  }
}
