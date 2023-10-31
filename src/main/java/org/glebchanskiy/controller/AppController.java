package org.glebchanskiy.controller;

import com.google.common.io.Files;
import org.glebchanskiy.kek.Configuration;
import org.glebchanskiy.kek.router.controllers.Controller;
import org.glebchanskiy.kek.utils.Request;
import org.glebchanskiy.kek.utils.Response;
import org.glebchanskiy.kek.utils.ResponseHeaders;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class AppController extends Controller {
    private final Configuration configuration = Configuration.getInstance();
    public AppController(String route) {
        super(route);
    }

    @Override
    public Response getMapping(Request request) {
        System.out.println("app controller");
        String var10001 = this.configuration.getLocation();

        System.out.println("path: " + var10001 + "/index.html");

        InputStream pageStream = this.getPageInputStream(var10001 + "/index.html");
        if (pageStream != null) {
            System.out.println("pageStream != null");
            String content;
            try {
                content = new String(pageStream.readAllBytes(), StandardCharsets.UTF_8);
            } catch (IOException var5) {
                return null;
            }

            System.out.println("content:\n"+content);

            ResponseHeaders responseHeaders = new ResponseHeaders();
            responseHeaders.put("Content-Type", "text/html");
            responseHeaders.put("Content-Length", String.valueOf(content.getBytes(StandardCharsets.UTF_8).length));
            return Response.builder().status(200).textStatus("OK").headers(responseHeaders).body(content).build();
        } else {
            return Response.NOT_FOUNDED;
        }
    }

    private InputStream getPageInputStream(String path) {
        InputStream pageStream = Configuration.class.getResourceAsStream(path);
        if (pageStream == null) {
            try {
                pageStream = Files.asByteSource(new File(path)).openStream();
            } catch (IOException var4) {
            }
        }

        return pageStream;
    }
}
