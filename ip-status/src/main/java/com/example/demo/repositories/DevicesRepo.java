package com.example.demo.repositories;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.models.Device;
import com.example.demo.services.IpStatusChecker;

@Component
public class DevicesRepo {

    @Autowired
    private IpStatusChecker ipStatusCheckerService;
    static private ArrayList<Device> deviceList = new ArrayList<>();

    public Device addDevice(String ip, String name) {
        final int status = ipStatusCheckerService.getIpStatus(ip);

        Device device = new Device(ip, name, status);
        deviceList.add(0, device);
        
        return device;
    }

    public void addDevice(Device device) {
        deviceList.add(0, device);
    }

    public void deleteDevice(String id) {
        int index = -1;
        for(int i=0; i<deviceList.size(); i++) {
            if(deviceList.get(i).getId().equals(id)) {
                index = i;
            }
        }
        if(index != -1) {
            deviceList.remove(index);
        }
    }

    public ArrayList<Device> getDeviceList() {
        return deviceList;
    }

    public void updateDeviceList(HashMap<String, String>[] devices) {
        for(HashMap<String, String> hashMap : devices) {
            if(hashMap.get("ip") != null) {
                String ip = hashMap.get("ip");
                String name = hashMap.get("name") != null ? hashMap.get("name") : "";

                boolean exists = isSameDeviceExists(ip, name);
                if(!exists) {
                    int UNKNOWN_STATUS = 3;
                    Device device = new Device(ip, name, UNKNOWN_STATUS);
                    addDevice(device);
                }
            }
        }
    }

    private boolean isSameDeviceExists(String ip, String name) {
        for(Device device : deviceList) {
            if(device.getIp().equals(ip) && device.getName().equals(name)) {
                return true;
            }
        }

        return false;
    }

}
