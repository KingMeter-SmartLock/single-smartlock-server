package com.kingmeter.single.smartlock.server.rest;


import com.kingmeter.dto.smartlock.socket.out.LockHeartBeatResponseDto;
import com.kingmeter.dto.smartlock.socket.out.UnLockSendResponseDto;
import com.kingmeter.single.smartlock.server.rest.dto.LockData;
import com.kingmeter.smartlock.socket.business.code.ServerFunctionCodeType;
import com.kingmeter.smartlock.socket.rest.SmartLockTestApplication;
import com.kingmeter.socket.framework.application.SocketApplication;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/test")
@Slf4j
@RestController
public class TestApi {
    @Autowired
    private SmartLockTestApplication testApplication;

    @Autowired
    private SocketApplication socketApplication;


    @DeleteMapping("/batchUnlock/{lockId}")
    public void stopUnlock(@PathVariable("lockId") long lockId) {
        testApplication.stopUnLock(lockId);
    }


    @GetMapping("/batchUnlock/{lockId}")
    public String batchUnlock(@PathVariable(name = "lockId") long lockId,
                              @RequestParam(name = "times") int times,
                              @RequestParam(name = "perLock") long perLock) {

        return testApplication.batchUnLock(lockId, times, perLock);
    }


    @PostMapping("/batchUnlock")
    public String batchUnlock(@RequestBody LockData data) {
        String[] lockArray = data.getData().trim().split(",");
        for (String lockId : lockArray
        ) {
            testApplication.batchUnLock(Long.parseLong(lockId), data.getTimes(), data.getPerLock());
        }
        return "batch unlock succeed";
    }

    @DeleteMapping("/queryLockInfo/{lockId}")
    public void stopQueryLockInfo(@PathVariable("lockId") long lockId) {
        testApplication.stopQueryLockInfo(lockId);
    }


    @GetMapping("/queryLockInfo/{lockId}")
    public String batchQueryLockInfo(@PathVariable(name = "lockId") long lockId,
                                     @RequestParam(name = "times") int times,
                                     @RequestParam(name = "perLock") long perLock) {

        return testApplication.batchQueryLockInfo(lockId, times, perLock);
    }

    @GetMapping("/testStickPackage/{lockId}")
    public String testStickPackage(@PathVariable(name = "lockId") long lockId,
                                   @RequestParam(name = "preCount") int preCount,
                                   @RequestParam(name = "afterCount") int afterCount) {
        List<com.kingmeter.socket.framework.dto.ResponseBody> array = new ArrayList();

        LockHeartBeatResponseDto dto = new LockHeartBeatResponseDto();
        dto.setSls("0");

        for (int i = 0; i < preCount; i++) {
            com.kingmeter.socket.framework.dto.ResponseBody heartbeat = socketApplication.createResponseBody(lockId,
                    ServerFunctionCodeType.LockHeartBeat, dto);
            array.add(heartbeat);
        }

        UnLockSendResponseDto responseDto = new UnLockSendResponseDto("1", "");
        com.kingmeter.socket.framework.dto.ResponseBody unlock = socketApplication.createResponseBody(lockId,
                ServerFunctionCodeType.UnLockSend, responseDto);

        array.add(unlock);

        for (int i = 0; i < afterCount; i++) {
            com.kingmeter.socket.framework.dto.ResponseBody heartbeat = socketApplication.createResponseBody(lockId,
                    ServerFunctionCodeType.LockHeartBeat, dto);
            array.add(heartbeat);
        }

        socketApplication.sendSocketMsg(String.valueOf(lockId),array);
        return "ok";
    }
}
