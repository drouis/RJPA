package com.rjpa.mic.controller;

import anno.Permission;
import com.rjpa.mic.server.ICustomJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/quartz")
public class CustomJobController {

    /**
     * 创建定时任务
     *
     * @param jobName
     * @param jobGroup
     * @return
     */
    @Permission(name = "创建定时任务", permissionName = "local.micoSSV.qutz.startCronJob", permissionUrl = "/qutz/startCronJob")
    @RequestMapping(value = "/startCronJob", method = RequestMethod.POST)
    public String startCronJob_(@RequestParam("jobName") String jobName, @RequestParam("jobGroup") String jobGroup) {
        customJobService.addCronJob(jobName, jobGroup);
        return "create cron task success";
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
    public String startAsyncJob_(@RequestParam("jobName") String jobName, @RequestParam("jobGroup") String jobGroup) {
        customJobService.addAsyncJob(jobName, jobGroup);
        return "create async task success";
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
    public String pauseJob(@RequestParam("jobName") String jobName, @RequestParam("jobGroup") String jobGroup) {
        customJobService.pauseJob(jobName, jobGroup);
        return "pause job success";
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
    public String resumeJob(@RequestParam("jobName") String jobName, @RequestParam("jobGroup") String jobGroup) {
        customJobService.resumeJob(jobName, jobGroup);
        return "resume job success";
    }

    /**
     * 删除任务
     *
     * @param jobName
     * @param jobGroup
     * @return
     */
    @Permission(name = "删除任务", permissionName = "local.micoSSV.qutz.deleteJob", permissionUrl = "/qutz/deleteJob")
    @RequestMapping(value = "/deleteJob", method = RequestMethod.PUT)
    public String deleteJob(@RequestParam("jobName") String jobName, @RequestParam("jobGroup") String jobGroup) {
        customJobService.deleteJob(jobName, jobGroup);
        return "delete job success";
    }

    @Autowired
    private ICustomJobService customJobService;
}
