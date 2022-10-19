package com.kingmeter.single.smartlock.server.rest;

import com.kingmeter.socket.framework.util.CacheUtil;
import io.netty.channel.socket.SocketChannel;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;

@RequestMapping("/netty")
@RestController
public class NettyApi {
    @GetMapping("/getCurrentLockMap")
    public Map<Long, Map<String, String>> getCurrentLockMap() {
        return CacheUtil.getInstance().getDeviceInfoMap();
    }


    @GetMapping("/getLockIdAndChannelId")
    public Map<String, String> getLockAndChannelId() {
        Map<String, String> result = new HashMap<>();
        for (Map.Entry<String, SocketChannel> entry : CacheUtil.getInstance().getDeviceIdAndChannelMap().entrySet()) {
            result.put(entry.getKey(), entry.getValue().id().asLongText());
        }
        return result;
    }


    @GetMapping("/getCurrentTime")
    public String getCurrentTime() {

        TimeZone timeZone = TimeZone.getTimeZone("GMT+8:00");
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        sdf.setTimeZone(timeZone);

        return sdf.format(new Date());
    }


    @AllArgsConstructor
    @Data
    public class LonLat {
        private String current;
        private String lon;
        private String lat;
    }

    @GetMapping("/connection")
    public List<ConnectionDto> queryConnection() {
        List<ConnectionDto> result = new ArrayList<>();
        Map<String, SocketChannel> map = CacheUtil.getInstance().getDeviceIdAndChannelMap();
        for (Map.Entry<String, SocketChannel> entry : map.entrySet()) {
            String deviceId = entry.getKey();
            SocketChannel channel = entry.getValue();
            result.add(new ConnectionDto(deviceId, channel.remoteAddress().getHostString(),
                    channel.remoteAddress().getPort()));
        }
        return result;
    }

    @AllArgsConstructor
    @Data
    class ConnectionDto {
        private String deviceId;
        private String host;
        private int port;
    }
}
