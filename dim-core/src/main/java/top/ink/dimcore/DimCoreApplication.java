package top.ink.dimcore;

import io.netty.channel.nio.NioEventLoopGroup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import top.ink.dimcore.boot.ChatBootServer;
import top.ink.dimcore.util.Register;
import top.ink.dimcore.util.SpringBeanFactory;

import javax.annotation.Resource;
import java.net.InetAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author wanglongjun
 */
@SpringBootApplication
public class DimCoreApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(DimCoreApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        ChatBootServer bootServer = SpringBeanFactory.getBean(ChatBootServer.class);
        bootServer.init();
        Register register = SpringBeanFactory.getBean(Register.class);
        ExecutorService thread = Executors.newSingleThreadExecutor();
        thread.execute(register);
    }
}
