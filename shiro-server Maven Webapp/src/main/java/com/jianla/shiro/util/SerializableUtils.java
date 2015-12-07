/**
 * HangZhou Jianla Network Technology Co., Ltd.
 * Copyright (c) 2014-2015 All Rights Reserved.
 */
package com.jianla.shiro.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.session.Session;

/**
 * 序列化工具类，提供序列化对象和反序列化对象的方法，对象必须可序列化
 * 
 * @author dimhat
 * @date 2015年12月3日 下午3:16:28
 * @version 1.0
 */
public class SerializableUtils {

    public static String serialize(Session session) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(session);
            return Base64.encodeToString(bos.toByteArray());
        } catch (Exception e) {
            throw new RuntimeException("serialize error", e);
        }
    }

    public static Session deserialize(String sessionStr) {
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(Base64.decode(sessionStr));
            ObjectInputStream ois = new ObjectInputStream(bis);
            return (Session) ois.readObject();
        } catch (Exception e) {
            throw new RuntimeException("deserialize error", e);
        }
    }

    //序列化成字节
    public static byte[] serializeByte(Session session) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(session);
            return bos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("serialize error", e);
        }
    }

    //反序列化字节为session
    public static Session deserializeByte(byte[] sessionByte) {
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(sessionByte);
            ObjectInputStream ois = new ObjectInputStream(bis);
            return (Session) ois.readObject();
        } catch (Exception e) {
            throw new RuntimeException("deserialize error", e);
        }
    }

}
