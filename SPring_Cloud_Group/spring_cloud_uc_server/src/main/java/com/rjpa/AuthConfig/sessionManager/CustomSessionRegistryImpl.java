package com.rjpa.AuthConfig.sessionManager;

import com.rjpa.AuthConfig.vo.User;
import com.rjpa.redis.RedisDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.session.SessionDestroyedEvent;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.util.Assert;

import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;

@Configuration
public class CustomSessionRegistryImpl implements SessionRegistry, ApplicationListener<SessionDestroyedEvent> {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    public static final String SESSIONIDS = "sessionIds";
    public static final String PRINCIPALS = "principals";

    @Autowired
    private RedisDao redisDao;

    public CustomSessionRegistryImpl() {
    }

    public List<Object> getAllPrincipals() {
        return new ArrayList(redisDao.getPrincipalsKeySet(PRINCIPALS));
    }

    public List<SessionInformation> getAllSessions(Object principal, boolean includeExpiredSessions) {
        Set<String> sessionsUsedByPrincipal = redisDao.getPrincipals(PRINCIPALS, ((User) principal).getUsername());
        if (sessionsUsedByPrincipal == null) {
            return Collections.emptyList();
        } else {
            List<SessionInformation> list = new ArrayList(sessionsUsedByPrincipal.size());
            Iterator var5 = sessionsUsedByPrincipal.iterator();
            while (true) {
                SessionInformation sessionInformation;
                do {
                    do {
                        if (!var5.hasNext()) {
                            return list;
                        }
                        String sessionId = (String) var5.next();
                        sessionInformation = this.getSessionInformation(sessionId);
                    } while (sessionInformation == null);
                } while (!includeExpiredSessions && sessionInformation.isExpired());
                list.add(sessionInformation);
            }
        }
    }

    public SessionInformation getSessionInformation(String sessionId) {
        Assert.hasText(sessionId, "SessionId required as per interface contract");
        return (SessionInformation) redisDao.getSessionInfo(SESSIONIDS, sessionId);
    }

    public void onApplicationEvent(SessionDestroyedEvent sessionDestroyedEvent) {
        String sessionId = sessionDestroyedEvent.getId();
        this.removeSessionInformation(sessionId);
    }

    public void refreshLastRequest(String sessionId) {
        Assert.hasText(sessionId, "SessionId required as per interface contract");
        SessionInformation info = this.getSessionInformation(sessionId);
        if (info != null) {
            info.refreshLastRequest();
        }
    }

    public void registerNewSession(String sessionId, Object principal) {
        Assert.hasText(sessionId, "SessionId required as per interface contract");
        Assert.notNull(principal, "Principal required as per interface contract");
        if (this.logger.isDebugEnabled()) {
            this.logger.debug("Registering session " + sessionId + ", for principal " + principal);
        }
        if (this.getSessionInformation(sessionId) != null) {
            this.removeSessionInformation(sessionId);
        }
        redisDao.addSessionInfo(SESSIONIDS, sessionId, new SessionInformation(principal, sessionId, new Date()));
//        this.sessionIds.put(sessionId, new SessionInformation(principal, sessionId, new Date()));
        Set<String> sessionsUsedByPrincipal = (Set) redisDao.getPrincipals(PRINCIPALS, principal.toString());
        if (sessionsUsedByPrincipal == null) {
            sessionsUsedByPrincipal = new CopyOnWriteArraySet();
            Set<String> prevSessionsUsedByPrincipal = (Set) redisDao.putIfAbsentPrincipals(PRINCIPALS, principal.toString(), sessionsUsedByPrincipal);
            if (prevSessionsUsedByPrincipal != null) {
                sessionsUsedByPrincipal = prevSessionsUsedByPrincipal;
            }
        }
        ((Set) sessionsUsedByPrincipal).add(sessionId);
        redisDao.putPrincipals(PRINCIPALS, principal.toString(), sessionsUsedByPrincipal);
        if (this.logger.isTraceEnabled()) {
            this.logger.trace("Sessions used by '" + principal + "' : " + sessionsUsedByPrincipal);
        }
    }

    public void removeSessionInformation(String sessionId) {
        Assert.hasText(sessionId, "SessionId required as per interface contract");
        SessionInformation info = this.getSessionInformation(sessionId);
        if (info != null) {
            if (this.logger.isTraceEnabled()) {
                this.logger.debug("Removing session " + sessionId + " from set of registered sessions");
            }
            redisDao.removeSessionInfo(SESSIONIDS, sessionId);
            Set<String> sessionsUsedByPrincipal = (Set) redisDao.getPrincipals(PRINCIPALS, info.getPrincipal().toString());
            if (sessionsUsedByPrincipal != null) {
                if (this.logger.isDebugEnabled()) {
                    this.logger.debug("Removing session " + sessionId + " from principal's set of registered sessions");
                }
                sessionsUsedByPrincipal.remove(sessionId);
                if (sessionsUsedByPrincipal.isEmpty()) {
                    if (this.logger.isDebugEnabled()) {
                        this.logger.debug("Removing principal " + info.getPrincipal() + " from registry");
                    }
                    redisDao.removePrincipal(PRINCIPALS, ((User) info.getPrincipal()).getUsername());
                }
                if (this.logger.isTraceEnabled()) {
                    this.logger.trace("Sessions used by '" + info.getPrincipal() + "' : " + sessionsUsedByPrincipal);
                }
            }
        }
    }
}
