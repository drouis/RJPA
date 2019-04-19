package com.rjpa.mic.server;

public interface ICustomJobService {
    /**
     * 添加一个定时任务
     *
     * @param jobName
     * @param jobGroup
     */
    void addCronJob(String jobName, String jobGroup);

    /**
     * 添加异步任务
     *
     * @param jobName
     * @param jobGroup
     */
    void addAsyncJob(String jobName, String jobGroup);

    /**
     * 暂停任务
     *
     * @param jobName
     * @param jobGroup
     */
    void pauseJob(String jobName, String jobGroup);

    /**
     * 恢复任务
     *
     * @param triggerName
     * @param triggerGroup
     */
    void resumeJob(String triggerName, String triggerGroup);

    /**
     * 删除job
     *
     * @param jobName
     * @param jobGroup
     */
    void deleteJob(String jobName, String jobGroup);
}
