package top.ink.dimgateway;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import top.ink.dimgateway.util.ZkListener;

/**
 * @author wanglongjun
 */
@SpringBootApplication
@MapperScan(basePackages = {"top.ink.dimgateway.mapper"})
public class DimGatewayApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(DimGatewayApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Thread thread = new Thread(new ZkListener(),"zkListener");
        thread.setDaemon(true);
        thread.start();
    }
}
