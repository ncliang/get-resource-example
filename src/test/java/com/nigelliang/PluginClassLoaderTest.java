package com.nigelliang;

import org.apache.kafka.connect.runtime.isolation.PluginClassLoader;
import org.junit.Test;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;

import static org.junit.Assert.assertEquals;

public class PluginClassLoaderTest {
    @Test
    public void classPathResourceLeaks() throws Exception {
        // The system loader gets 1.0.0
        assertEquals("1.0.0-SNAPSHOT", App.getVersion());

        File pluginPath = new File("/Users/nliang/repos/get-resource-example/plugin_path");
        File plugin = new File("/Users/nliang/repos/get-resource-example/plugin_path/get-resource-example-2.0.0-SNAPSHOT.jar");

        // This fails with system parent loader
        PluginClassLoader pluginClassLoader = new PluginClassLoader(
            pluginPath.toURL(),
            new URL[]{plugin.toURL()},
            this.getClass().getClassLoader());

        // This works with null parent loader
//        PluginClassLoader pluginClassLoader = new PluginClassLoader(
//            pluginPath.toURL(),
//            new URL[]{plugin.toURL()},
//            null);
        Class<?> appClass = pluginClassLoader.loadClass("com.nigelliang.App");
        Method method = appClass.getMethod("getVersion");
        assertEquals("2.0.0-SNAPSHOT", method.invoke(null));
    }
}
