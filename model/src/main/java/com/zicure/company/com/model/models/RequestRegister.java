package com.zicure.company.com.model.models;

/**
 * Created by 4GRYZ52 on 3/28/2017.
 */

public class RequestRegister {
    /**
     * DeviceToken : {"auth_token":"ZTZmMzIyOWEtMzQyNC00MjFiLTg4MDYtMzIyNTdlZGVmZjEw"}
     */

    private DeviceTokenBean DeviceToken;

    public DeviceTokenBean getDeviceToken() {
        return DeviceToken;
    }

    public void setDeviceToken(DeviceTokenBean DeviceToken) {
        this.DeviceToken = DeviceToken;
    }

    public static class DeviceTokenBean {
        /**
         * auth_token : ZTZmMzIyOWEtMzQyNC00MjFiLTg4MDYtMzIyNTdlZGVmZjEw
         */

        private String auth_token;

        public String getAuth_token() {
            return auth_token;
        }

        public void setAuth_token(String auth_token) {
            this.auth_token = auth_token;
        }
    }
}
