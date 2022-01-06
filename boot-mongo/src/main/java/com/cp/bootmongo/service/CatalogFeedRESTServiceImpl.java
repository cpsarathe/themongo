package com.cp.bootmongo.service;

import com.cp.bootmongo.dto.CatalogDTO;
import com.cp.bootmongo.dto.feeds.CatalogFeedsResponse;
import com.cp.bootmongo.dto.feeds.Product;
import com.cp.bootmongo.exception.RestServiceException;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.charset.Charset;
import java.util.*;

@Service("catalogFeedRESTService")
public class CatalogFeedRESTServiceImpl implements CatalogFeedRESTService {

    @Autowired
    private RestTemplate restTemplate;

    public List<Product> fetchCatalogFeeds(int pageSize, int pageNo) {
        try {
            HttpHeaders httpHeaders = createHeaders("dsdsd", "dsdsd");
            HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);
            Map<String, Integer> params = new HashMap<>();
            params.put("pageSize", pageSize);
            params.put("page", pageNo);
            UriComponents builder = UriComponentsBuilder
                    .fromHttpUrl("https://www.testtest.com/feeds/catalog-feed")
                    .queryParam("pageSize", pageSize)
                    .queryParam("page", pageNo)
                    .build();

            ResponseEntity<CatalogFeedsResponse> responseEntity = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, httpEntity, CatalogFeedsResponse.class);
            return responseEntity.getBody().getBody().getProducts();
        } catch (Exception ex) {
            String uuid = UUID.randomUUID().toString();
            throw new RestServiceException("Error calling REST Service " + uuid, ex);
        }
    }

    private HttpHeaders createHeaders(String username, String password) {
        return new HttpHeaders() {{
            String auth = username + ":" + password;
            byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("UTF-8")));
            String authHeader = "Basic " + new String(encodedAuth);
            set("Authorization", authHeader);
            setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        }};
    }
}
