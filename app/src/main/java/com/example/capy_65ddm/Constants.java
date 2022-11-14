package com.example.capy_65ddm;

import java.util.HashMap;

public class Constants {

    public static final String REMOTE_MSG_AUTHORIZATION = "Authorization";
    public static final String REMOTE_MSG_CONTENT_TYPE = "Content-Type";
    public static final String REMOTE_MSG_DATA = "data";
    public static final String REMOTE_MSG_REGISTRATION_IDS = "registration_ids";

    public static HashMap<String, String> remoteMsgHeaders = null;
    public static HashMap<String, String> getRemoteMsgHeaders(){
        if(remoteMsgHeaders ==  null){
            remoteMsgHeaders = new HashMap<>();
            remoteMsgHeaders.put(
                    REMOTE_MSG_AUTHORIZATION,
                    "AAAALHrWQ78:APA91bHIBkL5vq_H22Sv_Tl_Vxe9l8nXYc5wFDbMRPGdKFuGh3YInDjqQYj9z3Rp2mKqgTUum7dX6mTVc_cIM8W3QQTe0T7vJIp8uqOujoMOunfm2LxWMIWu9dQXxkn_p4geYDadCGFj");
            remoteMsgHeaders.put(
                    REMOTE_MSG_CONTENT_TYPE,
                    "application/json");
        }
        return remoteMsgHeaders;
    }

}
