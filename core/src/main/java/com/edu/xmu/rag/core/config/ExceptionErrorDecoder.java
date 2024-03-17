package com.edu.xmu.rag.core.config;

import com.edu.xmu.rag.core.exception.BusinessException;
import com.edu.xmu.rag.core.model.ReturnNo;
import com.edu.xmu.rag.core.util.JacksonUtil;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExceptionErrorDecoder implements ErrorDecoder {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionErrorDecoder.class);

    @Override
    public Exception decode(String s, Response response) {
        BusinessException exception = null;
        if (null != response){
            String responseString = response.body().toString();
            logger.debug("decode: responseString = {}",responseString);
            Integer errNo = JacksonUtil.parseInteger(responseString, "errno");
            String errMsg = JacksonUtil.parseString(responseString, "errMsg");
            exception =  new BusinessException(ReturnNo.getByCode(errNo), errMsg);
        }
        return exception;
    }
}
