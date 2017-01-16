/**
 * JBoss, Home of Professional Open Source
 * Copyright 2016, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.redhat.developers.msa.goodbye;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;

public class ServerVerticle extends AbstractVerticle {

    @Override
    public void start() throws Exception {
        Router router = Router.router(vertx);
        // Goodbye EndPoint
        router.get("/api/goodbye").handler(ctx -> ctx.response().end(goodbye()));
        // Nap  Endpoint
        router.get("/api/nap").handler(ctx -> ctx.response().end(nap()));

        vertx.createHttpServer().requestHandler(router::accept).listen(8080);
        System.out.println("Service running at 0.0.0.0:8080");
    }

    private String nap() {
        System.out.println("Received request to nap 30 seconds");
        // Sleep 30 seconds
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("Back from the nap");
        return "Nap from " + new Date().toString();
    }

    private String goodbye() {
        String msg = "Goodbye on " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SS").format(new Date()); 
        System.out.println(msg);
        return msg;
    }


}
