package com.kingmeter.single.smartlock.server.rest;


import com.alibaba.fastjson.JSONObject;
import com.kingmeter.dto.smartlock.rest.request.SettingCardListRequestDto;
import com.kingmeter.dto.smartlock.socket.in.LockQueryRequestDto;
import com.kingmeter.dto.smartlock.socket.in.SetCardListRequestDto;
import com.kingmeter.dto.smartlock.socket.in.SetCertForLockRequestDto;
import com.kingmeter.dto.smartlock.socket.in.UnLockSendRequestDto;
import com.kingmeter.smartlock.socket.rest.SmartLockSocketApplication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/lock")
@RestController
public class LockApi {

    @Autowired
    private SmartLockSocketApplication smartLockSocketApplication;


    /**
     * 扫描开智能锁
     * @return
     */
    @RequestMapping(value = "/unLockRequest/{lockId}",method = RequestMethod.GET)
    public UnLockSendRequestDto unLockRequest(
            @PathVariable("lockId") long lockId){
        return smartLockSocketApplication.unLockSend(lockId,"123","");
    }

    @RequestMapping(value = "/queryInfoFromSmartLock/{lockId}",method = RequestMethod.GET)
    public LockQueryRequestDto queryLockInfo(@PathVariable("lockId") long lockId){
        return smartLockSocketApplication.queryLockInfo(lockId);
    }

    @RequestMapping(value = "/setCardListInLock",method = RequestMethod.POST)
    public SetCardListRequestDto setCardList(@RequestBody SettingCardListRequestDto requestDto){
        return smartLockSocketApplication.setCardList(requestDto);
    }

    @PutMapping("/setCertForLock/{lockId}")
    public SetCertForLockRequestDto setCert(@PathVariable("lockId") long lockId,
                                            @RequestParam("cert") String cert){
        return smartLockSocketApplication.setCert(lockId,cert);
    }

}
