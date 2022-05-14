package com.benjamin.shirostudy.session;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.session.mgt.WebSessionKey;

import javax.servlet.ServletRequest;
import java.io.Serializable;

/**
 * @author zjw
 * @description
 */
public class DefaultRedisWebSessionManager extends DefaultWebSessionManager {

    @Override
    protected Session retrieveSession(SessionKey sessionKey) throws UnknownSessionException {
        // 通过sessionKey获取sessionId
        Serializable sessionId = getSessionId(sessionKey);

        // 将sessionKey转为WebSessionKey
        if(sessionKey instanceof WebSessionKey){
            WebSessionKey webSessionKey = (WebSessionKey) sessionKey;
            // 获取到request域
            ServletRequest request = webSessionKey.getServletRequest();
            // 通过request尝试获取session信息
            Session session = (Session) request.getAttribute(sessionId + "");
            if(session != null){
//                System.out.println("从request域中获取session信息");
                return session;
            }else{
                session = retrieveSessionFromDataSource(sessionId);
                if (session == null) {
                    //session ID was provided, meaning one is expected to be found, but we couldn't find one:
                    String msg = "Could not find session with ID [" + sessionId + "]";
                    throw new UnknownSessionException(msg);
                }
//                System.out.println("Redis---doReadSession");
                request.setAttribute(sessionId + "",session);
                return session;
            }
        }
        return null;
    }
}
