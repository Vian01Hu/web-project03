//package com.usermanagment.pojo;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//
//@Slf4j
//@RestControllerAdvice
//public class GlobalExceptionHandler {
//
//    /**
//     * 处理业务异常
//     */
//    @ExceptionHandler(RuntimeException.class)
//    public ResponseEntity<ApiResponse<Object>> handleBusinessException(RuntimeException e) {
//        log.error("业务异常: {}", e.getMessage());
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                .body(ApiResponse.error(e.getMessage()));
//    }
//
//    /**
//     * 处理参数校验异常
//     */
////    @ExceptionHandler(ConstraintViolationException.class)
////    public ResponseEntity<ApiResponse<Object>> handleValidationException(ConstraintViolationException e) {
////        log.error("参数校验异常: {}", e.getMessage());
////        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
////                .body(ApiResponse.error(e.getMessage()));
////    }
//
//    /**
//     * 处理所有其他异常
//     */
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ApiResponse<Object>> handleGlobalException(Exception e) {
//        log.error("系统异常: ", e);
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                .body(ApiResponse.error("系统内部错误"));
//    }
//}