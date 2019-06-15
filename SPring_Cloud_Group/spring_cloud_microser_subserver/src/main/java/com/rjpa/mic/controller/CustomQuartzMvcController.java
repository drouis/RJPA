package com.rjpa.mic.controller;

import anno.Permission;
import com.google.gson.Gson;
import com.rjpa.mic.service.ICustomJobService;
import model.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "/qutz")
public class CustomQuartzMvcController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    Gson gson = new Gson();

    ModelAndView quartzView = new ModelAndView("/quartz/quartzIndex.html");

    @RequestMapping(value = "/initQuartzPageView", method = RequestMethod.GET)
    public ModelAndView initQuartzPageView_(@RequestParam(value = "jobName", defaultValue = "") String jobName,
                                            @RequestParam(value = "jobGroup", defaultValue = "") String jobGroup,
                                            HttpServletRequest request, HttpServletResponse response) {

        return quartzView;
    }

    /**
     * 创建定时任务
     *
     * @param jobName
     * @param jobGroup
     * @return
     */
    @Permission(name = "创建定时任务", permissionName = "local.micoSSV.qutz.startCronJob", permissionUrl = "/qutz/startCronJob")
    @RequestMapping(value = "/startCronJob", method = RequestMethod.POST)
    @ResponseBody
    public void startCronJob_(@RequestParam("jobName") String jobName, @RequestParam("jobGroup") String jobGroup,
                              HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        Result errMsg = Result.ok(null);
        try {
            // TODO 1 创建定时任务
            customJobService.addCronJob(jobName, jobGroup);
            // TODO 2 返回更新数据结果 前端传过来的回调函数名称
            String callback = request.getParameter("callback");
            //TODO 用回调函数名称包裹返回数据，这样，返回数据就作为回调函数的参数传回去了
            String result = callback + "(" + new Gson().toJson(errMsg) + ")";
            response.getWriter().write(result);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * 创建异步任务
     *
     * @param jobName
     * @param jobGroup
     * @return
     */
    @Permission(name = "创建异步任务", permissionName = "local.micoSSV.qutz.startAsyncJob", permissionUrl = "/qutz/startAsyncJob")
    @RequestMapping(value = "/startAsyncJob", method = RequestMethod.POST)
    public void startAsyncJob_(@RequestParam("jobName") String jobName, @RequestParam("jobGroup") String jobGroup,
                               HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        Result errMsg = Result.ok(null);
        try {
            // TODO 1 创建异步任务
            customJobService.addAsyncJob(jobName, jobGroup);
            // TODO 2 返回更新数据结果 前端传过来的回调函数名称
            String callback = request.getParameter("callback");
            //TODO 用回调函数名称包裹返回数据，这样，返回数据就作为回调函数的参数传回去了
            String result = callback + "(" + new Gson().toJson(errMsg) + ")";
            response.getWriter().write(result);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * 暂停任务
     *
     * @param jobName
     * @param jobGroup
     * @return
     */
    @Permission(name = "暂停任务", permissionName = "local.micoSSV.qutz.pauseJob", permissionUrl = "/qutz/pauseJob")
    @RequestMapping(value = "/pauseJob", method = RequestMethod.POST)
    public void pauseJob(@RequestParam("jobName") String jobName, @RequestParam("jobGroup") String jobGroup,
                         HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        Result errMsg = Result.ok(null);
        try {
            // TODO 1 暂停任务
            customJobService.pauseJob(jobName, jobGroup);
            // TODO 2 返回更新数据结果 前端传过来的回调函数名称
            String callback = request.getParameter("callback");
            //TODO 用回调函数名称包裹返回数据，这样，返回数据就作为回调函数的参数传回去了
            String result = callback + "(" + new Gson().toJson(errMsg) + ")";
            response.getWriter().write(result);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * 恢复任务
     *
     * @param jobName
     * @param jobGroup
     * @return
     */
    @Permission(name = "恢复任务", permissionName = "local.micoSSV.qutz.resumeJob", permissionUrl = "/qutz/resumeJob")
    @RequestMapping(value = "/resumeJob", method = RequestMethod.POST)
    public void resumeJob(@RequestParam("jobName") String jobName, @RequestParam("jobGroup") String jobGroup,
                          HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        Result errMsg = Result.ok(null);
        try {
            // TODO 1 恢复任务
            customJobService.resumeJob(jobName, jobGroup);
            // TODO 2 返回更新数据结果 前端传过来的回调函数名称
            String callback = request.getParameter("callback");
            //TODO 用回调函数名称包裹返回数据，这样，返回数据就作为回调函数的参数传回去了
            String result = callback + "(" + new Gson().toJson(errMsg) + ")";
            response.getWriter().write(result);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * 删除任务
     *
     * @param jobName
     * @param jobGroup
     * @return
     */
    @Permission(name = "删除任务", permissionName = "local.micoSSV.qutz.deleteJob", permissionUrl = "/qutz/deleteJob")
    @RequestMapping(value = "/deleteJob", method = RequestMethod.POST)
    public void deleteJob(@RequestParam("jobName") String jobName, @RequestParam("jobGroup") String jobGroup,
                          HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        Result errMsg = Result.ok(null);
        try {
            // TODO 1 删除任务
            customJobService.deleteJob(jobName, jobGroup);
            // TODO 2 返回更新数据结果 前端传过来的回调函数名称
            String callback = request.getParameter("callback");
            //TODO 用回调函数名称包裹返回数据，这样，返回数据就作为回调函数的参数传回去了
            String result = callback + "(" + new Gson().toJson(errMsg) + ")";
            response.getWriter().write(result);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @Autowired
    private ICustomJobService customJobService;
}
