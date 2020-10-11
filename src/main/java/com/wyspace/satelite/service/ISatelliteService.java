package com.wyspace.satelite.service;

import org.springframework.web.multipart.MultipartFile;

public interface ISatelliteService {

	String calculatePass(MultipartFile file, Integer bandwidth);

}
