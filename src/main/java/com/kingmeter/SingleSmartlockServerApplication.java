package com.kingmeter;

import com.kingmeter.socket.framework.role.server.NettyServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class SingleSmartlockServerApplication implements CommandLineRunner {

    @Autowired
    private NettyServer nettyServer;

    public static void main(String[] args) {
        SpringApplication.run(SingleSmartlockServerApplication.class, args);
    }

    @Override
    public void run(String... args) {
        nettyServer.bind();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> nettyServer.destroy()));
    }
}
