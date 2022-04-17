package com.rikkeisoft.canifashop.base;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public abstract class BaseController {
	
	@Value("${page.size}")
    protected Integer PAGE_SIZE;

    public ResponseEntity<BaseResponseEntity> success(String message) {
        return ResponseEntity.ok(BaseResponseEntity.success(message));
    }

    public ResponseEntity<BaseResponseEntity> success(Object data, String message) {
        return ResponseEntity.ok(BaseResponseEntity.success(data, message));
    }

    public ResponseEntity<BaseResponseEntity> created(Object data, String message) {
        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponseEntity.success(data, message));
    }
}