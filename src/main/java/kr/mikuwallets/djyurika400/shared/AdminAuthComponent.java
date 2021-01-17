package kr.mikuwallets.djyurika400.shared;

import kr.mikuwallets.djyurika400.exception.NotAuthorizedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class AdminAuthComponent {
    @Value("${ddj400.key.access}")
    private String accessKey;

    // just admin
    public void validateAccessToken(HttpServletRequest request) {
        String xAccessToken = getAccessToken(request);
        if (xAccessToken == null || !(xAccessToken.equals(accessKey))) {
            throw new NotAuthorizedException("Key validation failed");
        }
    }

    private String getAccessToken(HttpServletRequest request) {
        return request.getHeader("X-ACCESS-KEY");
    }

}
