package kr.msp.util;

import java.util.Map;

import kr.msp.response.ResponseCode;
import kr.msp.response.ResponseHeader;

public class Utils {
    public static ResponseHeader createResponseHeader(Map<String, Object> T) {
        ResponseCode responseCode = (T != null && !T.isEmpty()) ? ResponseCode.OK : ResponseCode.NoContent;
        return new ResponseHeader(responseCode);
    }

}
