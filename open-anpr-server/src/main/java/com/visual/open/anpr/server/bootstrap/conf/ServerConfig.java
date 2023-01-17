package com.visual.open.anpr.server.bootstrap.conf;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration("visualServerConfig")
public class ServerConfig {

    @Configuration
    @ComponentScan("com.visual.open.anpr.server.utils")
    public static class SearchUtils {}

    @Configuration
    @ComponentScan("com.visual.open.anpr.server.config")
    public static class SearchConfig {}

    @Configuration
    @ComponentScan({"com.visual.open.anpr.server.service"})
    public static class ServiceConfig {}

    @Configuration
    @ComponentScan({"com.visual.open.anpr.server.controller"})
    public static class ControllerConfig {}

    @Configuration
    @ComponentScan({"com.visual.open.anpr.server.scheduler"})
    public static class SchedulerConfig {}
}
