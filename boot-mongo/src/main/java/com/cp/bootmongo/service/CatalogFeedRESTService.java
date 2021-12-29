package com.cp.bootmongo.service;

import com.cp.bootmongo.dto.feeds.Product;

import java.util.List;

public interface CatalogFeedRESTService {

    public List<Product> fetchCatalogFeeds(int pageSize , int pageNo);

 }
