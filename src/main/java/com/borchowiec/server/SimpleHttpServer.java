package com.borchowiec.server;

import fi.iki.elonen.NanoHTTPD;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Slf4j
public class SimpleHttpServer extends NanoHTTPD {
    private final String directoryPath;

    public SimpleHttpServer(int port, String directoryPath) {
        super(port);
        this.directoryPath = directoryPath;
    }

    @Override
    public Response serve(IHTTPSession session) {
        String uri = session.getUri();
        File file = new File(directoryPath + uri);

        if (file.exists() && file.isFile()) {
            try {
                return newFixedLengthResponse(Response.Status.OK, getMimeTypeForFile(uri), new FileInputStream(file), file.length());
            } catch (IOException e) {
                log.error("Cannot serve file: " + file.getAbsolutePath(), e);
                return newFixedLengthResponse(Response.Status.INTERNAL_ERROR, NanoHTTPD.MIME_PLAINTEXT, "Internal Server Error");
            }
        } else {
            return newFixedLengthResponse(Response.Status.NOT_FOUND, NanoHTTPD.MIME_PLAINTEXT, "File not found");
        }
    }
}
