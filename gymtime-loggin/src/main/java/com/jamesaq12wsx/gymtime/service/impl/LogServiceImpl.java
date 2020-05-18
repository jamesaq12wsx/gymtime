package com.jamesaq12wsx.gymtime.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jamesaq12wsx.gymtime.database.LogRepository;
import com.jamesaq12wsx.gymtime.model.entity.Log;
import com.jamesaq12wsx.gymtime.service.LogService;
import com.jamesaq12wsx.gymtime.service.dto.LogQueryCriteria;
import com.jamesaq12wsx.gymtime.service.mapper.LogErrorMapper;
import com.jamesaq12wsx.gymtime.service.mapper.LogSmallMapper;
import com.jamesaq12wsx.gymtime.util.*;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class LogServiceImpl implements LogService {

    private final LogRepository logRepository;
    private final LogErrorMapper logErrorMapper;
    private final LogSmallMapper logSmallMapper;

    @Autowired
    public LogServiceImpl(LogRepository logRepository, LogErrorMapper logErrorMapper, LogSmallMapper logSmallMapper) {
        this.logRepository = logRepository;
        this.logErrorMapper = logErrorMapper;
        this.logSmallMapper = logSmallMapper;
    }

    @Override
    public Object queryAll(LogQueryCriteria criteria, Pageable pageable){
        Page<Log> page = logRepository.findAll(((root, criteriaQuery, cb) -> QueryHelp.getPredicate(root, criteria, cb)),pageable);
        String status = "ERROR";
        if (status.equals(criteria.getLogType())) {
            return PageUtils.toPage(page.map(logErrorMapper::toDto));
        }
        return page;
    }

    @Override
    public List<Log> queryAll(LogQueryCriteria criteria) {
        return logRepository.findAll(((root, criteriaQuery, cb) -> QueryHelp.getPredicate(root, criteria, cb)));
    }

    @Override
    public Object queryAllByUser(LogQueryCriteria criteria, Pageable pageable) {
        Page<Log> page = logRepository.findAll(((root, criteriaQuery, cb) -> QueryHelp.getPredicate(root, criteria, cb)),pageable);
        return PageUtils.toPage(page.map(logSmallMapper::toDto));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(String username, String browser, String ip, ProceedingJoinPoint joinPoint, Log log){

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        com.jamesaq12wsx.gymtime.annotation.Log aopLog = method.getAnnotation(com.jamesaq12wsx.gymtime.annotation.Log.class);

        // Method Path
        String methodName = joinPoint.getTarget().getClass().getName()+"."+signature.getName()+"()";

        StringBuilder params = new StringBuilder("{");
        //Args
        Object[] argValues = joinPoint.getArgs();
        //Arg Names
        String[] argNames = ((MethodSignature)joinPoint.getSignature()).getParameterNames();
        if(argValues != null){
            for (int i = 0; i < argValues.length; i++) {
                params.append(" ").append(argNames[i]).append(": ").append(argValues[i]);
            }
        }
        // 描述
        if (log != null) {
            log.setDescription(aopLog.value());
        }
        assert log != null;
        log.setRequestIp(ip);

//        String loginPath = "login";
//        if(loginPath.equals(signature.getName())){
//            try {
//                assert argValues != null;
//                ObjectMapper mapper = new ObjectMapper();
//                username = mapper.convertValue(argValues[0], JsonNode.class).get("username").asText();
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//        }

        log.setAddress(StringUtils.getCityInfo(log.getRequestIp()));
        log.setMethod(methodName);
        log.setUsername(username);
        log.setParams(params.toString() + " }");
        log.setBrowser(browser);
        logRepository.save(log);
    }

    @Override
    public Object findByErrDetail(Long id) {
        // TODO:
//        Log log = logRepository.findById(id).orElseGet(Log::new);
//        ValidationUtils.isNull( log.getId(),"Log","id", id);
//        byte[] details = log.getExceptionDetail();
//        return Dict.create().set("exception",new String(ObjectUtil.isNotNull(details) ? details : "".getBytes()));
        return null;
    }

    @Override
    public void download(List<Log> logs, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (Log log : logs) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("Username", log.getUsername());
            map.put("IP", log.getRequestIp());
            map.put("IP Source", log.getAddress());
            map.put("Description", log.getDescription());
            map.put("Browser", log.getBrowser());
            map.put("Request Time", log.getTime());
            map.put("Exception", new String(!ObjectUtils.isEmpty(log.getExceptionDetail()) ? log.getExceptionDetail() : "".getBytes()));
            map.put("Create Time", log.getCreatedAt());
            list.add(map);
        }
        FileUtils.downloadExcel(list, response);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delAllByError() {
        logRepository.deleteByLogType("ERROR");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delAllByInfo() {
        logRepository.deleteByLogType("INFO");
    }

}
