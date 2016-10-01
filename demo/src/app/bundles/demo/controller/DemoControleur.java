package app.bundles.demo.controller;

import com.marvin.component.dialog.Request;
import com.marvin.component.dialog.Response;
import com.marvin.component.routing.mapping.Route;

public class DemoControleur {

    @Route(name="homepage", path="/")
    public Response homeAction(Request demande) {
        System.out.println("homepage");
        return Response.HTTPResponse();
    }
}
