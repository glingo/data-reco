package com.marvin.component.dialog;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ResponseFilter extends FilterOutputStream {

    public ResponseFilter(OutputStream out) {
        super(out);
    }

    public Response getResponse() throws IOException {
        return Response.HTTPResponse();
    }
}
