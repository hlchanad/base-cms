package com.chanhonlun.basecms.security.matcher;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AssetsMatcher implements RequestMatcher {

    private static final Logger logger = LoggerFactory.getLogger(AssetsMatcher.class);

    private static final List<String> DEFAULT_ASSETS_FOLDERS    = Arrays.asList("/assets/**", "/pages/**");

    private final String contextPath;
    private final List<String> assetsFolders;

    public AssetsMatcher(String contextPath) {
        this.contextPath = contextPath;
        this.assetsFolders = DEFAULT_ASSETS_FOLDERS;
    }

    public AssetsMatcher(String contextPath, List<String> assetsFolders) {
        this.contextPath = contextPath;
        this.assetsFolders = getList(assetsFolders, DEFAULT_ASSETS_FOLDERS);
    }

    @Override
    public boolean matches(HttpServletRequest request) {

        String endpoint = request.getRequestURI().substring(contextPath.length());

        boolean isAssetsFolder = assetsFolders
                .stream()
                .anyMatch(pattern -> new AntPathMatcher().match(pattern, endpoint));

        logger.debug("isAssetsFolder: {}", isAssetsFolder);

        return isAssetsFolder;
    }

    private List<String> getList(List<String> list, List<String> defaultList) {

        list = list.stream().map(String::trim).filter(StringUtils::isNotBlank).collect(Collectors.toList());

        return list.isEmpty() ? defaultList : list;
    }
}
