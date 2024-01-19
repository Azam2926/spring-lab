//package com.example.lab;
//
//import reactor.netty.http.server.HttpServerResponse;
//
//import java.io.IOException;
//import java.util.concurrent.CopyOnWriteArrayList;
//
//
//public class SseEmitter {
//
//    private HttpServerResponse response;
//    private CopyOnWriteArrayList<SseEvent> events;
//    private boolean isClosed;
//
//    public SseEmitter(HttpServerResponse response) {
//        this.response = this.response;
//        this.events = new CopyOnWriteArrayList<>();
//        this.isClosed = false;
//        this.response.setContentType("text/event-stream");
//        this.response.setCharacterEncoding("UTF-8");
//        this.response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
//        this.response.setHeader("Pragma", "no-cache");
//        this.response.setHeader("Expires", "0");
//    }
//
//    public synchronized void send(SseEvent event) throws IOException {
//        if (isClosed) {
//            return;
//        }
//
//        events.add(event);
//
//        String data = event.getData();
//        String id = event.getId();
//        String type = event.getType();
//        String retry = String.valueOf(event.getRetry());
//
//        StringBuilder sb = new StringBuilder();
//        if (id != null) {
//            sb.append("id: ").append(id).append("\n");
//        }
//        if (type != null) {
//            sb.append("event: ").append(type).append("\n");
//        }
//        sb.append("data: ").append(data).append("\n");
//        if (retry != null) {
//            sb.append("retry: ").append(retry).append("\n");
//        }
//        sb.append("\n");
//
//        response.getWriter().write(sb.toString());
//        response.flushBuffer();
//    }
//
//    public synchronized void close() throws IOException {
//        if (isClosed) {
//            return;
//        }
//
//        isClosed = true;
//
//        response.getWriter().write("event: close\n\n");
//        response.flushBuffer();
//
//        response.getWriter().close();
//        response.getOutputStream().close();
//        response.setHeader("Connection", "close");
//        response.flushBuffer();
//    }
//
//    public synchronized boolean isClosed() {
//        return isClosed;
//    }
//
//    public static class SseEvent {
//        private String id;
//        private String type;
//        private String data;
//        private int retry;
//
//        public SseEvent(String data) {
//            this.data = data;
//        }
//
//        public SseEvent(String id, String type, String data) {
//            this.id = id;
//            this.type = type;
//            this.data = data;
//        }
//
//        public String getId() {
//            return id;
//        }
//
//        public void setId(String id) {
//            this.id = id;
//        }
//
//        public String getType() {
//            return type;
//        }
//
//        public void setType(String type) {
//            this.type = type;
//        }
//
//        public String getData() {
//            return data;
//        }
//
//        public void setData(String data) {
//            this.data = data;
//        }
//
//        public int getRetry() {
//            return retry;
//        }
//
//        public void setRetry(int retry) {
//            this.retry = retry;
//        }
//    }
//}
