package com.alzios.api.configs;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.jwt.MappedJwtClaimSetConverter;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;


@Component
public class UsernameSubClaimAdapter implements Converter<Map<String, Object>, Map<String, Object>> {
    private final MappedJwtClaimSetConverter delegate = MappedJwtClaimSetConverter.withDefaults(Collections.emptyMap());

    @Override
    public Map<String, Object> convert(Map<String, Object> claims) {
        Map<String, Object> convertedClaims = this.delegate.convert(claims);

        Object adminClaim = convertedClaims.get("custom:admin");
        if(adminClaim != null && adminClaim.equals("1")) {
            convertedClaims.put("scope", "admin");
        }

        Object subClaim = convertedClaims.get("sub");
        if(subClaim != null) {
            convertedClaims.put("sub", subClaim.toString());
        }

        return convertedClaims;
    }
}