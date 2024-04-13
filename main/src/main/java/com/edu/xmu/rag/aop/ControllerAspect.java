package com.edu.xmu.rag.aop;

import com.edu.xmu.rag.core.exception.BusinessException;
import com.edu.xmu.rag.core.model.ReturnNo;
import com.edu.xmu.rag.core.model.ReturnObject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

import static com.edu.xmu.rag.core.model.Constants.BEGIN_TIME;
import static com.edu.xmu.rag.core.model.Constants.END_TIME;

@Aspect
@Component
public class ControllerAspect {
    private final Logger logger = LoggerFactory.getLogger(ControllerAspect.class);

    @Value("${page-size.max}")
    private int max_page_size;
    @Value("${page-size.default}")
    private int default_page_size;

    /**
     * 所有返回值为ReturnObject的Controller
     */
    @Around("com.edu.xmu.rag.aop.CommonPointCuts.controller()")
    public Object doAround(ProceedingJoinPoint jp) throws Throwable {
        ReturnObject retVal;

        MethodSignature ms = (MethodSignature) jp.getSignature();
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();

        String[] paramNames = ms.getParameterNames();
        logger.debug("doAround: method = {}, paramNames = {}", ms.getName(), paramNames);
        Object[] args = jp.getArgs();
        try {
            Object[] newArgs = checkPageTimeLimit(request, paramNames, args);
            Object obj = jp.proceed(newArgs);
            retVal = (ReturnObject) obj;
        } catch (BusinessException exception) {
            logger.info("doAround: BusinessException， errno = {}", exception.getErrno());
            retVal = new ReturnObject(exception.getErrno(), exception.getMessage());
        }

        ReturnNo code = retVal.getCode();
        logger.debug("doAround: jp = {}, code = {}", jp.getSignature().getName(), code);
        changeHttpStatus(code, response);

        return retVal;
    }

    /**
     * 根据code，修改response的HTTP Status code
     */
    private void changeHttpStatus(ReturnNo code, HttpServletResponse response) {
        switch (code) {
            // 201:
            case CREATED ->
                    response.setStatus(HttpServletResponse.SC_CREATED);
            // 404：资源不存在
            case RESOURCE_ID_NOT_EXIST ->
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            // 401
            case AUTH_INVALID_JWT, AUTH_JWT_EXPIRED, AUTH_NEED_LOGIN, AUTH_INVALID_ACCOUNT, AUTH_ID_NOT_EXIST, AUTH_USER_FORBIDDEN,
                    USER_INVALID_ACCOUNT, USER_NAME_EXIST ->
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            // 500：数据库或其他严重错误
            case INTERNAL_SERVER_ERR ->
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            // 400
            case FIELD_NOT_VALID, IMG_FORMAT_ERROR, IMG_SIZE_EXCEED, PARAMETER_MISSED ->
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            // 403
            case STATE_NOT_ALLOW ->
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            // 其他
            default -> response.setStatus(HttpServletResponse.SC_OK);
        }
        response.setContentType("application/json;charset=UTF-8");
    }

    /**
     * 设置默认的page = 1和pageSize = 10
     * 防止客户端发过来pageSize过大的请求
     */
    private Object[] checkPageTimeLimit(HttpServletRequest request, String[] paramNames, Object[] args) {
        Integer page = 1;
        Integer pageSize = default_page_size;

        if (request != null) {
            String pageString = request.getParameter("page");
            String pageSizeString = request.getParameter("pageSize");
            if (null != pageString  && !pageString.isEmpty() && pageString.matches("\\d+")) {
                page = Integer.valueOf(pageString);
                if (page <= 0) {
                    page = 1;
                }
            }

            if (null !=pageSizeString  && !pageSizeString.isEmpty() && pageSizeString.matches("\\d+")) {
                pageSize = Integer.valueOf(pageSizeString);
                if (pageSize <= 0 || pageSize > max_page_size) {
                    pageSize = default_page_size;
                }
            }
        }


        for (int i = 0; i < paramNames.length; i++) {
            logger.debug("checkPageTimeLimit: paramNames[{}] = {}", i, paramNames[i]);
            if (paramNames[i].equals("page")) {
                logger.debug("checkPageTimeLimit: set {} to {}",paramNames[i], page);
                args[i] = page;
                continue;
            }

            if (paramNames[i].equals("pageSize")) {
                logger.debug("checkPageTimeLimit: set {} to {}",paramNames[i], pageSize);
                args[i] = pageSize;
                continue;
            }

            if (paramNames[i].equals("beginTime") && (args[i] == null)){
                logger.debug("checkPageTimeLimit: set {} to {}",paramNames[i], BEGIN_TIME);
                args[i] = BEGIN_TIME;
                continue;
            }

            if (paramNames[i].equals("endTime") && (args[i] == null)){
                logger.debug("checkPageTimeLimit: set {} to {}",paramNames[i], END_TIME);
                args[i] = END_TIME;
            }
        }
        return args;
    }
}
