package kr.msp.util;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import kr.msp.response.Response;
import kr.msp.response.ResponseCode;
import kr.msp.response.ResponseHeader;

public class Utils {
    
    public static ResponseEntity<Response<ResponseHeader, Map<String, Object>>> buildOkResponse(ResponseCode responseCode, Map<String, Object> responseMap) {
        ResponseHeader responseHeader = new ResponseHeader();
        responseHeader.setResult_code(responseCode.getCode());
        responseHeader.setResult_msg(responseCode.getMessage());
        
        responseMap.put("resultCode", responseCode.getCode());
        responseMap.put("resultMsg", responseCode.getMessage());
        
        return ResponseEntity.ok(new Response<>(responseHeader, responseMap));
    }
    
    public static ResponseEntity<Response<ResponseHeader, Map<String, Object>>> buildBadResponse(ResponseCode responseCode, Map<String, Object> responseMap) {
        ResponseHeader responseHeader = new ResponseHeader();
        responseHeader.setResult_code(responseCode.getCode());
        responseHeader.setResult_msg(responseCode.getMessage());
        
        responseMap.put("resultCode", responseCode.getCode());
        responseMap.put("resultMsg", responseCode.getMessage());
        
        return ResponseEntity.badRequest().body(new Response<>(responseHeader, responseMap));
    }

}
