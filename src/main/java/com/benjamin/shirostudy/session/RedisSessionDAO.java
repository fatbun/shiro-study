package com.benjamin.shirostudy.session;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author zjw
 * @description
 */
@Component
public class RedisSessionDAO extends AbstractSessionDAO {

    @Resource
    private RedisTemplate redisTemplate;

    // 存储到Redis时，sessionId作为key，Session作为Value
    // sessionId就是一个字符串
    // Session可以和sessionId绑定到一起，绑定之后，可以基于Session拿到sessionId
    // 需要给Key设置一个统一的前缀，这样才可以方便通过keys命令查看到所有关联的信息

    private final String SHIOR_SESSION = "session:";

    @Override
    protected Serializable doCreate(Session session) {
        //1. 基于Session生成一个sessionId（唯一标识）
        Serializable sessionId = generateSessionId(session);

        //2. 将Session和sessionId绑定到一起（可以基于Session拿到sessionId）
        assignSessionId(session, sessionId);

        //3. 将 前缀:sessionId 作为key，session作为value存储
        redisTemplate.opsForValue().set(SHIOR_SESSION + sessionId, session, 30, TimeUnit.MINUTES);

        //4. 返回sessionId
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        //1. 基于sessionId获取Session （与Redis交互）
        if (sessionId == null) {
            return null;
        }
        Session session = (Session) redisTemplate.opsForValue().get(SHIOR_SESSION + sessionId);
        if (session != null) {
            redisTemplate.expire(SHIOR_SESSION + sessionId,30, TimeUnit.MINUTES);
        }
        return session;
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        //1. 修改Redis中session
        if (session == null) {
            return;
        }
        redisTemplate.opsForValue().set(SHIOR_SESSION + session.getId(), session, 30, TimeUnit.MINUTES);
    }

    @Override
    public void delete(Session session) {
        // 删除Redis中的Session
        if (session == null) {
            return;
        }
        redisTemplate.delete(SHIOR_SESSION + session.getId());
    }

    @Override
    public Collection<Session> getActiveSessions() {
        Set keys = redisTemplate.keys(SHIOR_SESSION + "*");

        Set<Session> sessionSet = new HashSet<>();
        // 尝试修改为管道操作，pipeline（Redis的知识）
        for (Object key : keys) {
            Session session = (Session) redisTemplate.opsForValue().get(key);
            sessionSet.add(session);
        }
        return sessionSet;
    }
}
