package com.example.shop_backend.paypal;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class PayPalClient {
    private final RestTemplate rest = new RestTemplate();
    private final PaypalProperties props;

    public PayPalClient (PaypalProperties props) {
        this.props = props;
    }

    public String getAppBaseUrl() {
        String fromProps = props.getAppBaseUrl();
        if (StringUtils.hasText(fromProps)) return fromProps;

        String fromReq = ServletUriComponentsBuilder.fromCurrentContextPath()
                .build()
                .toUriString();
        if (StringUtils.hasText(fromReq)) return fromReq;

        throw new IllegalStateException(
                "App Basis URL nicht festgelegt.");
    }



    private String getAccessToken() {
        String url = props.baseUrl() + "/v1/oauth2/token";
        HttpHeaders h = new HttpHeaders();
        h.setBasicAuth(props.getClientId(), props.getClientSecret());
        h.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("grant_type", "client_credentials");

        var res= rest.exchange(url, HttpMethod.POST, new HttpEntity<>(form, h), Map.class);
        return (String) Objects.requireNonNull(res).getBody().get("access_token");
    }


    public Map<String, Object> createOrder(String returnUrl, String cancelUrl, String currency, String value, String customId) {
        String url = props.baseUrl() + "/v2/checkout/orders";
        HttpHeaders h = new HttpHeaders();
        h.setBearerAuth(getAccessToken());
        h.setContentType(MediaType.APPLICATION_JSON);

        Map<String,Object> amount = Map.of("currency_code", currency, "value", value);
        Map<String, Object> pu = new HashMap<>();
        pu.put("amount", amount);
        pu.put("custom_id", customId);

        Map<String, Object> ctx = new HashMap<>();
        ctx.put("return_url", returnUrl);
        ctx.put("cancel_url", cancelUrl);
        ctx.put("brand_name", "Laufschuh.shop" );
        ctx.put("user_action", "PAY_NOW");
        ctx.put("shipping_preference", "NO_SHIPPING");

        Map<String, Object> body = new HashMap<>();
        body.put("intent", "CAPTURE");
        body.put("purchase_units", List.of(pu));
        body.put("application_context", ctx);

        var res = rest.exchange(url, HttpMethod.POST, new HttpEntity<>(body, h), Map.class);
        return res.getBody();

    }

    public Map<String, Object> captureOrder(String paypalOrderId) {
        String url = props.baseUrl() + "/v2/checkout/orders/" + paypalOrderId + "/capture";
        HttpHeaders h = new HttpHeaders();
        h.setBearerAuth(getAccessToken());
        h.setContentType(MediaType.APPLICATION_JSON);
        var res = rest.exchange(url, HttpMethod.POST, new HttpEntity<>("{}", h), Map.class);
        return res.getBody();
    }

}
