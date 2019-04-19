package com.rjpa.controller.system;

import com.google.gson.Gson;
import model.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/subPermissionAPI")
public class SystemPermissionApiController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    Gson gson = new Gson();

    @RequestMapping(value = "postSubMicoServerPermissions", method = RequestMethod.POST)
    public void postSubMicoServerPermissions_(@RequestBody Result data, HttpServletRequest request, HttpServletResponse response) {
        logger.info(gson.toJson(data));
        System.out.println("this is from subMicoServer Request");
    }
}
