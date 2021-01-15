package kr.mikuwallets.djyurika400.shared;

import kr.mikuwallets.djyurika400.exception.NotAuthorizedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class SharedModule {
    @Value("${ddj400.key.access}")
    private static String accessKey;

    @Value("${ddj400.key.access}")
    private void setAccessKey(String accessKey){
        SharedModule.accessKey = accessKey;
    }

    // just admin
    public static void validateAccessToken(HttpServletRequest request) {
        String xAccessToken = getAccessToken(request);
        if (xAccessToken == null || !(xAccessToken.equals(accessKey))) {
            throw new NotAuthorizedException("Key validation failed");
        }
    }

    private static String getAccessToken(HttpServletRequest request) {
        return request.getHeader("X-ACCESS-KEY");
    }

}
